package jces1209.vu.page.issuenavigator.bulkoperation

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.FalliblePage
import jces1209.vu.page.issuenavigator.DcIssueNavigator
import org.openqa.selenium.support.ui.ExpectedCondition
import java.time.Duration

class DcBulkOperationPage(
    jira: WebJira
) : BulkOperationPage(jira) {

    override fun getIssueNavigator(): DcIssueNavigator {
        return DcIssueNavigator(jira)
    }

    override fun waitForPage(expectedContent: ExpectedCondition<*>): DcBulkOperationPage {
        FalliblePage.Builder(
            webDriver = driver,
            expectedContent = expectedContent
        )
            .serverErrors()
            .timeout(Duration.ofSeconds(30))
            .build()
            .waitForPageToLoad()
        return this
    }
}
