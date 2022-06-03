package com.chriscarini.jetbrains.iris.client.model;

import com.chriscarini.jetbrains.iris.plugin.messages.IrisMessages;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NonNls;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Model representation of the result of the Iris API {@code /v0/stats} endpoint.
 */
public class Stats {
  @NonNls
  private static final String ROW_HEADER = "<tr><th scope='row' align='right' valign='top'>%s</th>%s";
  @NonNls
  private static final String ROW_VAL = "<td>%s</td></tr>\n";
  @NonNls
  private static final String HTML_BODY_TABLE = "<html><body><table>\n";
  @NonNls
  private static final String TABLE_BODY_HTML = "</table></body></html>";
  // @formatter:off
  private static final String HTML_FORMAT = HTML_BODY_TABLE +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.median.seconds.to.claim.last.week"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.incidents.claimed.last.week"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.active.users"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.applications"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.high.priority.incidents.last.week"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.incidents"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.incidents.last.week"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.messages.sent"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.messages.sent.last.week"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.plans"), ROW_VAL)
      + TABLE_BODY_HTML;
  // @formatter:on

  // Define the maximum number of decimals (number of symbols #); shows symbol '.' instead of ','
  private static final DecimalFormat DF = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));

  @SerializedName("median_seconds_to_claim_last_week") //NON-NLS
  private List<Map<String, Double>> medianSecondsToClaimLastWeek;
  @SerializedName("pct_incidents_claimed_last_week") //NON-NLS
  private List<Map<String, Double>> pctIncidentsClaimedLastWeek;
  @SerializedName("total_active_users") //NON-NLS
  private List<Map<String, Double>> totalActiveUsers;
  @SerializedName("total_applications") //NON-NLS
  private List<Map<String, Double>> totalApplications;
  @SerializedName("total_high_priority_incidents_last_week") //NON-NLS
  private List<Map<String, Double>> totalHighPriorityIncidentsLastWeek;
  @SerializedName("total_incidents") //NON-NLS
  private List<Map<String, Double>> totalIncidents;
  @SerializedName("total_incidents_last_week") //NON-NLS
  private List<Map<String, Double>> totalIncidentsLastWeek;
  @SerializedName("total_messages_sent") //NON-NLS
  private List<Map<String, Double>> totalMessagesSent;
  @SerializedName("total_messages_sent_last_week") //NON-NLS
  private List<Map<String, Double>> totalMessagesSentLastWeek;
  @SerializedName("total_plans") //NON-NLS
  private List<Map<String, Double>> totalPlans;

  @Override
  public String toString() {
    return "Stats{" +
        "medianSecondsToClaimLastWeek=" + medianSecondsToClaimLastWeek +
        ", pctIncidentsClaimedLastWeek=" + pctIncidentsClaimedLastWeek +
        ", totalActiveUsers=" + totalActiveUsers +
        ", totalApplications=" + totalApplications +
        ", totalIncidents=" + totalIncidents +
        ", totalHighPriorityIncidentsLastWeek=" + totalHighPriorityIncidentsLastWeek +
        ", totalIncidentsLastWeek=" + totalIncidentsLastWeek +
        ", totalMessagesSent=" + totalMessagesSent +
        ", totalMessagesSentLastWeek=" + totalMessagesSentLastWeek +
        ", totalPlans=" + totalPlans +
        '}';
  }

  public String toHtml() {
    return String.format(HTML_FORMAT,
        valuesToRowCells(medianSecondsToClaimLastWeek),
        valuesToRowCells(pctIncidentsClaimedLastWeek),
        valuesToRowCells(totalActiveUsers),
        valuesToRowCells(totalApplications),
        valuesToRowCells(totalHighPriorityIncidentsLastWeek),
        valuesToRowCells(totalIncidents),
        valuesToRowCells(totalIncidentsLastWeek),
        valuesToRowCells(totalMessagesSent),
        valuesToRowCells(totalMessagesSentLastWeek),
        valuesToRowCells(totalPlans)
    );
  }

  private String valuesToRowCells(final List<Map<String, Double>> listOfMaps) {
    return listOfMaps.stream().map(a -> {
      // There should only be a single value in the result, and if it is null, treat it as a zero.
      if (a.values().toArray()[0] == null) {
        return 0.0;
      }
      return a.values().stream().findFirst().orElse(0.0);
    }).map(DF::format).collect(Collectors.joining("</td><td>"));
  }
}
