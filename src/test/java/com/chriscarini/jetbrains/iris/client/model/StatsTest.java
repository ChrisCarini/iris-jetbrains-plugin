package com.chriscarini.jetbrains.iris.client.model;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class StatsTest {

  @Test
  public void testToStringEmpty() {
    // given
    final Stats stats = new Stats();

    // when
    final String result = stats.toString();

    // then
    assertNotNull(stats);

    assertEquals(
        "Stats{medianSecondsToClaimLastWeek=null, pctIncidentsClaimedLastWeek=null, totalActiveUsers=null, totalApplications=null, totalIncidents=null, totalHighPriorityIncidentsLastWeek=null, totalIncidentsLastWeek=null, totalMessagesSent=null, totalMessagesSentLastWeek=null, totalPlans=null}",
        result
    );
  }

  @Test
  public void testToString() {
    // given
    final Stats stats = new Stats();
    stats.medianSecondsToClaimLastWeek = List.of(Map.of("123456789", 123.4));
    stats.pctIncidentsClaimedLastWeek = List.of(Map.of("123456789", 234.5));
    stats.totalActiveUsers = List.of(Map.of("123456789", 345.0));
    stats.totalApplications = List.of(Map.of("123456789", 456.0));
    stats.totalHighPriorityIncidentsLastWeek = List.of(Map.of("123456789", 567.8));
    stats.totalIncidents = List.of(Map.of("123456789", 678.0));
    stats.totalIncidentsLastWeek = List.of(Map.of("123456789", 789.0));
    stats.totalMessagesSent = List.of(Map.of("123456789", 890.0));
    stats.totalMessagesSentLastWeek = List.of(Map.of("123456789", 901.0));
    stats.totalPlans = List.of(Map.of("123456789", 12.0));

    // when
    final String result = stats.toString();

    // then

    assertNotNull(stats);

    assertEquals(
        "Stats{" +
            "medianSecondsToClaimLastWeek=[{123456789=123.4}], " +
            "pctIncidentsClaimedLastWeek=[{123456789=234.5}], " +
            "totalActiveUsers=[{123456789=345.0}], " +
            "totalApplications=[{123456789=456.0}], " +
            "totalIncidents=[{123456789=678.0}], " +
            "totalHighPriorityIncidentsLastWeek=[{123456789=567.8}], " +
            "totalIncidentsLastWeek=[{123456789=789.0}], " +
            "totalMessagesSent=[{123456789=890.0}], " +
            "totalMessagesSentLastWeek=[{123456789=901.0}], " +
            "totalPlans=[{123456789=12.0}]" +
            "}",
        result
    );
  }

  @Test
  public void toHtmlEmpty() {
    // given
    final Stats stats = new Stats();

    // when
    final String result = stats.toHtml();

    // then
    assertNotNull(stats);

    assertEquals("<html><body><table>\n" +
            "<tr><th scope='row' align='right' valign='top'>Median Seconds To Claim Last Week</th><td>N/A</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>% Incidents Claimed Last Week</th><td>N/A</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Active Users</th><td>N/A</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Applications</th><td>N/A</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total High Priority Incidents Last Week</th><td>N/A</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Incidents</th><td>N/A</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Incidents Last Week</th><td>N/A</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Messages Sent</th><td>N/A</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Messages Sent Last Week</th><td>N/A</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Plans</th><td>N/A</td></tr>\n" +
            "</table></body></html>",
        result
    );
  }

  @Test
  public void toHtml() {
    // given
    final Stats stats = new Stats();
    stats.medianSecondsToClaimLastWeek = List.of(
        Map.of("123456789", 123.4),
        Map.of("123456790", 234.5),
        Map.of("123456791", 345.0),
        Map.of("123456792", 456.0),
        Map.of("123456793", 567.8),
        Map.of("123456794", 678.0),
        Map.of("123456795", 789.0),
        Map.of("123456796", 890.0)
    );
    stats.pctIncidentsClaimedLastWeek = List.of(
        Map.of("123456789", 234.5),
        Map.of("123456790", 345.0),
        Map.of("123456791", 456.0),
        Map.of("123456792", 567.8),
        Map.of("123456793", 678.0),
        Map.of("123456794", 789.0),
        Map.of("123456795", 890.0),
        Map.of("123456796", 123.4)
    );
    stats.totalActiveUsers = List.of(
        Map.of("123456789", 345.0),
        Map.of("123456790", 456.0),
        Map.of("123456791", 567.8),
        Map.of("123456792", 678.0),
        Map.of("123456793", 789.0),
        Map.of("123456794", 890.0),
        Map.of("123456795", 123.4),
        Map.of("123456796", 234.5)
    );
    stats.totalApplications = List.of(
        Map.of("123456789", 456.0),
        Map.of("123456790", 567.8),
        Map.of("123456791", 678.0),
        Map.of("123456792", 789.0),
        Map.of("123456793", 890.0),
        Map.of("123456794", 123.4),
        Map.of("123456795", 234.5),
        Map.of("123456796", 345.0)
    );
    stats.totalHighPriorityIncidentsLastWeek = List.of(
        Map.of("123456789", 567.8),
        Map.of("123456790", 678.0),
        Map.of("123456791", 789.0),
        Map.of("123456792", 890.0),
        Map.of("123456793", 123.4),
        Map.of("123456794", 234.5),
        Map.of("123456795", 345.0),
        Map.of("123456796", 456.0)
    );
    stats.totalIncidents = List.of(
        Map.of("123456789", 678.0),
        Map.of("123456790", 789.0),
        Map.of("123456791", 890.0),
        Map.of("123456792", 123.4),
        Map.of("123456793", 234.5),
        Map.of("123456794", 345.0),
        Map.of("123456795", 456.0),
        Map.of("123456796", 567.8)
    );
    stats.totalIncidentsLastWeek = List.of(
        Map.of("123456789", 789.0),
        Map.of("123456790", 890.0),
        Map.of("123456791", 123.4),
        Map.of("123456792", 234.5),
        Map.of("123456793", 345.0),
        Map.of("123456794", 456.0),
        Map.of("123456795", 567.8),
        Map.of("123456796", 678.0)
    );
    stats.totalMessagesSent = List.of(
        Map.of("123456789", 890.0),
        Map.of("123456790", 123.4),
        Map.of("123456791", 234.5),
        Map.of("123456792", 345.0),
        Map.of("123456793", 456.0),
        Map.of("123456794", 567.8),
        Map.of("123456795", 678.0),
        Map.of("123456796", 789.0)
    );
    stats.totalMessagesSentLastWeek = List.of(
        Map.of("123456789", 123.4),
        Map.of("123456790", 234.5),
        Map.of("123456791", 345.0),
        Map.of("123456792", 456.0),
        Map.of("123456793", 567.8),
        Map.of("123456794", 678.0),
        Map.of("123456795", 789.0),
        Map.of("123456796", 890.0)
    );
    stats.totalPlans = List.of(
        Map.of("123456789", 234.5),
        Map.of("123456790", 345.0),
        Map.of("123456791", 456.0),
        Map.of("123456792", 567.8),
        Map.of("123456793", 678.0),
        Map.of("123456794", 789.0),
        Map.of("123456795", 890.0),
        Map.of("123456796", 123.4)
    );

    // when
    final String result = stats.toHtml();

    // then
    assertNotNull(stats);

    assertEquals("<html><body><table>\n" +
            "<tr><th scope='row' align='right' valign='top'>Median Seconds To Claim Last Week</th><td>123.4</td><td>234.5</td><td>345</td><td>456</td><td>567.8</td><td>678</td><td>789</td><td>890</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>% Incidents Claimed Last Week</th><td>234.5</td><td>345</td><td>456</td><td>567.8</td><td>678</td><td>789</td><td>890</td><td>123.4</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Active Users</th><td>345</td><td>456</td><td>567.8</td><td>678</td><td>789</td><td>890</td><td>123.4</td><td>234.5</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Applications</th><td>456</td><td>567.8</td><td>678</td><td>789</td><td>890</td><td>123.4</td><td>234.5</td><td>345</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total High Priority Incidents Last Week</th><td>567.8</td><td>678</td><td>789</td><td>890</td><td>123.4</td><td>234.5</td><td>345</td><td>456</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Incidents</th><td>678</td><td>789</td><td>890</td><td>123.4</td><td>234.5</td><td>345</td><td>456</td><td>567.8</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Incidents Last Week</th><td>789</td><td>890</td><td>123.4</td><td>234.5</td><td>345</td><td>456</td><td>567.8</td><td>678</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Messages Sent</th><td>890</td><td>123.4</td><td>234.5</td><td>345</td><td>456</td><td>567.8</td><td>678</td><td>789</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Messages Sent Last Week</th><td>123.4</td><td>234.5</td><td>345</td><td>456</td><td>567.8</td><td>678</td><td>789</td><td>890</td></tr>\n" +
            "<tr><th scope='row' align='right' valign='top'>Total Plans</th><td>234.5</td><td>345</td><td>456</td><td>567.8</td><td>678</td><td>789</td><td>890</td><td>123.4</td></tr>\n" +
            "</table></body></html>",
        result
    );
  }
}