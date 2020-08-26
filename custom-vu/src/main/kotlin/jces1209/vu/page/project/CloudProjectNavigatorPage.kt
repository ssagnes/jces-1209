package jces1209.vu.page.project

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.issuenavigator.CloudIssueNavigator


class CloudProjectNavigatorPage(
    private val jira: WebJira
) : ProjectNavigatorPage {

    override fun openProject(projectKey: String): CloudProjectNavigatorPage {
        jira.navigateTo("/jira/software/c/projects/$projectKey/issues?filter=allissues")
        return this
    }

    override fun waitForNavigator() {
        CloudIssueNavigator(jira).waitForBeingLoaded()
    }
}
