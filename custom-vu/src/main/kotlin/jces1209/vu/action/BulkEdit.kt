package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import jces1209.vu.MeasureType
import jces1209.vu.page.IssueNavigator
import jces1209.vu.page.bulkOperation.BulkOperation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Works for both Cloud and Data Center.
 */
class BulkEdit(
    private val issueNavigator: IssueNavigator,
    private val bulkOperation: BulkOperation,
    private val jira: WebJira,
    private val meter: ActionMeter
) : Action {
    private val logger: Logger = LogManager.getLogger(this::class.java)

    override fun run() {
        bulkEdit()
    }

    private fun bulkEdit() {
        val jqlQuery = "order by updated DESC"
        jira.goToIssueNavigator(jqlQuery)
        issueNavigator.waitForNavigator()

        meter.measure(MeasureType.BULK_EDIT) {
            meter.measure(MeasureType.BULK_EDIT_CLICK_ON_TOOLS) {
                issueNavigator.clickOnTools()
            }
            meter.measure(MeasureType.BULK_EDIT_CHOOSE_CURRENT_PAGE) {
                issueNavigator.selectCurrentPageToolsItem()
                bulkOperation.waitForBulkOperationPage()
            }
            meter.measure(MeasureType.BULK_EDIT_CHOOSE_ISSUES) {
                bulkOperation.chooseIssues(100)
                bulkOperation.waitForBulkOperationPage()
            }
            meter.measure(MeasureType.BULK_EDIT_CHOOSE_OPERATION) {
                bulkOperation.chooseOperation()
                bulkOperation.waitForBulkOperationPage()
            }
            meter.measure(MeasureType.BULK_EDIT_OPERATION_DETAILS) {
                bulkOperation.operationDetails()
                bulkOperation.waitForBulkOperationPage()
            }
            meter.measure(MeasureType.BULK_EDIT_CONFIRMATION) {
                bulkOperation.confirmation()
                bulkOperation.waitForBulkOperationPage()
            }
            meter.measure(MeasureType.BULK_EDIT_PROGRESS) {
                bulkOperation.progress()
                bulkOperation.waitForBulkOperationPage()
            }
            meter.measure(MeasureType.BULK_OPERATION_SUBMIT) {
                bulkOperation.submit()
                issueNavigator.waitForNavigator()
            }
        }
    }
}
