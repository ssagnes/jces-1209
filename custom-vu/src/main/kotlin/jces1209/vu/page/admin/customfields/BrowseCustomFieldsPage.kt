package jces1209.vu.page.admin.customfields

import com.atlassian.performance.tools.jiraactions.api.WebJira

abstract class BrowseCustomFieldsPage(
    private val jira: WebJira
) {

    fun open(): BrowseCustomFieldsPage {
        jira.navigateTo("secure/admin/ViewCustomFields.jspa")
        return this
    }

    abstract fun waitForBeingLoaded(): BrowseCustomFieldsPage
}
