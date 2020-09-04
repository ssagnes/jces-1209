package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import com.atlassian.performance.tools.jiraactions.api.memories.UserMemory
import com.atlassian.performance.tools.jiraactions.api.scenario.JiraCoreScenario
import jces1209.vu.page.DcAdminLogInPage
import jces1209.vu.page.admin.workflow.browse.DcBrowseWorkflowsPage

class LogInToDc(
    private val jira: WebJira,
    private val meter: ActionMeter,
    private val userMemory: UserMemory
) : Action {
    private val browseWorkflowsPage = DcBrowseWorkflowsPage(jira)

    override fun run() {
        val user = userMemory
            .recall()
            ?: throw Exception("I cannot recall which user I am")

        JiraCoreScenario().getLogInAction(jira, meter, userMemory).run()
        browseWorkflowsPage.open()
        DcAdminLogInPage(jira.driver, user).logIn()
        browseWorkflowsPage.waitForBeingLoaded()
    }
}
