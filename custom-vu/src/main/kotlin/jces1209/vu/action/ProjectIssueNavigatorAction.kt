package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.BROWSE_PROJECTS
import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import jces1209.vu.page.project.ProjectIssueNavigatorPage
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ProjectIssueNavigatorAction(
    private val jira: WebJira,
    private val meter: ActionMeter,
    private val projectIssueNavigatorPage: ProjectIssueNavigatorPage,
    private val numberOfProject: Int

) : Action {
    private val logger: Logger = LogManager.getLogger(this::class.java)

    override fun run() {

        meter.measure(BROWSE_PROJECTS) {
            jira.goToBrowseProjects(1).getProjects()
            projectIssueNavigatorPage.openProjectByIndex(numberOfProject)
        }
    }

}
