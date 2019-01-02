package com.chriscarini.jetbrains.iris.client.model;

import org.junit.Test;

import static org.junit.Assert.*;


public class StatsTest {

  @Test
  public void testToString() {
    final Stats stats = new Stats();
    assertNotNull(stats);

    assertEquals(
        "Stats{medianSecondsToClaimLastMonth=0.0, pctIncidentsClaimedLastMonth=0.0, totalActiveUsers=0.0, totalApplications=0.0, totalIncidents=0.0, totalIncidentsToday=0.0, totalMessagesSent=0.0, totalMessagesSentToday=0.0, totalPlans=0.0}",
        stats.toString());
  }

  @Test
  public void toHtml() {
    final Stats stats = new Stats();
    assertNotNull(stats);

    assertEquals("<html><body><table>\n"
        + "<tr><th scope='row' align='right' valign='top'>Median Seconds To Claim Last Month</th><td>0</td></tr>\n"
        + "<tr><th scope='row' align='right' valign='top'>% Incidents Claimed Last Month</th><td>0</td></tr>\n"
        + "<tr><th scope='row' align='right' valign='top'>Total Active Users</th><td>0</td></tr>\n"
        + "<tr><th scope='row' align='right' valign='top'>Total Applications</th><td>0</td></tr>\n"
        + "<tr><th scope='row' align='right' valign='top'>Total Incidents</th><td>0</td></tr>\n"
        + "<tr><th scope='row' align='right' valign='top'>Total Incidents Today</th><td>0</td></tr>\n"
        + "<tr><th scope='row' align='right' valign='top'>Total Messages Sent</th><td>0</td></tr>\n"
        + "<tr><th scope='row' align='right' valign='top'>Total Messages Sent Today</th><td>0</td></tr>\n"
        + "<tr><th scope='row' align='right' valign='top'>Total Plans</th><td>0</td></tr>\n" + "</table></body></html>", stats.toHtml());
  }
}