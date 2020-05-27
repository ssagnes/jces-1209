package jces1209.vu.page.dashboard.cloud

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.dashboard.DashboardPage

class CloudDashboardPage(

    private val jira: WebJira
) : DashboardPage {


    override fun openDashboardsPage() {
        jira.navigateTo("/secure/ConfigurePortalPages!default.jspa")
   }

    override fun waitForDashboards() {
        TODO("Not yet implemented")
    }


}
