package jces1209.vu.page.issue

import com.atlassian.performance.tools.jiraactions.api.page.wait
import jces1209.vu.page.issue.AbstractProjectIssueNavigatorPage
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class DcProjectIssueNavigatorPage(
    private val driver: WebDriver
) : AbstractProjectIssueNavigatorPage {
    override fun openProjectByIndex(numberOfProject: String) {
        driver
            .wait(ExpectedConditions
                .elementToBeClickable(By.xpath("(//*[@id='projects']//a)[$numberOfProject]")))
            .click()
       // waitForProjectIssueNavigator()
    }

    fun waitForProjectIssueNavigator() {

        driver.wait(
            Duration.ofSeconds(30),
            ExpectedConditions.or(
                ExpectedConditions.and(
                    ExpectedConditions.or(
                        ExpectedConditions.presenceOfElementLocated(By.cssSelector("ol.issue-list")),
                        ExpectedConditions.presenceOfElementLocated(By.id("issuetable")),
                        ExpectedConditions.presenceOfElementLocated(By.id("issue-content"))
                    ),
                    ExpectedConditions.or(
                        ExpectedConditions.presenceOfElementLocated(By.id("jira-issue-header")),
                        ExpectedConditions.presenceOfElementLocated(By.id("key-val"))
                    ),
                    ExpectedConditions.or(
                        ExpectedConditions.presenceOfElementLocated(By.id("new-issue-body-container")),
                        ExpectedConditions.presenceOfElementLocated(By.className("issue-body-content"))
                    )
                ),
                ExpectedConditions.presenceOfElementLocated(By.className("no-results-hint")) // TODO is it too optimistic like in SearchServerFilter.waitForIssueNavigator ?
            )
        )
    }
}
