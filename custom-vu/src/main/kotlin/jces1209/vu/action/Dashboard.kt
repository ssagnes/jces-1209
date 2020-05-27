package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.ActionType
import com.atlassian.performance.tools.jiraactions.api.VIEW_DASHBOARD
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import com.atlassian.performance.tools.jiraactions.api.memories.Memory
import jces1209.vu.MeasureType
import jces1209.vu.page.dashboard.DashboardPage
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Dashboard(
    private val meter: ActionMeter,
    private val dashboardMemory: Memory<DashboardPage>

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
}

