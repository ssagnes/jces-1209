package jces1209.vu.page.issuenavigator.bulkoperation

import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.page.wait
import jces1209.vu.page.issuenavigator.IssueNavigator
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.*
import java.time.Duration

abstract class BulkOperationPage(
    protected val jira: WebJira
) {
    protected val driver = jira.driver
    private val bulkEditComment = "Bulk Edit comment"

    private val acknowledgeButtonLocator = By.id("acknowledge_submit")
    private val confirmButtonLocator = By.id("confirm")
    private val nextButtonLocator = By.id("next")
    private val cancelButtonLocator = By.id("cancel")
    private val stepsLocator = By.className("steps")

    protected abstract fun waitForPage(expectedContent: ExpectedCondition<*>): BulkOperationPage
    protected abstract fun getIssueNavigator(): IssueNavigator

    fun waitForBeingLoaded(): BulkOperationPage {
        return waitForPage(and(
            presenceOfElementLocated(stepsLocator),
            presenceOfElementLocated(By.id("bulkedit")),
            presenceOfElementLocated(By.id("issuetable")),
            presenceOfAllElementsLocatedBy(By.cssSelector("[data-issue-key]")),
            presenceOfElementLocated(nextButtonLocator),
            presenceOfElementLocated(cancelButtonLocator)
        ))
    }

    fun selectIssues(issuesNumber: Int): BulkOperationPage {
        var issuesNumberLocal: Int = issuesNumber

        val checkboxElements =
            driver
                .wait(visibilityOfAllElementsLocatedBy(By.cssSelector("input[name^='bulkedit']")))
                .filter { !it.isSelected }

        if (checkboxElements.size < issuesNumberLocal || issuesNumberLocal < 1) {
            throw RuntimeException("Failed to select [%s] issues. Actual count [%s]".format(issuesNumber, checkboxElements.size))
        }

        for (i in 0..--issuesNumberLocal) {
            checkboxElements[i].click()
        }
        return clickNextButton(and(
            presenceOfElementLocated(stepsLocator),
            presenceOfElementLocated(By.id("bulkedit_chooseoperation")),
            presenceOfAllElementsLocatedBy(By.cssSelector("[id^='cellbulk']")),
            presenceOfElementLocated(nextButtonLocator),
            presenceOfElementLocated(cancelButtonLocator)
        ))
    }

    fun selectEditOperation(): BulkOperationPage {
        try {
            driver
                .wait(elementToBeClickable(By.id("bulk.edit.operation.name_id")))
                .click()
        } catch (e:Throwable){
            e.printStackTrace()
        }
        return clickNextButton(and(
            presenceOfElementLocated(stepsLocator),
            presenceOfElementLocated(By.id("bulkedit")),
            presenceOfAllElementsLocatedBy(By.id("availableActionsTable")),
            presenceOfAllElementsLocatedBy(By.id("unavailableActionsTable")),
            presenceOfAllElementsLocatedBy(By.className("availableActionRow")),
            presenceOfAllElementsLocatedBy(By.cssSelector("input[id^='customfield']")),
            presenceOfElementLocated(nextButtonLocator),
            presenceOfElementLocated(cancelButtonLocator)
        ))
    }

    fun operationDetailsComment(): BulkOperationPage {
        driver
            .wait(elementToBeClickable(By.id("cbcomment")))
            .click()
        driver
            .wait(elementToBeClickable(By.id("comment")))
            .click()
        Actions(driver)
            .sendKeys(bulkEditComment)
            .perform()
        return clickNextButton(and(
            presenceOfElementLocated(stepsLocator),
            presenceOfElementLocated(By.id("bulkedit_confirmation")),
            presenceOfAllElementsLocatedBy(By.id("updatedfields")),
            presenceOfAllElementsLocatedBy(By.id("updatedIssueTable")),
            presenceOfAllElementsLocatedBy(confirmButtonLocator),
            presenceOfAllElementsLocatedBy(By.id("cancel-bottom"))
        ))
    }

    fun clickConfirm(): BulkOperationPage {
        driver
            .wait(elementToBeClickable(confirmButtonLocator))
            .click()
        return waitForPage(and(
            presenceOfElementLocated(By.className("pb_bartable")),
            presenceOfAllElementsLocatedBy(By.className("pb_section"))
        ))
    }

    fun waitForCompletedProgress(): BulkOperationPage {
        driver
            .wait(
                Duration.ofSeconds(60),
                visibilityOfElementLocated(By.xpath("//*[@id='stepped-process']//*[contains(text() , 'Bulk operation is 100% complete.')]"))
            )
        return this
    }

    fun clickAcknowledge(): IssueNavigator {
        driver
            .wait(elementToBeClickable(acknowledgeButtonLocator))
            .click()
        return getIssueNavigator()
            .waitForBeingLoaded()
    }

    private fun clickNextButton(expectedContent: ExpectedCondition<*>): BulkOperationPage {
        driver
            .wait(elementToBeClickable(nextButtonLocator))
            .click()
        return waitForPage(expectedContent)
    }
}
