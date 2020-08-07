package jces1209.vu.page.admin.workflow

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.FalliblePage
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions.*
import java.time.Duration

class CloudBrowseWorkflowsPage(
    private val jira: WebJira
) : BrowseWorkflowsPage(jira) {

    private val falliblePage = FalliblePage.Builder(
        jira.driver,
        and(
            visibilityOfElementLocated(By.className("aui-page-panel")),
            visibilityOfElementLocated(By.id("active-workflows-module")),
            presenceOfAllElementsLocatedBy(By.cssSelector(".workflow-name, .workflow-schemes, .workflow-operations")),
            presenceOfElementLocated(By.id("inactive-workflows-module"))
        )
    )
        .cloudErrors()
        .timeout(Duration.ofSeconds(30))
        .build()

    override fun waitForBeingLoaded(): CloudBrowseWorkflowsPage {
        falliblePage.waitForPageToLoad()
        return this
    }
}
