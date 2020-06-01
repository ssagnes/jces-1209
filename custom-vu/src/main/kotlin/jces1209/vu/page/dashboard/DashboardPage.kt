package jces1209.vu.page.dashboard

import com.atlassian.performance.tools.jiraactions.api.page.wait
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URI
import java.time.Duration


abstract class DashboardPage(
    protected val driver: WebDriver,
    protected val uri: URI
) {


    fun waitForDashboards() {
        driver.wait(
            condition = ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@href,'/secure/Dashboard.jspa?selectPageId')]")),
            timeout = Duration.ofSeconds(50)
        )
    }

    fun createDashboard() {
        driver.findElement(
            By.xpath("(//button[@data-test-id='directory.dashboards-v2.create-button'])")).click()
        driver.findElement(
            By.xpath("(//input[@id='shareable-entity-dialog.input-name'])"))
            .sendKeys("TestDasboard" + System.currentTimeMillis())
        driver.findElement(
            By.xpath("(//button[@type='submit'])")).click()
    }

    fun loadGadget() {
        driver.wait(
            condition = ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/secure/Dashboard.jspa?selectPageId')]")),
            timeout = Duration.ofSeconds(50)
        ).click()
        WebDriverWait(driver, 50).until { webDriver: WebDriver ->
            (webDriver as JavascriptExecutor).
            executeScript("return document.readyState") == "complete"
        }

    }

    fun createGadget(projectName: String) {

        driver.wait(
            condition = ExpectedConditions.elementToBeClickable(By.id("add-gadget")),
            timeout = Duration.ofSeconds(50)
        ).click()
        driver.wait(
            condition = ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-item-id=" +
                    "'com.atlassian.jira.gadgets:bubble-chart-dashboard-item']")),
            timeout = Duration.ofSeconds(30)
        ).click()
        driver.wait(
            condition = ExpectedConditions.elementToBeClickable(
                By.className("aui-button aui-button-link button-close-gadgets-dialog")),
            timeout = Duration.ofSeconds(50)
        ).click()

        driver.findElement(By.xpath("(//input[@placeholder='Search'])"))
            .sendKeys(projectName)
        driver.wait(
            condition = ExpectedConditions.visibilityOfElementLocated(
                By.className("aui-list-item-link aui-indented-link")),
            timeout = Duration.ofSeconds(50)
        ).click()
        driver.findElement(By.className("button submit")).click()
        driver.wait(
            condition = ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("dashboard-item-header")),
            timeout = Duration.ofSeconds(50)
        )

    }


}
