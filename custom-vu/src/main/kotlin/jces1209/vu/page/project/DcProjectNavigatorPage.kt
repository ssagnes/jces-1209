package jces1209.vu.page.project

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.DcIssueNavigator
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions

class DcProjectNavigatorPage(
    private val jira: WebJira
) : ProjectNavigatorPage {
    override fun openProject(projectKey: String) {
        jira.navigateTo("projects")
        jira.driver
            .wait(ExpectedConditions
                .elementToBeClickable(
                    By.xpath("(//*[@class='aui-page-panel-content']//*[contains(@href,'/browse/')])")))
        jira.navigateTo("projects/$projectKey/issues")
        waitForNavigator(jira.driver)
    }

    override fun waitForNavigator(driver: WebDriver) {
        DcIssueNavigator(driver).waitForNavigator()
    }
}
