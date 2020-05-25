package jces1209.vu

import com.atlassian.performance.tools.jiraactions.api.SeededRandom
import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import com.atlassian.performance.tools.jiraactions.api.memories.UserMemory
import com.atlassian.performance.tools.jiraactions.api.scenario.Scenario
import jces1209.vu.action.LogInWithAtlassianId
import jces1209.vu.action.ProjectIssueNavigatorAction
import jces1209.vu.page.issue.CloudProjectIssueNavigatorPage
import org.openqa.selenium.TakesScreenshot

class CloudProjectIssueNavigatorScenario : Scenario {

    override fun getLogInAction(
        jira: WebJira,
        meter: ActionMeter,
        userMemory: UserMemory
    ): Action {
        val user = userMemory
            .recall()
            ?: throw Exception("I cannot recall which user I am")
        return LogInWithAtlassianId(user, jira, meter)
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
                projectIssueNavigatorPage = CloudProjectIssueNavigatorPage(jira.driver),
                numberOfProject = "5"
            )
        )
    }
}
