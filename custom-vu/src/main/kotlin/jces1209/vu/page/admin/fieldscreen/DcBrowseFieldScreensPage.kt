package jces1209.vu.page.admin.fieldscreen

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.FalliblePage
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions.*
import java.time.Duration

class DcBrowseFieldScreensPage(
    jira: WebJira
) : BrowseFieldScreensPage(jira) {

    private val falliblePage = FalliblePage.Builder(
        jira.driver,
        and(
            visibilityOfElementLocated(By.id("add-field-screen")),
            visibilityOfElementLocated(By.id("field-screens-table")),
            presenceOfAllElementsLocatedBy(By.cssSelector("[data-field-screen-id]")),
            presenceOfElementLocated(By.cssSelector(".field-screen-name, .operations-list"))
        )
    )
        .serverErrors()
        .timeout(Duration.ofSeconds(60))
        .build()

    override fun waitForBeingLoaded(): DcBrowseFieldScreensPage {
        falliblePage.waitForPageToLoad()
        return this
    }
}
