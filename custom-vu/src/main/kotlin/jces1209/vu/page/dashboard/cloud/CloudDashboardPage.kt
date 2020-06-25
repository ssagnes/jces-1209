package jces1209.vu.page.dashboard.cloud

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.dashboard.DashboardPage
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions

class CloudDashboardPage(
    jira: WebJira
) : DashboardPage(
    driver = jira.driver) {
    override fun createDashboard() {
        driver.wait(
            ExpectedConditions.visibilityOfElementLocated(By.id("shareable-entity-dialog.input-name"))
        )
        driver.findElement(
            By.xpath("//*[@data-test-id='directory.dashboards-v2.create-button']")).click()
        driver.findElement(
            By.id("shareable-entity-dialog.input-name"))
            .sendKeys("TestDasboard" + System.currentTimeMillis())
        driver.findElement(
            By.xpath("(//button[@type='submit'])")).click()
    }
}



