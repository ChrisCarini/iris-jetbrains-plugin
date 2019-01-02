package com.chriscarini.jetbrains.iris.client.model;

import java.util.Locale;
import org.jetbrains.annotations.NonNls;
import org.junit.Test;

import static org.junit.Assert.*;


public class IncidentTest {
  @Test
  public void testBuilder() {
    Incident.RequestParameterBuilder builder = Incident.RequestParameterBuilder.create();
    assertNotNull(builder);

    @NonNls
    final String target = "FAFASDA";
    @NonNls
    final String fieldName = "FOO";
    final int created = 987654321;
    final Incident.RequestParameterBuilder.Operator operator = Incident.RequestParameterBuilder.Operator.EQ;
    final boolean active = false;
    final int limit = 100;
    final String request = builder.withIncidentId(123456)
        .withTarget(target)
        .isActive(active)
        .limitResultsTo(limit)
        .fetchField(fieldName)
        .created(operator, created)
        .build();

    assertTrue(request.contains(String.format(Incident.RequestParameterBuilder.TARGET_EQ, target)));
    assertTrue(request.contains(String.format(Incident.RequestParameterBuilder.ACTIVE_EQ, active ? 1 : 0)));
    assertTrue(request.contains(String.format(Incident.RequestParameterBuilder.LIMIT_EQ, limit)));
    assertTrue(request.contains(String.format(Incident.RequestParameterBuilder.CREATED_N_EQ, "", created)));
    assertTrue(request.contains(String.format(Incident.RequestParameterBuilder.FIELDS_EQ, fieldName)));
  }

  @Test
  public void testBuilderNonEQOperator() {
    Incident.RequestParameterBuilder builder = Incident.RequestParameterBuilder.create();
    assertNotNull(builder);

    final int created = 987654321;
    final Incident.RequestParameterBuilder.Operator operator = Incident.RequestParameterBuilder.Operator.LE;
    final String request = builder.created(operator, created).build();

    assertTrue(request.contains(String.format(Incident.RequestParameterBuilder.CREATED_N_EQ,
        String.format(Incident.RequestParameterBuilder.OPERATOR_FORMAT,
            operator.name().toLowerCase(Locale.getDefault())), created)));
  }
}