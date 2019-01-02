package com.chriscarini.jetbrains.iris.client.model;

import java.util.StringJoiner;
import org.jetbrains.annotations.NonNls;


/**
 * Model representation of a single Iris message.
 */
public class Message {
  private String body;
  private long incidentId;
  private String target;
  private long created;
  private int targetChanged;
  private String destination;
  private int modeChanged;
  private String priority;
  private String application;
  private String mode;
  private int active;
  private long id;
  private long sent;
  private String subject;

  public String getBody() {
    return body;
  }

  public long getIncidentId() {
    return incidentId;
  }

  public String getTarget() {
    return target;
  }

  public long getCreated() {
    return created;
  }

  public int getTargetChanged() {
    return targetChanged;
  }

  public String getDestination() {
    return destination;
  }

  public int getModeChanged() {
    return modeChanged;
  }

  public String getPriority() {
    return priority;
  }

  public String getApplication() {
    return application;
  }

  public String getMode() {
    return mode;
  }

  public int getActive() {
    return active;
  }

  public long getId() {
    return id;
  }

  public long getSent() {
    return sent;
  }

  public String getSubject() {
    return subject;
  }

  public static class RequestParameterBuilder {
    @NonNls
    protected static final String INCIDENT_ID_EQ = "incident_id=%d";
    @NonNls
    protected static final String TARGET_EQ = "target=%s";
    private long incidentId;
    private String target;

    public RequestParameterBuilder() {
    }

    public static RequestParameterBuilder create() {
      return new Message.RequestParameterBuilder();
    }

    public RequestParameterBuilder withIncidentId(final long id) {
      this.incidentId = id;
      return this;
    }

    public RequestParameterBuilder withTarget(final String target) {
      this.target = target;
      return this;
    }

    public String build() {
      final StringJoiner sj = new StringJoiner("&");

      sj.add(String.format(INCIDENT_ID_EQ, incidentId));

      if (target != null) {
        sj.add(String.format(TARGET_EQ, target));
      }
      return sj.toString();
    }
  }
}
