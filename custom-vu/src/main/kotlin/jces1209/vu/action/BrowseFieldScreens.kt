package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import jces1209.vu.Measure
import jces1209.vu.MeasureType
import jces1209.vu.page.admin.fieldscreen.BrowseFieldScreensPage
import net.jcip.annotations.NotThreadSafe

class BrowseFieldScreens(
    private val measure: Measure,
    private val browseFieldScreensPage: BrowseFieldScreensPage
) : Action {

    override fun run() {
        measure.measure(MeasureType.BROWSE_FIELD_SCREENS) {
            browseFieldScreensPage
                .open()
                .waitForBeingLoaded()
        }
    }
}
