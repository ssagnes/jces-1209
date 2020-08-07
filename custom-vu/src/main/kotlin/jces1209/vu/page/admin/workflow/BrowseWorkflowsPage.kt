package jces1209.vu.page.admin.workflow

import com.atlassian.performance.tools.jiraactions.api.WebJira

abstract class BrowseWorkflowsPage(
    private val jira: WebJira
) {

    fun open(): BrowseWorkflowsPage {
        jira.navigateTo("secure/admin/workflows/ListWorkflows.jspa")
        return this
    }

    abstract fun waitForBeingLoaded(): BrowseWorkflowsPage
}
