package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import jces1209.vu.Measure
import jces1209.vu.MeasureType
import jces1209.vu.page.admin.customfields.BrowseCustomFieldsPage
import jces1209.vu.page.admin.workflow.BrowseWorkflowsPage
import net.jcip.annotations.NotThreadSafe

@NotThreadSafe
class BrowseCustomFields(
    private val measure: Measure,
    private val browseCustomFieldsPage: BrowseCustomFieldsPage
) : Action {

    override fun run() {
        measure.measure(MeasureType.BROWSE_CUSTOM_FIELDS) {
            browseCustomFieldsPage
                .open()
                .waitForBeingLoaded()
        }
    }
}
