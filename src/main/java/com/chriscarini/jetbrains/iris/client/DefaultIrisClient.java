package com.chriscarini.jetbrains.iris.client;

import com.chriscarini.jetbrains.iris.client.model.ClaimResponse;
import com.chriscarini.jetbrains.iris.client.model.Incident;
import com.chriscarini.jetbrains.iris.client.model.Message;
import com.chriscarini.jetbrains.iris.client.model.Stats;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.diagnostic.Logger;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.http.HttpResponse;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Default implementation of an {@link IrisClient}.
 */
public class DefaultIrisClient implements IrisClient {
  private static final Logger LOG = Logger.getInstance(DefaultIrisClient.class);

  // @formatter:off
  private static final TypeToken<List<Incident>> IRIS_INCIDENTS_TYPE_TOKEN = new TypeToken<List<Incident>>() {};
  private static final TypeToken<List<Message>> IRIS_MESSAGES_TYPE_TOKEN = new TypeToken<List<Message>>() {};
  private static final TypeToken<Stats> IRIS_STATS_TYPE_TOKEN = new TypeToken<Stats>() {};
  @SuppressWarnings("unused") // TODO - remove `unused` annotation once we can claim an incident via the Iris API...
  private static final TypeToken<ClaimResponse> IRIS_INCIDENT_CLAIM_TYPE_TOKEN = new TypeToken<ClaimResponse>() {};
  // @formatter:on

  private static final Gson GSON = new Gson();

  private final int timeout;
  private final int maxRedirectFollow;
  private String baseUrl;
  private CloseableHttpClient httpClient;
  private RedirectStrategy redirectStrategy;

  public DefaultIrisClient(final String baseUrl) {
    this(baseUrl, IrisConstants.TIMEOUT, IrisConstants.MAX_REDIRECT, DefaultRedirectStrategy.INSTANCE);
  }

  private DefaultIrisClient(@NotNull final String baseUrl, final int timeout, final int maxRedirectFollow,
      @NotNull final RedirectStrategy redirectStrategy) {
    this.baseUrl = baseUrl;
    this.timeout = timeout;
    this.maxRedirectFollow = maxRedirectFollow;
    this.redirectStrategy = redirectStrategy;
  }

  @NotNull
  @Override
  public List<Incident> getActiveIncidentsSince(final String target, final long since) {
    final String parameters = Incident.RequestParameterBuilder.create()
        .withTarget(target)
        .created(Incident.RequestParameterBuilder.Operator.GE, since)
        .isActive(true)
        .limitResultsTo(500)
        .fetchFields(ACTIVE_INCIDENT_FIELD_NAMES)
        .build();
    return getListFromParameters(IrisConstants.API_INCIDENTS, parameters, IRIS_INCIDENTS_TYPE_TOKEN);
  }

  @Override
  public void claimIncident(final long incidentId, @NotNull final String owner) {
        /*
         TODO - How do we actually claim an incident? It seems like we need to be our own 'application' in order to
                claim an incident (because the HMAC Auth header requires the 'app key'). This would be better if a
                user was able to do this instead of an application.

        final long startTime = System.currentTimeMillis();

        LOG.debug(String.format("Beginning Iris incident claim request for %s", incidentId)); //NON-NLS

        final HttpPost httpPost = createPostRequest(String.format(IrisConstants.API_INCIDENT_CLAIM, incidentId));
        httpPost.addHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()));
        httpPost.addHeader(new BasicHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType()));
        httpPost.setEntity(new ByteArrayEntity(String.format("{\"owner\": \"%s\"}", owner).getBytes()));

        final ClaimResponse result = sendRequestGetResponse(httpPost, IRIS_INCIDENT_CLAIM_TYPE_TOKEN);

        final long timeTaken = System.currentTimeMillis() - startTime;

        if (result == null) {
            LOG.debug(String.format("Finished Iris incident claim request for %s; claim failed? Duration: %s ms", //NON-NLS
                    incidentId, timeTaken));
            return;
        }
        LOG.debug(String.format("Finished Iris incident claim request for %s. Duration: %s ms", incidentId, timeTaken)); //NON-NLS
        */
  }

  @NotNull
  @Override
  public List<Message> getMessages(final long incidentId, final String target) {
    final String parameters =
        new Message.RequestParameterBuilder().withIncidentId(incidentId).withTarget(target).build();
    return getListFromParameters(IrisConstants.API_MESSAGES, parameters, IRIS_MESSAGES_TYPE_TOKEN);
  }

