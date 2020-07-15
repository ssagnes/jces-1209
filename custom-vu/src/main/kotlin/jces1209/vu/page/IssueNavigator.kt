package jces1209.vu.page

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.bulkOperation.BulkOperation
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.*

abstract class IssueNavigator(
    private val jira: WebJira
) {
    enum class ViewType {
        DETAIL, LIST
    }

    protected val driver = jira.driver

    private val changeViewButtonLocator = By.id("layout-switcher-button")

    abstract fun waitForNavigator()
    abstract fun selectIssue()
    abstract fun clickOnTools()
    abstract fun selectCurrentPageToolsItem(): BulkOperation


    fun openChangeViewPopup(): IssueNavigator {
        driver
            .wait(elementToBeClickable(changeViewButtonLocator))
            .click()
        driver.wait(
            visibilityOfNestedElementsLocatedBy(By.cssSelector("#layout-switcher-button_drop, #layout-switcher-options"), By.className("layout-switcher-item"))
        )
        return this
    }

    fun getViewType(): ViewType {
        val switchButton = driver.wait(ExpectedConditions.visibilityOfElementLocated(changeViewButtonLocator))

        return when {
            switchButton.findElements(By.className("aui-iconfont-view-list")).isNotEmpty() -> {
                ViewType.DETAIL
            }
            switchButton.findElements(By.className("aui-iconfont-view-table")).isNotEmpty() -> {
                ViewType.LIST
            }
            else -> {
                throw RuntimeException("Unexpected switch view button type")
            }
        }
    }

    fun selectInactiveViewType(): IssueNavigator {
        val issues = driver.findElements(By.cssSelector(".issue-link-key, .issuekey"))
        driver
            .wait(elementToBeClickable(By.cssSelector(".layout-switcher-item > .aui-list-item-link > :not(.aui-iconfont-success)")))
            .click()
        driver.wait(invisibilityOfAllElements(issues))
        waitForNavigator()
        return this
    }

    fun openNavigator(): IssueNavigator {
        jira.goToIssueNavigator("resolution = Unresolved ORDER BY priority DESC")
        return this;
    }
}
