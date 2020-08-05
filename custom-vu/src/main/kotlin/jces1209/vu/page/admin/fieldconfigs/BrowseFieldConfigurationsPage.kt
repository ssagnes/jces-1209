package jces1209.vu.page.admin.fieldconfigs

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.FalliblePage
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class BrowseFieldConfigurationsPage(
    private val jira: WebJira
) {
    private val falliblePage = FalliblePage.Builder(
        jira.driver,
        ExpectedConditions.and(
            ExpectedConditions.visibilityOfElementLocated(By.id("field-configurations-table")),
            ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[data-scheme-field='name']")),
            ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[id^='configure-']"))
        )
    )
        .cloudErrors()
        .timeout(Duration.ofSeconds(60))
        .build()

    fun open(): BrowseFieldConfigurationsPage {
        jira.navigateTo("secure/admin/ViewFieldLayouts.jspa")
        return this
    }

    fun waitForBeingLoaded(): BrowseFieldConfigurationsPage {
        falliblePage.waitForPageToLoad()
        return this
    }
}
