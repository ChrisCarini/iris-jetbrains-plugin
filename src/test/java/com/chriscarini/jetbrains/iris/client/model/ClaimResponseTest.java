package com.chriscarini.jetbrains.iris.client.model;

import org.jetbrains.annotations.NonNls;
import org.junit.Test;

import static org.junit.Assert.*;


public class ClaimResponseTest {

  @Test
  public void getSetIncidentId() {
    final int incidentId = 1234567;

    ClaimResponse claimResponse = new ClaimResponse();
    claimResponse.setIncidentId(incidentId);

    assertEquals(incidentId, claimResponse.getIncidentId());
  }

  @Test
  public void getOwner() {
    @NonNls
    final String owner = "ASDASDASDSDASD";

    ClaimResponse claimResponse = new ClaimResponse();
    claimResponse.setOwner(owner);

    assertEquals(owner, claimResponse.getOwner());
  }

  @Test
  public void setActive() {
    final boolean isActive = true;

    ClaimResponse claimResponse = new ClaimResponse();
    claimResponse.setActive(isActive);

    assertEquals(isActive, claimResponse.isActive());
  }
}