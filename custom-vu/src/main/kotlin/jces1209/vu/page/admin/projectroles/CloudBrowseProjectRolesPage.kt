package jces1209.vu.page.admin.projectroles

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.FalliblePage
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions.*
import java.time.Duration

class CloudBrowseProjectRolesPage(
    private val jira: WebJira
) : BrowseProjectRolesPage(jira) {

    private val falliblePage = FalliblePage.Builder(
        jira.driver,
        loadingPageExpectedCondition
    )
        .cloudErrors()
        .timeout(Duration.ofSeconds(30))
        .build()

    override fun waitForBeingLoaded(): CloudBrowseProjectRolesPage {
        falliblePage.waitForPageToLoad()
        return this
    }
}
