package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import jces1209.vu.Measure
import jces1209.vu.MeasureType
import jces1209.vu.page.admin.fieldconfigs.BrowseFieldConfigurationsPage
import net.jcip.annotations.NotThreadSafe

@NotThreadSafe
class BrowseFieldConfigurations(
    private val measure: Measure,
    private val browseFieldConfigurationsPage: BrowseFieldConfigurationsPage
) : Action {

    override fun run() {
        measure.measure(MeasureType.BROWSE_FIELD_CONFIGURATIONS) {
            browseFieldConfigurationsPage
                .open()
                .waitForBeingLoaded()
        }
    }
}