  @NotNull
  @Override
  public String getCurrentHostname() {
    return getBaseUrl();
  }

  @Override
  public void changeCurrentHostname(@NotNull final String hostname) {
    this.baseUrl = hostname;
  }

  @Override
  public boolean testConnection() {
    LOG.debug("Beginning Iris test connection..."); //NON-NLS

    final long startTime = System.currentTimeMillis();
    final HttpGet httpGet = createGetRequest(getPath(IrisConstants.HEALTHCHECK));

    final String requestResponse = handleRequest(httpGet, response -> {
      String responseStr = response.getStatusLine().toString();
      try {
        responseStr = EntityUtils.toString(response.getEntity());
      } catch (final IOException e) {
        LOG.warn("IOException converting the http response entity to a string", e); //NON-NLS
      }
      return responseStr;
    });
    final long timeTaken = System.currentTimeMillis() - startTime;

    LOG.debug(
        String.format("Finished Iris test connection - %s. Duration: %s ms", requestResponse, timeTaken)); //NON-NLS
    return IrisConstants.HEALTHCHECK_GOOD.equals(requestResponse);
  }

  @Nullable
  @Override
  public Stats getStats() {
    LOG.debug("Beginning Iris stats request..."); //NON-NLS

    final long startTime = System.currentTimeMillis();
    final HttpGet httpGet = createGetRequest(getPath(IrisConstants.API_STATS));
    final Stats result = sendRequestGetResponse(httpGet, IRIS_STATS_TYPE_TOKEN);
    final long timeTaken = System.currentTimeMillis() - startTime;

    LOG.debug(String.format("Finished Iris stats request. Duration: %s ms", timeTaken)); //NON-NLS

    return result;
  }

  /**
   * Get the {@link List} of {@link T} from Iris for the provided path and parameters.
   * @param path The {@link String} representation of the path to request.
   * @param parameters The {@link String} representation of the parameters to append to the path.
   * @param type The {@link TypeToken} specifying the type ({@link T}) of elements in the {@link List}.
   * @param <T> The type of elements in the {@link List} response from Iris.
   * @return The {@link List} of {@link T} from Iris.
   */
  @NotNull
  private <T> List<T> getListFromParameters(@NotNull final String path, @NotNull final String parameters,
      @NotNull final TypeToken<List<T>> type) {
    final String typeStr = type.toString();
    LOG.debug(String.format("Beginning Iris request for %s...", typeStr)); //NON-NLS

    final long startTime = System.currentTimeMillis();
    final HttpGet httpGet = createGetRequest(getPath(path, parameters));
    final List<T> result = sendRequestGetResponse(httpGet, type);
    final long timeTaken = System.currentTimeMillis() - startTime;

    LOG.debug(String.format("Finished Iris request for %s. Number of results: %s  Duration: %s ms", typeStr, //NON-NLS
        result == null ? 0 : result.size(), timeTaken));

    return result == null ? Collections.emptyList() : result;
  }

  /**
   * Send out a request given the provided inputs and return the expected response
   *
   * @param httpRequest the HttpGet object representing the request
   * @param type        the TypeToken of the expected return object; used for deserialization of the JSON response from the API
   * @param <T>         the response type expected from the TypeToken specified
   * @return response from the request
   */
  @Nullable
  private <T> T sendRequestGetResponse(@NotNull final HttpRequestBase httpRequest, @NotNull final TypeToken<T> type) {
    return handleRequest(httpRequest, response -> handleResponse(response, type));
  }

  /**
   * Generic handler for {@link HttpRequestBase}. Creates a {@link CloseableHttpClient}, issues the provided request,
   * and handles the {@link HttpResponse} based on the provided {@link Function} response handler.
   *
   * @param request     The Http Request; commonly a {@link HttpGet} or {@link HttpPost}, but
   *                        any {@link HttpRequestBase} will work.
   * @param responseHandler The {@link Function} response handler used to handle the {@link HttpResponse}.
   * @return The {@link R} object from the provided {@code responseHandler}.
   */
  @Nullable
  private <R> R handleRequest(@NotNull final HttpRequestBase request,
      @NotNull final Function<HttpResponse, R> responseHandler) {
    final CloseableHttpClient httpClient = getHttpClient();
    try {
      // Send Http request
      LOG.debug("Executing request " + request.getRequestLine()); //NON-NLS
      final HttpResponse response = httpClient.execute(request);
      // Handle response
      return responseHandler.apply(response);
    } catch (final UnknownHostException e) {
      LOG.debug(String.format("Unknown host: %s", request.getURI().getHost()), e); //NON-NLS
    } catch (final IOException e) {
      LOG.warn("Error executing Http Request", e); //NON-NLS
    }
    return null;
  }

