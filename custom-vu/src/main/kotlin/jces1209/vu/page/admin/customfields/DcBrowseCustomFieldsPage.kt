package jces1209.vu.page.admin.customfields

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.FalliblePage
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class DcBrowseCustomFieldsPage(
    private val jira: WebJira
): BrowseCustomFieldsPage(jira) {

    private val falliblePage = FalliblePage.Builder(
        jira.driver,
        ExpectedConditions.and(
            ExpectedConditions.visibilityOfElementLocated(By.id("customfields-container")),
            ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("cell-type-actions")),
            ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[data-custom-field-id]"))
        )
    )
        .serverErrors()
        .timeout(Duration.ofSeconds(60))
        .build()

    override fun waitForBeingLoaded(): DcBrowseCustomFieldsPage {
        falliblePage.waitForPageToLoad()
        return this
    }
}
