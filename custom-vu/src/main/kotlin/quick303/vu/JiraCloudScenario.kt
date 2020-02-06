package quick303.vu

import com.atlassian.performance.tools.jiraactions.api.SeededRandom
import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.action.ProjectSummaryAction
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import com.atlassian.performance.tools.jiraactions.api.memories.UserMemory
import com.atlassian.performance.tools.jiraactions.api.memories.adaptive.AdaptiveIssueKeyMemory
import com.atlassian.performance.tools.jiraactions.api.memories.adaptive.AdaptiveJqlMemory
import com.atlassian.performance.tools.jiraactions.api.memories.adaptive.AdaptiveProjectMemory
import com.atlassian.performance.tools.jiraactions.api.scenario.Scenario
import org.openqa.selenium.By
import quick303.vu.action.BrowseCloudProjects
import quick303.vu.action.CreateAnIssue
import quick303.vu.action.JiraCloudLogIn
import quick303.vu.action.SearchCloudJql
import quick303.vu.action.WorkAnIssue
import quick303.vu.page.CloudIssuePage
import java.util.Collections

class JiraCloudScenario : Scenario {

    override fun getLogInAction(
        jira: WebJira,
        meter: ActionMeter,
        userMemory: UserMemory
    ): Action {
        return JiraCloudLogIn(userMemory, jira, meter)
    }

    override fun getActions(
        jira: WebJira,
        seededRandom: SeededRandom,
        meter: ActionMeter
    ): List<Action> {
        val jqlMemory = AdaptiveJqlMemory(seededRandom)
            .also { it.remember(listOf("order by created DESC")) } // work around https://ecosystem.atlassian.net/browse/JPERF-573
        val issueKeyMemory = AdaptiveIssueKeyMemory(seededRandom)
        val projectMemory = AdaptiveProjectMemory(seededRandom)
        val issuePage = CloudIssuePage(jira.driver)
        val createIssue = CreateAnIssue(
            jira = jira,
            meter = meter,
            projectMemory = projectMemory,
            createIssueButton = By.id("createGlobalItem")
        )
        val searchWithJql = SearchCloudJql(
            jira = jira,
            meter = meter,
            jqlMemory = jqlMemory,
            issueKeyMemory = issueKeyMemory
        )
        val browseProjects = BrowseCloudProjects(
            jira = jira,
            meter = meter,
            projectMemory = projectMemory
        )
        val workAnIssue = WorkAnIssue(
            issuePage = issuePage,
            jira = jira,
            meter = meter,
            issueKeyMemory = issueKeyMemory,
            random = seededRandom,
            commentProbability = 0.04f
        )
        val projectSummary = ProjectSummaryAction(
            jira = jira,
            meter = meter,
            projectMemory = projectMemory
        )
        return mapOf(
            createIssue to 5,
            searchWithJql to 20,
            workAnIssue to 55,
            projectSummary to 5,
            browseProjects to 5
        )
            .map { (action, proportion) -> Collections.nCopies(proportion, action) }
            .flatten()
            .shuffled(seededRandom.random)
    }
}
