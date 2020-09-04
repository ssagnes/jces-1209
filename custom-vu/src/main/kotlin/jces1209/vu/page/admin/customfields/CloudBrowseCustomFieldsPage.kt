package jces1209.vu.page.admin.customfields

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.FalliblePage
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class CloudBrowseCustomFieldsPage(
    private val jira: WebJira
): BrowseCustomFieldsPage(jira) {

    private val falliblePage = FalliblePage.Builder(
        jira.driver,
        ExpectedConditions.and(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='global-pages.directories.directory-base.content.table.container']")),
            ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[data-test-id='global-pages.directories.directory-base.content.paginator.container']")),
            ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[class^='Droplist']"))
        )
    )
        .serverErrors()
        .timeout(Duration.ofSeconds(60))
        .build()

    override fun waitForBeingLoaded(): CloudBrowseCustomFieldsPage {
        falliblePage.waitForPageToLoad()
        return this
    }
}
