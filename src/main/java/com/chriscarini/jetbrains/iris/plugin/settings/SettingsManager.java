package com.chriscarini.jetbrains.iris.plugin.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.RoamingType;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;


/**
 * The {@link SettingsManager} for this plugin; settings will be stored out to and read from {@code iris.xml}.
 */
@State(name = "iris", storages = @Storage(value = "iris.xml", roamingType = RoamingType.PER_OS))
public class SettingsManager implements PersistentStateComponent<SettingsManager.IrisSettingsState> {
  private IrisSettingsState myState;

  public static SettingsManager getInstance() {
    return ServiceManager.getService(SettingsManager.class);
  }

  @NotNull
  @Override
  public IrisSettingsState getState() {
    if (myState == null) {
      myState = new IrisSettingsState();
    }
    return myState;
  }

  @Override
  public void loadState(@NotNull final IrisSettingsState irisSettingsState) {
    myState = irisSettingsState;
  }

  /**
   * Representation of the Iris Settings {@link State}.
   */
  public static class IrisSettingsState {
    public boolean enabled;
    public int pollingFrequency;
    public int lookbackAmount;
    public int incidentResultSize;
    public boolean hideNotification;
    public boolean keepExistingNotification;
    public boolean notifyFocusedProjectOnly;
    public String username;
    public String apiHost;

    @SuppressWarnings("WeakerAccess")
    public IrisSettingsState() {
      this.enabled = false;
      this.pollingFrequency = SettingsConfigurable.maxPollingFrequency;
      this.lookbackAmount = SettingsConfigurable.maxLookbackAmount;
      this.incidentResultSize = SettingsConfigurable.maxIncidentResultSize;
      this.hideNotification = false;
      this.keepExistingNotification = false;
      this.notifyFocusedProjectOnly = false;
      this.username = "";
      this.apiHost = "";
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      IrisSettingsState that = (IrisSettingsState) o;
      return enabled == that.enabled && pollingFrequency == that.pollingFrequency
          && lookbackAmount == that.lookbackAmount && incidentResultSize == that.incidentResultSize
          && hideNotification == that.hideNotification && keepExistingNotification == that.keepExistingNotification
          && notifyFocusedProjectOnly == that.notifyFocusedProjectOnly && Objects.equals(username, that.username)
          && Objects.equals(apiHost, that.apiHost);
    }

    @Override
    public int hashCode() {
      return Objects.hash(enabled, pollingFrequency, lookbackAmount, incidentResultSize, hideNotification,
          keepExistingNotification, notifyFocusedProjectOnly, username, apiHost);
    }
  }
}
