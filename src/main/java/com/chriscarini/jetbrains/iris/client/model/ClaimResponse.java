package com.chriscarini.jetbrains.iris.client.model;

/**
 * Model representation of the response for claiming an Iris incident.
 *
 * **NOTE** Not yet tested, as it seems Iris has no good way to claim an incident
 *          without being an 'application' (vs just a user). Bummer.
 */
public class ClaimResponse {
  private long incidentId;
  private String owner;
  private boolean active;

  public long getIncidentId() {
    return incidentId;
  }

  protected void setIncidentId(final long incidentId) {
    this.incidentId = incidentId;
  }

  public String getOwner() {
    return owner;
  }

  protected void setOwner(final String owner) {
    this.owner = owner;
  }

  public boolean isActive() {
    return active;
  }

  protected void setActive(final boolean active) {
    this.active = active;
  }
}
