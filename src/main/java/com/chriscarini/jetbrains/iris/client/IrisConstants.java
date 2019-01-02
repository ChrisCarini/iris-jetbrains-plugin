package com.chriscarini.jetbrains.iris.client;

import org.jetbrains.annotations.NonNls;


/**
 * Constants for Iris
 */
public interface IrisConstants {
  /**
   * The default value to use for timeouts.
   */
  int TIMEOUT = 30;

  /**
   * The default value to use for the number of maximum redirects.
   */
  int MAX_REDIRECT = 2;

  /**
   * The base of the API v0 endpoint for Iris.
   */
  @NonNls
  String API_V0 = "/v0";

  /**
   * For requesting {@link com.chriscarini.jetbrains.iris.client.model.Incident}s
   * <p>
   * Example Request: https://iris.my_host.com/v0/incidents/?target=user123&created__ge=1538946000&limit=500
   */
  @NonNls
  String API_INCIDENTS = API_V0 + "/incidents";

  /**
   * For claiming {@link com.chriscarini.jetbrains.iris.client.model.Incident}s
   * <p>
   * Example Request: https://iris.my_host.com/v0/incidents/123
   */
  @NonNls
  String API_INCIDENT_CLAIM = API_INCIDENTS + "/%d";

  /**
   * For requesting {@link com.chriscarini.jetbrains.iris.client.model.Message}s
   * <p>
   * Example Request: https://iris.my_host.com/v0/messages?incident_id=10332307
   */
  @NonNls
  String API_MESSAGES = API_V0 + "/messages";

  /**
   * For requesting {@link com.chriscarini.jetbrains.iris.client.model.Stats}s
   * <p>
   * Example Request: https://iris.my_host.com/stats
   */
  @NonNls
  String API_STATS = API_V0 + "/stats";

  /**
   * For checking the health of Iris; will return "GOOD" if everything is ok.
   */
  @NonNls
  String HEALTHCHECK = "/healthcheck";

  /**
   * The return value of the /healthcheck endpoint denoting everything is ok.
   */
  @NonNls
  String HEALTHCHECK_GOOD = "GOOD";
}
