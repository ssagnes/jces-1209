package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.BROWSE_PROJECTS
import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import com.atlassian.performance.tools.jiraactions.api.memories.ProjectMemory
import jces1209.vu.page.issue.AbstractProjectIssueNavigatorPage
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ProjectIssueNavigatorAction(
    private val jira: WebJira,
    private val meter: ActionMeter,
    private val projectMemory: ProjectMemory,
    private val projectIssueNavigatorPage: AbstractProjectIssueNavigatorPage,
    private val numberOfProject: String

) : Action {
    private val logger: Logger = LogManager.getLogger(this::class.java)

    override fun run() {
        val project = projectMemory.recall()
        if (project == null) {
            logger.debug("Skipping Open project issue navigator...")
            return
        }
        meter.measure(BROWSE_PROJECTS) {
            jira.goToBrowseProjects(1).getProjects()
            projectIssueNavigatorPage.openProjectByIndex(numberOfProject)
        }
    }

}
