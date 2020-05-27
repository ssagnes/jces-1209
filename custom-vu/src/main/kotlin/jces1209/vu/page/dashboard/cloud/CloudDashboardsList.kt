package jces1209.vu.page.dashboard.cloud

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.dashboard.DashboardsList
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class CloudDashboardsList(
    private val jira: WebJira
) : DashboardsList() {
    override fun getDashboardslist(): List<WebElement> {
        return jira.driver.findElements(By.xpath("(//a[contains(@href,'/secure/Dashboard.jspa')])"))
    }

}
