package jces1209.vu.page.dashboard

import com.atlassian.performance.tools.jiraactions.api.WebJira
import org.openqa.selenium.WebDriver

class CloudDashboardPage(
    private val driver: WebDriver,
    private val jira: WebJira
) : DashboardPage {


    override fun openDashboardsPage() {
        jira.navigateTo("/secure/ConfigurePortalPages!default.jspa")


    }

    override fun createDashboard() {
        TODO("Not yet implemented")
    }

    override fun createGadget() {
        TODO("Not yet implemented")
    }

    override fun loadGadget() {
        TODO("Not yet implemented")
    }
}
