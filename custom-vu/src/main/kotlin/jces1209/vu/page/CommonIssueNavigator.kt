package jces1209.vu.page

import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions

abstract class CommonIssueNavigator {
    fun expectedConditionsList(): ExpectedCondition<*> {
        return (ExpectedConditions.and(
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
            ),
            ExpectedConditions.presenceOfElementLocated(By.className("no-results-hint")) // TODO is it too optimistic like in SearchServerFilter.waitForIssueNavigator ?
        ))
    }
}
