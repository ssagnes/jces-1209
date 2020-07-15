package jces1209.vu.page

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.bulkOperation.BulkOperation
import jces1209.vu.page.bulkOperation.cloud.CloudBulkOperation
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.*
import java.time.Duration
import java.util.*

class CloudIssueNavigator(
    jira: WebJira
) : IssueNavigator(jira) {
    private val meatballTriggerLocator = By.id("jira-meatball-trigger")
    private val bulkEditAllLocator = By.id("bulkedit_all")
    private val falliblePage = FalliblePage.Builder(
        driver,
        or(
            and(
                or(
                    presenceOfElementLocated(By.cssSelector("ol.issue-list")),
                    presenceOfElementLocated(By.id("issuetable")),
                    presenceOfElementLocated(By.id("issue-content"))
                ),
                or(
                    presenceOfElementLocated(By.id("jira-issue-header")),
                    presenceOfElementLocated(By.id("key-val"))
                ),
                or(
                    presenceOfElementLocated(By.id("new-issue-body-container")),
                    presenceOfElementLocated(By.className("issue-body-content"))
                )
            ),
            and(
                presenceOfElementLocated(By.cssSelector("[data-testid='issue-navigator.ui.card-list.card']")),
                presenceOfElementLocated(By.cssSelector("[data-test-id='issue.views.issue-base.foundation.summary.heading']")),
                presenceOfElementLocated(By.cssSelector("[data-test-id='issue.views.issue-base.foundation.status.status-field-wrapper']")),
                presenceOfElementLocated(By.cssSelector("[data-test-id='issue.views.issue-details.issue-layout.footnote']"))
            ),
            and(
                visibilityOfElementLocated(By.id("issuetable")),
                visibilityOfElementLocated(By.id("layout-switcher-toggle")),
                presenceOfAllElementsLocatedBy(By.cssSelector("[data-issue-key]")),
                presenceOfAllElementsLocatedBy(By.cssSelector(".summary, .components, .assignee, .reporter, .priority, .status"))
            ),
            presenceOfElementLocated(By.className("no-results-hint")) // TODO is it too optimistic like in SearchServerFilter.waitForIssueNavigator ?
        )
    )
        .cloudErrors()
        .timeout(Duration.ofSeconds(120))
        .build()

    override fun waitForNavigator() {
        falliblePage.waitForPageToLoad()
    }

    override fun selectIssue() {
        val element = getIssueElementFromList()
        val title = element.getAttribute("title")
        element.click()
        driver.wait(
            and(
                presenceOfElementLocated(By.xpath("//*[@data-test-id = 'issue.views.issue-base.foundation.summary.heading' and contains(text(), '$title')]")),
                visibilityOfElementLocated(By.xpath("//*[@aria-label='Not watching']")),
                visibilityOfElementLocated(By.xpath("//*[@data-test-id='issue.activity.comment']")),
                visibilityOfElementLocated(By.xpath("//*[@aria-label='Add attachment']"))
            )
        )
    }

    override fun clickOnTools() {
        driver
            .wait(
                ExpectedConditions.elementToBeClickable(meatballTriggerLocator)
            )
            .click()
        driver
            .wait(
                ExpectedConditions.visibilityOfElementLocated(bulkEditAllLocator)
            )
    }

    override fun selectCurrentPageToolsItem(): BulkOperation {
        driver
            .wait(
                ExpectedConditions.visibilityOfElementLocated(bulkEditAllLocator)
            )
            .click()
        val cloudBulkOperation = CloudBulkOperation(driver)
        cloudBulkOperation.waitForBulkOperationPage()
        return cloudBulkOperation
    }

    private fun getIssueElementFromList(): WebElement {
        val elements = driver.wait(
            presenceOfAllElementsLocatedBy(By.xpath("//*[@class ='issue-list']/*[not(@class ='focused')]"))
        )
        val rndIndex = Random().nextInt(elements.size - 1)
        return elements[rndIndex]
    }
}
