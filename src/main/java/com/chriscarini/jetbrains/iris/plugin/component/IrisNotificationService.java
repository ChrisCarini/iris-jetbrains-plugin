package com.chriscarini.jetbrains.iris.plugin.component;

import com.chriscarini.jetbrains.iris.client.DefaultIrisClient;
import com.chriscarini.jetbrains.iris.client.IrisClient;
import com.chriscarini.jetbrains.iris.client.model.Incident;
import com.chriscarini.jetbrains.iris.client.model.Message;
import com.chriscarini.jetbrains.iris.plugin.IrisIcons;
import com.chriscarini.jetbrains.iris.plugin.action.ClaimIncidentAction;
import com.chriscarini.jetbrains.iris.plugin.messages.IrisMessages;
import com.chriscarini.jetbrains.iris.plugin.settings.SettingsManager;
import com.intellij.concurrency.JobScheduler;
import com.intellij.ide.AppLifecycleListener;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.util.messages.MessageBusConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;


/**
 * Application Service managing the Iris Notifications.
 */
public class IrisNotificationService implements BaseComponent {
  @NonNls
  private static final Logger LOG = Logger.getInstance(IrisNotificationService.class);

  private final NotificationGroup IRIS_NOTIFICATION_GROUP;

  private final Map<String, List<Notification>> irisIncidentIdToNotificationListMap = new HashMap<>();
  private IrisClient irisClient;
  private int currentPollingFrequency = 900; // in seconds
  private ScheduledFuture<?> irisPollingJob = null;
  private AtomicLong lastRequestAt; // in seconds
  private final Runnable irisPollingRunnable = () -> {
    LOG.debug("Starting Iris Polling execution...");
    final SettingsManager.IrisSettingsState settings = SettingsManager.getInstance().getState();

    // If the polling frequency changed in the settings from the current polling frequency, cancel the current job,
    //   then update the polling frequency and reschedule the polling job.
    if (settings.pollingFrequency != currentPollingFrequency) {
      LOG.debug(String.format("Iris polling frequency changed from %s sec to %s sec; updating...", // NON-NLS
          currentPollingFrequency, settings.pollingFrequency));
      irisPollingJob.cancel(false);
      currentPollingFrequency = settings.pollingFrequency;
      scheduleIrisPollingJob();
      return;
    }

    // If we're not enabled, the username or hostname are empty, or there are no active projects, don't bother proceeding at all.
    final List<Project> activeProjects = getActiveProjects();
    if (!settings.enabled || settings.username.isEmpty() || settings.apiHost.isEmpty() || activeProjects.isEmpty()) {
      // @formatter:off
      LOG.debug(String.format(
        "Stopping Iris Polling execution early - enabled:[%s] username empty:[%s] hostname empty:[%s] active projects empty:[%s]", //NON-NLS
        settings.enabled, settings.username.isEmpty(), settings.apiHost.isEmpty(), activeProjects.isEmpty()));
      // @formatter:on
      return;
    }
    // Change the hostname of the client if the settings have changed since the last call
    if (!irisClient.getCurrentHostname().equals(settings.apiHost)) {
      LOG.debug(String.format("Iris hostname changing from [%s] to [%s] ...", irisClient.getCurrentHostname(), //NON-NLS
          settings.apiHost));
      irisClient.changeCurrentHostname(settings.apiHost);
    }
    final List<Incident> incidents = irisClient.getActiveIncidentsSince(settings.username,
        lastRequestAt.getAndSet((System.currentTimeMillis() / 1000) - settings.lookbackAmount),
        settings.incidentResultSize);
    if (!incidents.isEmpty()) {
      // notify current project
      ApplicationManager.getApplication().invokeLater(() -> notifyCurrentProjects(incidents));
    }
  };

  public IrisNotificationService() {
    final SettingsManager.IrisSettingsState settings = SettingsManager.getInstance().getState();
    IRIS_NOTIFICATION_GROUP =
        new NotificationGroup(IrisMessages.get("iris.notification.service.notification.group.name"),
            settings.hideNotification ? NotificationDisplayType.BALLOON : NotificationDisplayType.STICKY_BALLOON, true,
            null, IrisIcons.Iris);
    lastRequestAt = new AtomicLong((System.currentTimeMillis() / 1000) - settings.lookbackAmount);
    irisClient = new DefaultIrisClient(settings.apiHost);
  }

  public static IrisNotificationService getInstance() {
    return ApplicationManager.getApplication().getComponent(IrisNotificationService.class);
  }

