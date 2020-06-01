package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.VIEW_DASHBOARD
import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import jces1209.vu.MeasureType.Companion.CREATE_DASHBOARD
import jces1209.vu.MeasureType.Companion.CREATE_GADGET
import jces1209.vu.MeasureType.Companion.LOAD_GADGET
import jces1209.vu.page.JiraCloudProjectList
import jces1209.vu.page.dashboard.DashboardPage
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class WorkOnDashboard(
    private val jira: WebJira,
    private val meter: ActionMeter,
    private val dashboardPage: DashboardPage

) : Action {
    private val logger: Logger = LogManager.getLogger(this::class.java)

    override fun run() {

        meter.measure(
            key = VIEW_DASHBOARD,
            action = {
                openDashboardsPage().waitForDashboards()
            }
        )

        createDashboard(dashboardPage)
        createGadget(dashboardPage)
        loadGadget(dashboardPage)
    }

    private fun openDashboardsPage(): DashboardPage {
        jira.driver.navigate().to("/secure/Dashboard.jspa")
        return dashboardPage
    }

    private fun getProjectKey(): String {
        jira.driver.navigate().to("/projects")
        val projectList = JiraCloudProjectList(jira.driver)
        val progectKey = projectList.listProjects().last().key
        return progectKey
    }

    private fun createDashboard(dashboard: DashboardPage) {
        openDashboardsPage().waitForDashboards()
        meter.measure(
            key = CREATE_DASHBOARD,
            action = {
                dashboardPage.createDashboard()
            }
        )
    }

    private fun createGadget(dashboard: DashboardPage) {
        val projectKey = getProjectKey()
        dashboard.createDashboard()
        meter.measure(
            key = CREATE_GADGET,
            action = {
                dashboard.createGadget(projectKey)
            }
        )
    }


    private fun loadGadget(dashboard: DashboardPage) {
        openDashboardsPage().waitForDashboards()
        meter.measure(
            key = LOAD_GADGET,
            action = {
                dashboard.loadGadget()
            }
        )

    }

}
