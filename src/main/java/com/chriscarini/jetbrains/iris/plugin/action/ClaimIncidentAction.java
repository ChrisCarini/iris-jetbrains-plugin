package com.chriscarini.jetbrains.iris.plugin.action;

import com.chriscarini.jetbrains.iris.plugin.messages.IrisMessages;
import com.intellij.notification.BrowseNotificationAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * A simple {@link DumbAwareAction} to launch a browser to allow the user to easily claim an incident.
 *
 * <i>Ideally</i>... we'd be able to hit an API endpoint in order to claim the incident, but it seems like we need to
 * be our own 'application' in order to claim an incident (because the HMAC Auth header requires the 'app key').
 */
public class ClaimIncidentAction extends BrowseNotificationAction {
  @NonNls
  private static final String IRIS_INCIDENT_URL_FORMAT = "%s/incidents/%s";
  @Nullable
  private final Runnable afterActionPerformed;

  public ClaimIncidentAction(@NotNull final String hostnameBase, final long incidentId,
      @Nullable final Runnable afterActionPerformed) {

    super(IrisMessages.get("iris.claim.incident.action.title", Long.toString(incidentId)),
        String.format(IRIS_INCIDENT_URL_FORMAT, hostnameBase, incidentId));
    this.afterActionPerformed = afterActionPerformed;
  }

  @Override
  public void actionPerformed(@NotNull final AnActionEvent e) {
    super.actionPerformed(e);
    if (this.afterActionPerformed != null) {
      this.afterActionPerformed.run();
    }
  }
}
