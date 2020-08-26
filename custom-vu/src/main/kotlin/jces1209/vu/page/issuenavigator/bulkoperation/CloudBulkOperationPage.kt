package jces1209.vu.page.issuenavigator.bulkoperation

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.FalliblePage
import jces1209.vu.page.issuenavigator.CloudIssueNavigator
import jces1209.vu.page.issuenavigator.IssueNavigator
import org.openqa.selenium.support.ui.ExpectedCondition
import java.time.Duration

class CloudBulkOperationPage(
    jira: WebJira
) : BulkOperationPage(jira) {

    override fun getIssueNavigator(): IssueNavigator {
        return CloudIssueNavigator(jira)
    }

    override fun waitForPage(expectedContent: ExpectedCondition<*>): CloudBulkOperationPage {
        FalliblePage.Builder(
            webDriver = driver,
            expectedContent = expectedContent
        )
            .cloudErrors()
            .timeout(Duration.ofSeconds(30))
            .build()
            .waitForPageToLoad()
        return this
    }
}
