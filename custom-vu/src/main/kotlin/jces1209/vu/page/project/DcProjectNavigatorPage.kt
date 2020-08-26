package jces1209.vu.page.project

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.issuenavigator.DcIssueNavigator
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions

class DcProjectNavigatorPage(
    private val jira: WebJira
) : ProjectNavigatorPage {

    override fun openProject(projectKey: String): DcProjectNavigatorPage {
        jira.driver.navigate().to("/projects/")
        jira.driver
            .wait(ExpectedConditions
                .elementToBeClickable(
                    By.xpath("(//*[@class='aui-page-panel-content']//*[@href='/browse/$projectKey'])")))
            .click()
        return this
    }

    override fun waitForNavigator() {
        DcIssueNavigator(jira).waitForBeingLoaded()
    }
}
