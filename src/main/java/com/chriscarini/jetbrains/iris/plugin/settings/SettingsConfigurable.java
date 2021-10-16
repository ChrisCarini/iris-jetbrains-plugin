package com.chriscarini.jetbrains.iris.plugin.settings;

import com.chriscarini.jetbrains.iris.client.DefaultIrisClient;
import com.chriscarini.jetbrains.iris.client.IrisClient;
import com.chriscarini.jetbrains.iris.client.model.Stats;
import com.chriscarini.jetbrains.iris.plugin.messages.IrisMessages;
import com.intellij.concurrency.JobScheduler;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.progress.PerformInBackgroundOption;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.ui.VerticalFlowLayout;
import com.intellij.ui.AnimatedIcon;
import com.intellij.ui.CollapsiblePanel;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.UIUtil;
import org.apache.http.entity.ContentType;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * A {@link Configurable} that provides the user the ability to configure the Iris plugin.
 */
public class SettingsConfigurable implements Configurable {
  @SuppressWarnings("WeakerAccess")
  protected static final int maxPollingFrequency = 125;
  @SuppressWarnings("WeakerAccess")
  protected static final int maxLookbackAmount = 240;
  @SuppressWarnings("WeakerAccess")
  protected static final int maxIncidentResultSize = 100;
  private final JPanel mainPanel = new JBPanel<>();

  private final JBCheckBox enabledField = new JBCheckBox();
  private final JSlider pollingFrequencySlider = new JSlider(5, maxPollingFrequency);
  private final JSlider lookbackAmountSlider = new JSlider(10, maxLookbackAmount);
  private final JSlider incidentResultSizeSlider = new JSlider(10, maxIncidentResultSize);
  private final JBCheckBox hideNotificationField = new JBCheckBox();
  private final JBCheckBox keepExistingNotificationField = new JBCheckBox();
  private final JBCheckBox notifyFocusedProjectOnlyField = new JBCheckBox();
  private final JBTextField usernameField = new JBTextField();
  private final JBTextField apiHostnameField = new JBTextField();

  private final JButton apiCheckConnectionButton =
      new JButton(IrisMessages.get("iris.settings.test.iris.host.connection"));
  private final JBLabel apiCheckConnectionResult = new JBLabel();
  private final JTextPane irisStatsResult = new JTextPane();

  private ScheduledFuture<?> checkConnectionStatusClearJob;

  public SettingsConfigurable() {
    buildMainPanel();
  }

  @Nls(capitalization = Nls.Capitalization.Title)
  @Override
  public String getDisplayName() {
    return IrisMessages.get("iris.settings.display.name.iris.configuration");
  }