  /**
   * Handle a response - gets the response entity and convert JSON (using {@link Gson}) into
   * the provided {@link TypeToken} type.
   *
   * @param response The {@link HttpResponse} from the request
   * @param type     The {@link TypeToken} type of which to return
   * @param <T>      An extension of for which we're expecting the response.
   * @return The {@code T} or {@code null} should there be an issue with converting the response.
   */
  @Nullable
  private <T> T handleResponse(@NotNull final HttpResponse response, @NotNull final TypeToken<T> type) {
    try {
      final String responseJson = EntityUtils.toString(response.getEntity());
      return GSON.fromJson(responseJson, type.getType());
    } catch (final IOException e) {
      LOG.warn("IOException converting the http response entity to a string", e); //NON-NLS
    }
    return null;
  }

  /**
   * Gets a {@link CloseableHttpClient}.
   *
   * Note, the initial invocation creates a client and incurs a small performance penalty; but subsequent calls use
   * the same {@link CloseableHttpClient} and thus speed up the call.
   *
   * @return A fully configured {@link CloseableHttpClient}.
   */
  @NotNull
  private CloseableHttpClient getHttpClient() {
    if (httpClient == null) {
      httpClient = createHttpClient();
    }
    return httpClient;
  }

  /**
   * Create a {@link CloseableHttpClient}
   *
   * @return A {@link CloseableHttpClient}
   */
  private CloseableHttpClient createHttpClient() {
    // @formatter:off
    final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(getTimeout() * 1000)            // in ms
            .setConnectionRequestTimeout(getTimeout() * 1000)  // in ms
            .setRedirectsEnabled(true)
            .setMaxRedirects(getMaxRedirectFollow())
            .build();
    // @formatter:on
    return HttpClients.custom()
        .setDefaultRequestConfig(requestConfig)
        .setConnectionTimeToLive(getTimeout(), TimeUnit.SECONDS)
        .setRedirectStrategy(redirectStrategy)
        .build();
  }

  /**
   * Helper method to simplify getting a {@link String} representation of the full request URL from just the path and
   * provided parameters using the provided base url from client construction.
   *
   * @param path The path to append to the baseUrl; needs to include the leading '/'
   * @param parameters The parameters to append to the request; ignored if null.
   * @return The {@link String} representation of the full URL for a request
   */
  @NotNull
  private String getPath(@NotNull final String path, @Nullable final String parameters) {
    return parameters == null ? getPath(path) : getPath(String.format(PATH_WITH_PARAM_FORMAT, path, parameters));
  }

  /**
   * Helper method to simplify getting a {@link String} representation of the full request URL from just the path by
   * using the provided base url from client construction.
   *
   * @param path the path to append to the baseUrl; needs to include the leading '/'
   * @return The {@link String} representation of the full URL for a request
   */
  @NotNull
  private String getPath(@NotNull final String path) {
    return String.format(PATH_FORMAT, this.getBaseUrl(), path);
  }

  /**
   * Get a HttpGet request for a given path.
   *
   * @param path full path for the request
   * @return The {@link HttpGet} object for the provided path.
   */
  @NotNull
  private HttpGet createGetRequest(@NotNull final String path) {
    return new HttpGet(path);
  }

  /**
   * Get a HttpPost request for a given path.
   *
   * @param path full path for the request
   * @return The {@link HttpPost} object for the provided path.
   */
  @NotNull
  protected HttpPost createPostRequest(@NotNull final String path) {
    return new HttpPost(path);
  }

  @SuppressWarnings("WeakerAccess")
  public String getBaseUrl() {
    return baseUrl;
  }

  @SuppressWarnings("WeakerAccess")
  public int getTimeout() {
    return timeout;
  }

  @SuppressWarnings("WeakerAccess")
  public int getMaxRedirectFollow() {
    return maxRedirectFollow;
  }
}