  /**
   * Initialize the component by setting the current polling frequency, scheduling the polling job, and registering
   * the job cancellation upon application shutdown.
   */
  @Override
  public void initComponent() {
    LOG.debug("Initializing Iris Notification Service...");
    final SettingsManager.IrisSettingsState settings = SettingsManager.getInstance().getState();
    currentPollingFrequency = settings.pollingFrequency;
    scheduleIrisPollingJob();

    // Subscribe to appWillBeClosed event to stop the scheduled job
    // we cannot do this disposeComponent as it seems services get killed too fast (before dispose but after appClosing)
    LOG.debug("Registering Application lifecycle listener for Iris Notification Service...");
    final Application app = ApplicationManager.getApplication();
    final MessageBusConnection connection = app.getMessageBus().connect(app);
    connection.subscribe(AppLifecycleListener.TOPIC, new AppLifecycleListener() {
      @Override
      public void appWillBeClosed(final boolean isRestart) {
        if (irisPollingJob != null) {
          irisPollingJob.cancel(false);
        }
      }
    });
    LOG.debug("Initializing Iris Notification Service complete.");
  }

  /**
   * Convenience method to schedule the Iris Polling Job.
   */
  private void scheduleIrisPollingJob() {
    LOG.debug(String.format("Scheduling Iris Polling Job every %s seconds...", currentPollingFrequency)); //NON-NLS
    irisPollingJob = JobScheduler.getScheduler()
        .scheduleWithFixedDelay(irisPollingRunnable, 0, currentPollingFrequency, TimeUnit.SECONDS);
  }

  /**
   * Notify the currently open projects of the provided {@link Incident}s. If there are no incidents, or active projects
   * then this method has no effect.
   * @param incidents The {@link List} of {@link Incident}s to notify
   */
  private void notifyCurrentProjects(@NotNull final List<Incident> incidents) {
    final SettingsManager.IrisSettingsState settings = SettingsManager.getInstance().getState();

    final Map<String, Notification> notificationsToShow = new HashMap<>();
    for (final Incident incident : incidents) {
      final String username = SettingsManager.getInstance().getState().username;
      final List<Message> messages = irisClient.getMessages(incident.getId(), username);
      final List<Message> filteredMessages =
          messages.stream().filter(message -> username.equals(message.getTarget())).collect(Collectors.toList());
      if (filteredMessages.isEmpty()) {
        return;
      }
      final Message message = filteredMessages.get(0);
      final String application = incident.getApplication();

      final String messageSubject = message.getSubject();
      final String subject =
          messageSubject == null ? "" : messageSubject.substring(String.valueOf(message.getId()).length()).trim();

      final String irisNotificationKey = getIrisNotificationKey(incident);

      if (!irisIncidentIdToNotificationListMap.containsKey(irisNotificationKey)) {
        irisIncidentIdToNotificationListMap.put(irisNotificationKey, new CopyOnWriteArrayList<>());
      }

      final List<Notification> existingNotifications = irisIncidentIdToNotificationListMap.get(irisNotificationKey);

      // If there are existing notifications and we want to keep them, just return, otherwise expire the
      // existing notifications and create another
      if (settings.keepExistingNotification && !existingNotifications.isEmpty()) {
        return;
      }

      // Expire any existing notifications
      for (final Notification notification : existingNotifications) {
        existingNotifications.remove(notification);
        notification.expire();
      }

      // Show the notifications in the focused project (if we can determine it, otherwise, try all the projects).
//      final Project focusedProject = ProjectUtils.getLastFocusedOrOpenedProject();
//      final List<Project> projects = focusedProject == null ? getActiveProjects() : Collections.singletonList(focusedProject);
      final List<Project> projects = getActiveProjects();

      for (final Project project : projects) {
        // notify if the project isn't disposed.
        if (!project.isDisposed()) {
          // Create a new notification
          final Notification incidentNotification =
              IRIS_NOTIFICATION_GROUP.createNotification(IrisMessages.get("iris.notification.title"), application,
                  subject, NotificationType.INFORMATION, null);
          incidentNotification.addAction(
              new ClaimIncidentAction(irisClient.getCurrentHostname(), incident.getId(), () -> {
                for (final Notification notification : irisIncidentIdToNotificationListMap.get(irisNotificationKey)) {
                  notification.expire();
                }
                irisIncidentIdToNotificationListMap.remove(irisNotificationKey);
              }));
          incidentNotification.notify(project);
          existingNotifications.add(incidentNotification);
        }
      }
    }
  }

  /**
   * Helper method to get the notification key for an Iris {@link Incident}.
   * @param incident The Iris {@link Incident} to obtain the key for.
   * @return The {@link String} representation of the key for the Iris {@link Incident}.
   */
  private String getIrisNotificationKey(Incident incident) {
    return String.format("IRIS_INCIDENT_%s", incident.getId()); //NON-NLS
  }

  /**
   * Helper method to get the list of active projects.
   * @return The {@link List} of active {@link Project}s.
   */
  private List<Project> getActiveProjects() {
    return Arrays.asList(ProjectManager.getInstance().getOpenProjects());
  }
}
