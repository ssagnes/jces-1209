package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import jces1209.vu.MeasureType
import jces1209.vu.page.admin.projectroles.BrowseProjectRolesPage
import net.jcip.annotations.NotThreadSafe

class BrowseProjectRoles(
    private val meter: ActionMeter,
    private val browseProjectRolesPage: BrowseProjectRolesPage
) : Action {

    override fun run() {
        meter.measure(MeasureType.BROWSE_PROJECT_ROLES) {
            browseProjectRolesPage
                .open()
                .waitForBeingLoaded()
        }
    }
}
