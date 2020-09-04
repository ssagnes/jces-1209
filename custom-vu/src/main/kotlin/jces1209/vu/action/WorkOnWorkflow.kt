package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.action.Action
import jces1209.vu.Measure
import jces1209.vu.MeasureType
import jces1209.vu.page.admin.workflow.browse.BrowseWorkflowsPage
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.time.OffsetDateTime

class WorkOnWorkflow(
    private val measure: Measure,
    private val browseWorkflowsPage: BrowseWorkflowsPage,
    private val browseWorkflowsProbability: Float,
    private val viewWorkflowProbability: Float,
    private val editWorkflowProbability: Float,
    private val createWorkflowProbability: Float
) : Action {
    private val logger: Logger = LogManager.getLogger(this::class.java)

    override fun run() {
        browseWorkflow()
        viewAndEditWorkflow()
        addWorkflow()
    }

    private fun addWorkflow() {
        measure.measure(MeasureType.CREATE_WORKFLOW) {
            browseWorkflowsPage
                .open()
                .waitForBeingLoaded()
                .addWorkflow("TestAddWorkflow" + OffsetDateTime.now().toEpochSecond().toString())
        }
    }

    private fun viewAndEditWorkflow() {
        val viewWorkflowPage = measure.measure(MeasureType.VIEW_WORKFLOW, isSilent = false) {
            browseWorkflowsPage
                .open()
                .waitForBeingLoaded()
                .viewRandom()
        }
        if (null == viewWorkflowPage) {
            logger.debug("Failed to view workflow")
        } else {
            measure.measure(MeasureType.EDIT_WORKFLOW, isSilent = false) {
                viewWorkflowPage
                    .clickEdit()
                    .clickAddStatus()
                    .fillStatus("TestStatus" + OffsetDateTime.now().toEpochSecond().toString())
                    .clickAdd()
                    .clickPublish()
                    .selectSaveBackup()
                    .clickPublish()
            }
        }
    }

    private fun browseWorkflow() {
        measure.measure(MeasureType.BROWSE_WORKFLOWS) {
            browseWorkflowsPage
                .open()
                .waitForBeingLoaded()
        }
    }
}
