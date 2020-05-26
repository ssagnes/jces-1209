package jces1209.vu.page.project

import com.atlassian.performance.tools.jiraactions.api.page.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

interface ProjectIssueNavigatorPage{

    fun openProjectByIndex(index: Int)
    fun waitForProjectIssueNavigator(driver: WebDriver) {

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
