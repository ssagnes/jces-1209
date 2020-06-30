package jces1209.vu.page.dashboard

import com.atlassian.performance.tools.jiraactions.api.memories.Project
import com.atlassian.performance.tools.jiraactions.api.page.wait
import jces1209.vu.page.FalliblePage
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.*
import java.time.Duration

abstract class DashboardPage(
    protected val driver: WebDriver
) {
    abstract fun createDashboard()

    fun waitForDashboards() {
        driver.wait(
            condition = visibilityOfElementLocated(By.xpath("//a[contains(@href,'/secure/Dashboard.jspa?selectPageId')]")),
            timeout = Duration.ofSeconds(50)
        )
    }

    fun openDashboard(): DashboardPage {
        val links = driver.wait(
            condition = visibilityOfAllElementsLocatedBy(
                By.xpath("(//a[contains(@href,'/secure/Dashboard.jspa?selectPageId') and not(@class)])")),
            timeout = Duration.ofSeconds(50)
        ).shuffled().first().click()
        return this
    }

    fun waitForGadgetsLoad() {
        FalliblePage.Builder(
            expectedContent = or(
                visibilityOfElementLocated(By.className("dashboard-item-frame gadget-container")),
                and(
                    visibilityOfElementLocated(By.className("aui-page-header-main")),
                    visibilityOfElementLocated(By.id("tools-dropdown-icon")))
            ),
            webDriver = driver
        )
            .timeout(Duration.ofSeconds(30))
            .cloudErrors()
            .build()
            .waitForPageToLoad()

        driver
            .wait(
                timeout = Duration.ofSeconds(30),
                condition = invisibilityOfElementLocated(By.className("loading")))
    }

    fun createGadget(projectName: String) {
        driver.wait(
            condition = elementToBeClickable(By.id("add-gadget")),
            timeout = Duration.ofSeconds(50)
        ).click()
        driver.wait(
            condition = elementToBeClickable(
                By.xpath("//button[@data-item-id=" +
                    "'com.atlassian.jira.gadgets:bubble-chart-dashboard-item']")),
            timeout = Duration.ofSeconds(30)
        ).click()
        driver.wait(
            condition = elementToBeClickable(
                By.className("aui-button aui-button-link button-close-gadgets-dialog")),
            timeout = Duration.ofSeconds(50)
        ).click()
        driver.findElement(By.xpath("(//input[@placeholder='Search'])"))
            .sendKeys(projectName)
        driver.wait(
            condition = visibilityOfElementLocated(
                By.className("aui-list-item-link aui-indented-link")),
            timeout = Duration.ofSeconds(50)
        ).click()
        driver.findElement(By.className("button submit")).click()
        driver.wait(
            condition = presenceOfAllElementsLocatedBy(By.className("bubble-chart-component-plot")),
            timeout = Duration.ofSeconds(50)
        )
    }
}
