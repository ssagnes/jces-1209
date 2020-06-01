package jces1209.vu.page.dashboard.dc

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.dashboard.DashboardPage

class DcDashboardPage(
    private val jira: WebJira
) : DashboardPage(
    driver = jira.driver,
    uri = jira.base.resolve("/secure/ConfigurePortalPages.jspa?view=popular")) {
}



