package jces1209.vu.page.admin.fieldscreen

import com.atlassian.performance.tools.jiraactions.api.WebJira

abstract class BrowseFieldScreensPage(
    private val jira: WebJira
) {

    fun open(): BrowseFieldScreensPage {
            jira.navigateTo("secure/admin/ViewFieldScreens.jspa")
        return this
    }

    abstract fun waitForBeingLoaded(): BrowseFieldScreensPage
}
