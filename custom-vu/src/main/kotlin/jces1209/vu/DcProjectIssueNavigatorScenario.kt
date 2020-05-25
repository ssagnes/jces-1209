package jces1209.vu

import com.atlassian.performance.tools.jiraactions.api.SeededRandom
import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import com.atlassian.performance.tools.jiraactions.api.memories.UserMemory
import com.atlassian.performance.tools.jiraactions.api.scenario.JiraCoreScenario
import com.atlassian.performance.tools.jiraactions.api.scenario.Scenario
import jces1209.vu.action.ProjectIssueNavigatorAction
import jces1209.vu.page.issue.DcProjectIssueNavigatorPage
import org.openqa.selenium.TakesScreenshot

class DcProjectIssueNavigatorScenario : Scenario {

    override fun getLogInAction(
        jira: WebJira,
        meter: ActionMeter,
        userMemory: UserMemory
    ): Action {
        return JiraCoreScenario().getLogInAction(jira, meter, userMemory)
    }

    override fun getActions(
        jira: WebJira,
        seededRandom: SeededRandom,
        actionMeter: ActionMeter
    ): List<Action> {
        val meter = ActionMeter.Builder(actionMeter)
            .appendPostMetricHook(
                TakeScreenshotHook.Builder(
                    jira.driver as TakesScreenshot
                ).build())
            .build()
        val similarities = ScenarioSimilarities(jira, seededRandom, meter)
        return similarities.assembleScenarioProjectIssueNavigator(

            projectIssueNavigatorAction = ProjectIssueNavigatorAction(
                jira = jira,
                meter = meter,
                projectMemory = similarities.projectMemory,
                projectIssueNavigatorPage = DcProjectIssueNavigatorPage(jira.driver),
                numberOfProject= "3"
            )
        )
    }
}
