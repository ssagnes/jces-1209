package jces1209.vu.page.dashboard.cloud

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.dashboard.DashboardPage
import org.openqa.selenium.By

class CloudDashboardPage(

    private val jira: WebJira
) : DashboardPage(
    driver = jira.driver,
    uri = jira.base.resolve("/secure/ConfigurePortalPages!default.jspa")) {
    override fun createDashboard() {
        driver.findElement(
            By.xpath("(//button[@data-test-id='directory.dashboards-v2.create-button'])")).click()
        driver.findElement(
            By.xpath("(//input[@id='shareable-entity-dialog.input-name'])"))
            .sendKeys("TestDasboard" + System.currentTimeMillis())
        driver.findElement(
            By.xpath("(//button[@type='submit'])")).click()
    }
}