  private void buildMainPanel() {
    // Create a simple form to display the user-configurable options
    mainPanel.setLayout(new VerticalFlowLayout(true, false));

    // Setup the sliders
    configureSlider(pollingFrequencySlider);
    pollingFrequencySlider.setMinorTickSpacing(1);
    configureSlider(lookbackAmountSlider);
    configureSlider(incidentResultSizeSlider);

    // Add a KeyListener to enable / disable the 'check connection' button
    apiHostnameField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(final KeyEvent e) {
        final boolean buttonEnabled = "".equals(apiHostnameField.getText());
        setApiCheckConnectionResult("", null, true, !buttonEnabled);
      }
    });

    apiCheckConnectionButton.setEnabled(true);
    apiCheckConnectionButton.addActionListener(new TestServerConnectionListener());

    irisStatsResult.setEnabled(false);
    irisStatsResult.setVisible(true);
    irisStatsResult.setContentType(ContentType.TEXT_HTML.getMimeType());
    irisStatsResult.setText(IrisMessages.get("iris.settings.stats.result.default.text"));

    final JComponent advancedOptionsPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent(IrisMessages.get("iris.settings.label.polling.frequency"), pollingFrequencySlider)
        .addTooltip(IrisMessages.get("iris.settings.tooltip.polling.frequency"))
        .addSeparator()
        .addLabeledComponent(IrisMessages.get("iris.settings.label.automatically.hide.notification"),
            hideNotificationField)
        .addTooltip(IrisMessages.get("iris.settings.tooltip.automatically.hide.notification"))
        .addSeparator()
        .addLabeledComponent(IrisMessages.get("iris.settings.label.keep.existing.notification"),
            keepExistingNotificationField)
        .addTooltip(IrisMessages.get("iris.settings.tooltip.keep.existing.notification"))
        .addSeparator()
        .addLabeledComponent(IrisMessages.get("iris.settings.label.notify.focused.project.only"),
            notifyFocusedProjectOnlyField)
        .addTooltip(IrisMessages.get("iris.settings.tooltip.notify.focused.project.only"))
        .addSeparator()
        .addLabeledComponent(IrisMessages.get("iris.settings.label.lookback.amount"), lookbackAmountSlider)
        .addTooltip(IrisMessages.get("iris.settings.tooltip.lookback.amount"))
        .addSeparator()
        .addLabeledComponent(IrisMessages.get("iris.settings.label.incident.result.size"), incidentResultSizeSlider)
        .addTooltip(IrisMessages.get("iris.settings.tooltip.incident.result.size"))
        .getPanel();

    mainPanel.add(FormBuilder.createFormBuilder()
        .addLabeledComponent(IrisMessages.get("iris.settings.label.enabled"), enabledField)
        .addSeparator()
        .addLabeledComponent(IrisMessages.get("iris.settings.label.username"), usernameField)
        .addTooltip(IrisMessages.get("iris.settings.tooltip.username"))
        .addSeparator()
        .addLabeledComponent(IrisMessages.get("iris.settings.label.iris.api.hostname"), apiHostnameField)
        .addTooltip(IrisMessages.get("iris.settings.tooltip.api.hostname"))
        .addLabeledComponent(apiCheckConnectionButton, apiCheckConnectionResult)
        .addLabeledComponent(IrisMessages.get("iris.settings.label.iris.server.stats"), irisStatsResult)
        .addSeparator()
        .addVerticalGap(UIUtil.LARGE_VGAP)
        .addComponent(new CollapsiblePanel(advancedOptionsPanel, true, true, AllIcons.General.ArrowDown,
            AllIcons.General.ArrowRight, IrisMessages.get("iris.settings.label.advanced.options")))
        .getPanel());
  }

  /**
   * Modifies the result field and button for Iris host check
   *
   * @param text          The result text to display
   * @param icon          The result icon to display
   * @param visible       {@code true} if the result should be visible, {@code false} otherwise.
   * @param buttonEnabled {@code true} if the test button should be enabled, {@code false} otherwise.
   */
  private void setApiCheckConnectionResult(@Nls(capitalization = Nls.Capitalization.Sentence) @NotNull final String text,
      @Nullable final Icon icon, final boolean visible, final boolean buttonEnabled) {
    apiCheckConnectionResult.setText(text);
    apiCheckConnectionResult.setIcon(icon);
    apiCheckConnectionResult.setVisible(visible);
    apiCheckConnectionButton.setEnabled(buttonEnabled);
  }

  private void configureSlider(final JSlider slider) {
    slider.setMajorTickSpacing(10);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    slider.setSnapToTicks(true);
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    // Set the user input field to contain the currently saved settings
    setUserInputFieldsFromSavedSettings();
    return mainPanel;
  }

  @Override
  public boolean isModified() {
    return !getSettingsFromUserInput().equals(getSettings());
  }

  /**
   * Apply the settings; saves the current user input list to the {@link SettingsManager}, and updates the table.
   */
  @Override
  public void apply() {
    final SettingsManager.IrisSettingsState settingsState = getSettingsFromUserInput();
    SettingsManager.getInstance().loadState(settingsState);
    setUserInputFieldsFromSavedSettings();
  }

  @NotNull
  private SettingsManager.IrisSettingsState getSettingsFromUserInput() {
    final SettingsManager.IrisSettingsState settingsState = new SettingsManager.IrisSettingsState();

    settingsState.enabled = enabledField.isSelected();
    settingsState.pollingFrequency = pollingFrequencySlider.getValue();
    settingsState.lookbackAmount = lookbackAmountSlider.getValue();
    settingsState.incidentResultSize = incidentResultSizeSlider.getValue();
    settingsState.hideNotification = hideNotificationField.isSelected();
    settingsState.notifyFocusedProjectOnly = notifyFocusedProjectOnlyField.isSelected();
    settingsState.keepExistingNotification = keepExistingNotificationField.isSelected();
    settingsState.username = usernameField.getText();
    settingsState.apiHost = apiHostnameField.getText();

    return settingsState;
  }

  /**
   * Get the saved settings and update the user input field.
   */
  private void setUserInputFieldsFromSavedSettings() {
    updateUserInputFields(getSettings());
  }

  /**
   * Update the user input field based on the input value provided by {@code val}
   *
   * @param settings The {@link SettingsManager.IrisSettingsState} for the plugin.
   */
  private void updateUserInputFields(@Nullable final SettingsManager.IrisSettingsState settings) {
    if (settings == null) {
      return;
    }

    enabledField.setSelected(settings.enabled);
    pollingFrequencySlider.setValue(settings.pollingFrequency);
    lookbackAmountSlider.setValue(settings.lookbackAmount);
    incidentResultSizeSlider.setValue(settings.incidentResultSize);
    notifyFocusedProjectOnlyField.setSelected(settings.notifyFocusedProjectOnly);
    hideNotificationField.setSelected(settings.hideNotification);
    keepExistingNotificationField.setSelected(settings.keepExistingNotification);
    usernameField.setText(settings.username);
    apiHostnameField.setText(settings.apiHost);
  }

  @NotNull
  private SettingsManager.IrisSettingsState getSettings() {
    return SettingsManager.getInstance().getState();
  }

  /**
   * Cancel the {@code checkConnectionStatusClearJob} if (1) the job is not null AND (2) the job is not done OR (3) has
   * not been canceled.
   */
  private void cancelCheckCredentialsClearJob() {
    if (checkConnectionStatusClearJob != null && (!checkConnectionStatusClearJob.isDone()
        || !checkConnectionStatusClearJob.isCancelled())) {
      checkConnectionStatusClearJob.cancel(true);
    }
  }

  /**
   * An {@link ActionListener} for checking Iris server connection.
   */
  private final class TestServerConnectionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      // Clear any previous result.
      setApiCheckConnectionResult(IrisMessages.get("iris.settings.test.server.connection.checking"),
          new AnimatedIcon.Default(), true, false);

      ProgressManager.getInstance()
          .run(new Task.Backgroundable(null,
              IrisMessages.get("iris.settings.test.server.connection.checking.iris.connection"), true,
              PerformInBackgroundOption.ALWAYS_BACKGROUND) {
            @Override
            public void run(@NotNull final ProgressIndicator indicator) {
              final IrisClient tempIrisClient = new DefaultIrisClient(apiHostnameField.getText());
              if (tempIrisClient.testConnection()) {
                setApiCheckConnectionResult(IrisMessages.get("iris.settings.test.server.connection.connection.success"),
                    AllIcons.General.InspectionsOK, true, true);
                final Stats stats = tempIrisClient.getStats();
                if (stats != null) {
                  irisStatsResult.setText(stats.toHtml());
                }
              } else {
                setApiCheckConnectionResult(IrisMessages.get("iris.settings.test.server.connection.connection.failed"),
                    AllIcons.General.ExclMark, true, true);
                irisStatsResult.setText("");
              }
              // Cancel any existing jobs to clear the text and re-create (reset clear timer)
              cancelCheckCredentialsClearJob();
              checkConnectionStatusClearJob = JobScheduler.getScheduler()
                  .schedule(() -> setApiCheckConnectionResult("", null, false, true), 10, TimeUnit.SECONDS);
            }
          });
    }
  }
}
