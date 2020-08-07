package jces1209.vu.page.admin.workflow

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.FalliblePage
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions.*
import java.time.Duration

class DcBrowseWorkflowsPage(
    private val jira: WebJira
) : BrowseWorkflowsPage(jira) {

    private val falliblePage = FalliblePage.Builder(
        jira.driver,
        and(
            visibilityOfElementLocated(By.id("active-workflows-table")),
            presenceOfAllElementsLocatedBy(By.className("cell-type-value")),
            presenceOfElementLocated(By.id("inactive-workflows-module-heading"))
        )
    )
        .serverErrors()
        .timeout(Duration.ofSeconds(30))
        .build()

    override fun waitForBeingLoaded(): DcBrowseWorkflowsPage {
        falliblePage.waitForPageToLoad()
        return this
    }
}
