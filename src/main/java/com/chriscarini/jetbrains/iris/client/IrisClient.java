package com.chriscarini.jetbrains.iris.client;

import com.chriscarini.jetbrains.iris.client.model.Incident;
import com.chriscarini.jetbrains.iris.client.model.Message;
import com.chriscarini.jetbrains.iris.client.model.Stats;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Interface for an Iris Client.
 */
public interface IrisClient {
  @NonNls
  String PATH_FORMAT = "%s%s";
  @NonNls
  String PATH_WITH_PARAM_FORMAT = "%s?%s";

  @NonNls
  String ID = "id";
  @NonNls
  String OWNER = "owner";
  @NonNls
  String APPLICATION = "application";
  @NonNls
  String PLAN = "plan";
  @NonNls
  String PLAN_ID = "plan_id";
  @NonNls
  String CREATED = "created";
  @NonNls
  String UPDATED = "updated";
  @NonNls
  String ACTIVE = "active";
  @NonNls
  String CURRENT_STEP = "current_step";
  List<String> ACTIVE_INCIDENT_FIELD_NAMES =
      Arrays.asList(ID, OWNER, APPLICATION, PLAN, PLAN_ID, CREATED, UPDATED, ACTIVE, CURRENT_STEP);

  /**
   * Get a {@link List} of active {@link Incident}'s since the provided {@code since} value.
   * @param target The target to search for active incidents.
   * @param since The {@code long} value to use when searching for active incidents.
   * @return A {@link List} of {@link Incident}'s matching the provided search criteria; empty list otherwise.
   */
  @NotNull
  List<Incident> getActiveIncidentsSince(final String target, final long since);

  /**
   * Claim an Iris incident.
   * @param incidentId The id of the {@link Incident} to claim.
   * @param owner The owner claiming the Iris {@link Incident}.
   */
  void claimIncident(final long incidentId, @NotNull final String owner);

  /**
   * Get a {@link List} of {@link Message}'s for the provided id of the {@link Incident} and target user.
   * @param incidentId The {@link Incident} id of which to obtain the messages.
   * @param target The target of the message.
   * @return A {@link List} of {@link Message}'s matching the provided search criteria; empty list otherwise.
   */
  @NotNull
  List<Message> getMessages(final long incidentId, final String target);

  /**
   * Get the current hostname being used for requests to Iris.
   * @return The {@link String} value of the hostname being used for Iris requests.
   */
  @NotNull
  String getCurrentHostname();

  /**
   * Change the hostname to use for sending requests to Iris.
   * @param hostname The {@link String} value of the hostname, including with the protocol (http/https) and any port.
   */
  void changeCurrentHostname(@NotNull final String hostname);

  /**
   * Test the connection to the Iris instance. Does this via requesting the {@code /healthcheck} endpoint of Iris.
   * @return {@code true} if the connection succeeded, {@code false} otherwise.
   */
  boolean testConnection();

  /**
   * Get the {@link Stats} from the Iris instance.
   * @return The {@link Stats} for the Iris instance.
   */
  @Nullable
  Stats getStats();
}
