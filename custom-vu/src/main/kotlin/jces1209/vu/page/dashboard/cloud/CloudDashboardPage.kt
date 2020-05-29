package jces1209.vu.page.dashboard.cloud

import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.page.wait
import jces1209.vu.page.dashboard.DashboardPage
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class CloudDashboardPage(

    private val jira: WebJira
) : DashboardPage(
    driver = jira.driver,
    uri = jira.base.resolve("/secure/ConfigurePortalPages!default.jspa")) {

    override fun waitForDashboards() {
        jira.driver.wait(
            condition = ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@href,'/secure/Dashboard.jspa?selectPageId')]")),
            timeout = Duration.ofSeconds(50)
        )
    }

    override fun createDashboard() {
        driver.findElement(
            By.xpath("(//button[@data-test-id='directory.dashboards-v2.create-button'])")).click()
        driver.findElement(
            By.xpath("(//input[@id='shareable-entity-dialog.input-name'])"))
            .sendKeys("TestDasboard" + System.currentTimeMillis())
        driver.findElement(
            By.xpath("(//button[@type='submit'])")).click()
    }

    override fun loadGadget() {
        openDashboardsPage()
        jira.driver.wait(
            condition = ExpectedConditions.elementToBeClickable(
                By.xpath("//a[]contains(@href,'/secure/Dashboard.jspa?selectPageId')")),
            timeout = Duration.ofSeconds(50)
        ).click()
    }

    override fun createGadget(projectKey: String) {
        jira.driver.wait(
            condition = ExpectedConditions.elementToBeClickable(By.id("add-gadget")),
            timeout = Duration.ofSeconds(50)
        ).click()
        jira.driver.wait(
            condition = ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-item-id=" +
                    "'com.atlassian.jira.gadgets:bubble-chart-dashboard-item']")),
            timeout = Duration.ofSeconds(30)
        ).click()
        jira.driver.wait(
            condition = ExpectedConditions.elementToBeClickable(
                By.className("aui-button aui-button-link button-close-gadgets-dialog")),
            timeout = Duration.ofSeconds(50)
        ).click()
        driver.findElement(By.id("138573-project-filter-picker")).sendKeys(projectKey)
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
