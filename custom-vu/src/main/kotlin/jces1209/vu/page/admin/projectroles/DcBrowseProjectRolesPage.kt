package jces1209.vu.page.admin.projectroles

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.FalliblePage
import java.time.Duration

class DcBrowseProjectRolesPage(
    private val jira: WebJira
) : BrowseProjectRolesPage(jira) {

    private val falliblePage = FalliblePage.Builder(
        jira.driver,
        loadingPageExpectedCondition
    )
        .serverErrors()
        .timeout(Duration.ofSeconds(30))
        .build()

    override fun waitForBeingLoaded(): DcBrowseProjectRolesPage {
        falliblePage.waitForPageToLoad()
        return this
    }
}
