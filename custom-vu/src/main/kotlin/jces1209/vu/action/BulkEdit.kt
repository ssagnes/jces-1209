package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.action.Action
import jces1209.vu.Measure
import jces1209.vu.MeasureType
import jces1209.vu.page.issuenavigator.IssueNavigator
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Works for both Cloud and Data Center.
 */
class BulkEdit(
    private val issueNavigator: IssueNavigator,
    private val measure: Measure
) : Action {
    private val logger: Logger = LogManager.getLogger(this::class.java)

    override fun run() {
        issueNavigator
            .openNavigator()
            .waitForBeingLoaded()

        measure.measure(MeasureType.BULK_EDIT) {
            val bulkOperationPage = measure.measure(MeasureType.BULK_EDIT_OPEN_PAGE, isSilent = false) {
                issueNavigator.openBulkOperation()
            }
            if (null != bulkOperationPage) {
                measure.measure(MeasureType.BULK_EDIT_SELECT_ISSUES, isSilent = false) {
                    bulkOperationPage.selectIssues(100)
                }
                measure.measure(MeasureType.BULK_EDIT_SELECT_OPERATION, isSilent = false) {
                    bulkOperationPage.selectEditOperation()
                }
                measure.measure(MeasureType.BULK_EDIT_OPERATION_DETAILS, isSilent = false) {
                    bulkOperationPage.operationDetailsComment()
                }
                measure.measure(MeasureType.BULK_EDIT_CONFIRMATION, isSilent = false) {
                    bulkOperationPage.clickConfirm()
                }
                measure.measure(MeasureType.BULK_EDIT_PROGRESS, isSilent = false) {
                    bulkOperationPage.waitForCompletedProgress()
                }
                measure.measure(MeasureType.BULK_OPERATION_ACKNOWLEDGE, isSilent = false) {
                    bulkOperationPage.clickAcknowledge()
                }
            }
        }
    }
}
