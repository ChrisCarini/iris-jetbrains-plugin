package com.chriscarini.jetbrains.iris.client.model;

import org.jetbrains.annotations.NonNls;
import org.junit.Test;

import static org.junit.Assert.*;


public class MessageTest {
  @Test
  public void testBuilder() {
    Message.RequestParameterBuilder builder = Message.RequestParameterBuilder.create();
    assertNotNull(builder);

    @NonNls
    final String target = "FAFASDA";
    final int incidentId = 123456;
    final String request = builder.withIncidentId(incidentId).withTarget(target).build();

    assertTrue(request.contains(String.format(Message.RequestParameterBuilder.TARGET_EQ, target)));
    assertTrue(request.contains(String.format(Message.RequestParameterBuilder.INCIDENT_ID_EQ, incidentId)));
  }
}