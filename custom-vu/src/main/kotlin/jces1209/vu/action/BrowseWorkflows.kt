package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import jces1209.vu.MeasureType
import jces1209.vu.page.admin.workflow.BrowseWorkflowsPage
import net.jcip.annotations.NotThreadSafe

@NotThreadSafe
class BrowseWorkflows(
    private val meter: ActionMeter,
    private val browseWorkflowsPage: BrowseWorkflowsPage
) : Action {

    override fun run() {
        meter.measure(MeasureType.BROWSE_WORKFLOWS) {
            browseWorkflowsPage
                .open()
                .waitForBeingLoaded()
        }
    }
}
