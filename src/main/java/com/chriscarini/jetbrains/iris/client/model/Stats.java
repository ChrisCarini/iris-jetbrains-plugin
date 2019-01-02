package com.chriscarini.jetbrains.iris.client.model;

import com.chriscarini.jetbrains.iris.plugin.messages.IrisMessages;
import com.google.gson.annotations.SerializedName;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.jetbrains.annotations.NonNls;


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
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.median.seconds.to.claim.last.month"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.incidents.claimed.last.month"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.active.users"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.applications"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.incidents"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.incidents.today"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.messages.sent"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.messages.sent.today"), ROW_VAL) +
      String.format(ROW_HEADER, IrisMessages.get("iris.stats.table.total.plans"), ROW_VAL)
      + TABLE_BODY_HTML;
  // @formatter:on

  @SerializedName("median_seconds_to_claim_last_month") //NON-NLS
  private double medianSecondsToClaimLastMonth;
  @SerializedName("pct_incidents_claimed_last_month") //NON-NLS
  private double pctIncidentsClaimedLastMonth;
  @SerializedName("total_active_users") //NON-NLS
  private double totalActiveUsers;
  @SerializedName("total_applications") //NON-NLS
  private double totalApplications;
  @SerializedName("total_incidents") //NON-NLS
  private double totalIncidents;
  @SerializedName("total_incidents_today") //NON-NLS
  private double totalIncidentsToday;
  @SerializedName("total_messages_sent") //NON-NLS
  private double totalMessagesSent;
  @SerializedName("total_messages_sent_today") //NON-NLS
  private double totalMessagesSentToday;
  @SerializedName("total_plans") //NON-NLS
  private double totalPlans;

  @Override
  public String toString() {
    //noinspection HardCodedStringLiteral
    return "Stats{" + "medianSecondsToClaimLastMonth=" + medianSecondsToClaimLastMonth
        + ", pctIncidentsClaimedLastMonth=" + pctIncidentsClaimedLastMonth + ", totalActiveUsers=" + totalActiveUsers
        + ", totalApplications=" + totalApplications + ", totalIncidents=" + totalIncidents + ", totalIncidentsToday="
        + totalIncidentsToday + ", totalMessagesSent=" + totalMessagesSent + ", totalMessagesSentToday="
        + totalMessagesSentToday + ", totalPlans=" + totalPlans + '}';
  }

  public String toHtml() {
    // Define the maximum number of decimals (number of symbols #); shows symbol '.' instead of ','
    final DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));

    return String.format(HTML_FORMAT, df.format(medianSecondsToClaimLastMonth), df.format(pctIncidentsClaimedLastMonth),
        df.format(totalActiveUsers), df.format(totalApplications), df.format(totalIncidents),
        df.format(totalIncidentsToday), df.format(totalMessagesSent), df.format(totalMessagesSentToday),
        df.format(totalPlans));
  }
}
