package jces1209.vu.page.dashboard.cloud

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.dashboard.DashboardPage

class CloudDashboardPage(

    private val jira: WebJira
) : DashboardPage(
    driver = jira.driver,
    uri = jira.base.resolve("/secure/ConfigurePortalPages!default.jspa")) {

    override fun waitForDashboards() {
        TODO("Not yet implemented")
    }

    override fun createDashboard() {
        TODO("Not yet implemented")
    }

    override fun loadGadget() {
        TODO("Not yet implemented")
    }

    override fun createGadget() {
        TODO("Not yet implemented")
    }
}
