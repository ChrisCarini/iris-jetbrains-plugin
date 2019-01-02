package com.chriscarini.jetbrains.iris.client.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;


/**
 * Model representation of a single Iris incident.
 */
public class Incident {
  private long updated;
  private long planId;
  private long created;
  private String owner;
  private String target;
  private String application;
  private String plan;
  private int active;
  private long id;
  private long currentStep;

  public long getUpdated() {
    return updated;
  }

  public long getPlanId() {
    return planId;
  }

  public long getCreated() {
    return created;
  }

  public String getOwner() {
    return owner;
  }

  public String getTarget() {
    return target;
  }

  public String getApplication() {
    return application;
  }

  public String getPlan() {
    return plan;
  }

  public int getActive() {
    return active;
  }

  public long getId() {
    return id;
  }

  public long getCurrentStep() {
    return currentStep;
  }

  public static class RequestParameterBuilder {
    @SuppressWarnings("WeakerAccess")
    @NonNls
    protected static final String TARGET = "target";
    @SuppressWarnings("WeakerAccess")
    @NonNls
    protected static final String TARGET_EQ = "target=%s";
    @SuppressWarnings("WeakerAccess")
    @NonNls
    protected static final String ACTIVE_EQ = "active=%d";
    @SuppressWarnings("WeakerAccess")
    @NonNls
    protected static final String LIMIT_EQ = "limit=%d";
    @SuppressWarnings("WeakerAccess")
    @NonNls
    protected static final String CREATED_N_EQ = "created%s=%d";
    @SuppressWarnings("WeakerAccess")
    @NonNls
    protected static final String OPERATOR_FORMAT = "__%s";
    @SuppressWarnings("WeakerAccess")
    @NonNls
    protected static final String FIELDS_EQ = "fields=%s";
    private long incidentId;
    private String target = null;
    private boolean active;
    private List<String> fieldNames = new ArrayList<>(Collections.singletonList(TARGET));
    private int limit;
    private long created = 0;
    private Operator createdOperator = Operator.EQ;

    private RequestParameterBuilder() {
    }

    public static RequestParameterBuilder create() {
      return new RequestParameterBuilder();
    }

    public RequestParameterBuilder withIncidentId(final long incidentId) {
      this.incidentId = incidentId;
      return this;
    }

    public RequestParameterBuilder withTarget(@NotNull final String target) {
      this.target = target;
      return this;
    }

    public RequestParameterBuilder isActive(final boolean active) {
      this.active = active;
      return this;
    }

    public RequestParameterBuilder limitResultsTo(final int limit) {
      this.limit = limit;
      return this;
    }

    public RequestParameterBuilder fetchField(@NotNull final String fieldName) {
      return fetchFields(Collections.singleton(fieldName));
    }

    public RequestParameterBuilder fetchFields(@NotNull final Collection<String> fieldNames) {
      this.fieldNames.addAll(fieldNames);
      return this;
    }

    public RequestParameterBuilder created(@NotNull final Operator operator, final long created) {
      this.created = created;
      this.createdOperator = operator;
      return this;
    }

    public String build() {
      final StringJoiner sj = new StringJoiner("&");
      if (target != null) {
        sj.add(String.format(TARGET_EQ, target));
      }
      sj.add(String.format(ACTIVE_EQ, active ? 1 : 0));
      sj.add(String.format(LIMIT_EQ, limit));

      if (created > 0) {
        final String operator = Operator.EQ.equals(createdOperator) ? ""
            : String.format(OPERATOR_FORMAT, createdOperator.name().toLowerCase(Locale.getDefault()));
        sj.add(String.format(CREATED_N_EQ, operator, created));
      }

      for (final String fieldName : this.fieldNames) {
        sj.add(String.format(FIELDS_EQ, fieldName));
      }

      return sj.toString();
    }

    public enum Operator {
      EQ, GT, GE, LT, LE;
    }
  }
}
