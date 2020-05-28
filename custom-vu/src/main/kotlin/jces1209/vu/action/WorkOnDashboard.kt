package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.ActionType
import com.atlassian.performance.tools.jiraactions.api.VIEW_DASHBOARD
import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import com.atlassian.performance.tools.jiraactions.api.memories.Memory
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
    private val dashboardMemory: Memory<DashboardPage>,
    private val dashboardPage: DashboardPage

) : Action {
    private val logger: Logger = LogManager.getLogger(this::class.java)

    override fun run() {
        val dashboard = dashboardMemory.recall()
        if (dashboard == null) {
            logger.debug("I cannot recall dashboard, skipping...")
            return
        }


        val dashboardContent = meter.measure(
            key = VIEW_DASHBOARD,
            action = {
                meter.measure(
                    key = ActionType("View dashboards") { Unit },
                    action = {
                        dashboard
                            .openDashboardsPage()
                            .waitForDashboards()
                    }
                )
            }
        )
    }

    private fun getProjectKey(): String {
        jira.driver.navigate().to("/projects")
        val projectList = JiraCloudProjectList(jira.driver)
        val progectKey = projectList.listProjects().last().key
        return progectKey
    }

    private fun createDashboard(dashboard: DashboardPage) {
        dashboard
            .openDashboardsPage()
            .waitForDashboards()
        val createDashboard = meter.measure(
            key = CREATE_DASHBOARD,
            action = {
                dashboardPage.createDashboard()
            }
        )
    }

    private fun createGadget(dashboard: DashboardPage) {
        val projectKey = getProjectKey()
        dashboardPage.createDashboard()
        val createGadget = meter.measure(
            key = CREATE_GADGET,
            action = {
                meter.measure(
                    key = ActionType("Create gadget") { Unit },
                    action = {
                        dashboard.createGadget(projectKey)
                    }
                )
            }
        )
    }

    private fun loadGadget(dashboard: DashboardPage) {
        dashboard
            .openDashboardsPage()
            .waitForDashboards()
        val loadGadget = meter.measure(
            key = LOAD_GADGET,
            action = {
                meter.measure(
                    key = ActionType("Load gadget") { Unit },
                    action = {
                        dashboard.loadGadget()
                    }
                )
            }
        )
    }
}

