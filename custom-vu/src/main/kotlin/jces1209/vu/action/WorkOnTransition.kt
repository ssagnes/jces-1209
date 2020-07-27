package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.ActionType
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import jces1209.vu.MeasureType
import jces1209.vu.memory.SeededMemory
import jces1209.vu.page.IssueNavigator
import jces1209.vu.page.bars.side.SideBar
import jces1209.vu.page.boards.view.ScrumSprintPage
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class WorkOnTransition(
    private val meter: ActionMeter,
    private val boardsMemory: SeededMemory<ScrumSprintPage>,
    private val sideBar: SideBar,
    private val issueNavigator: IssueNavigator
) : Action {
    private val logger: Logger = LogManager.getLogger(this::class.java)
    override fun run() {
        workOnBoardTransition()
        workOnIssueNavigatorTransition()
    }

    private fun workOnIssueNavigatorTransition() {
        issueNavigator
            .openNavigator()
            .waitForNavigator()

        meter.measure(MeasureType.TRANSITION_ISSUE_NAVIGATOR) {
            meter.measure(ActionType(MeasureType.TRANSITION_ISSUE_NAVIGATOR.label + " - Open Issues") { Unit }) {
                sideBar
                    .clickOpenIssues()
                    .waitForNavigator()
            }
        }

        meter.measure(MeasureType.TRANSITION_ISSUE_NAVIGATOR) {
            meter.measure(ActionType(MeasureType.TRANSITION_ISSUE_NAVIGATOR.label + " - Search Issues") { Unit }) {
                sideBar
                    .clickAllIssues()
                    .waitForNavigator()
            }
        }
    }

    private fun workOnBoardTransition() {
        val board = boardsMemory.recall()
        if (board == null) {
            logger.debug("I cannot recall board, skipping...")
            return
        }

        board
            .goToBoard()
            .waitForBoardPageToLoad()

        checkBacklogTransition()
        checkSprintTransition()
        checkKanbanTransition()
    }

    private fun checkKanbanTransition() {
        if ((sideBar.isBacklogPresent() || sideBar.isSprintPresent()) && sideBar.isSelectBoardPresent()) {
            meter.measure(MeasureType.TRANSITION_VIEW_BOARD) {
                meter.measure(ActionType(MeasureType.TRANSITION_VIEW_BOARD.label + " - Kanban") { Unit }) {
                    sideBar
                        .selectOtherBoard()
                        .waitForBoardPageToLoad()
                }
            }
        } else {
            logger.debug("There are no kanban board on side bar")
        }
    }

    private fun checkSprintTransition() {
        if (sideBar.isSprintPresent()) {
            meter.measure(MeasureType.TRANSITION_VIEW_BOARD) {
                meter.measure(ActionType(MeasureType.TRANSITION_VIEW_BOARD.label + " - Sprint") { Unit }) {
                    sideBar
                        .clickSprint()
                        .waitForBoardPageToLoad()
                }
            }
        } else {
            logger.debug("There are no sprint button on side bar")
        }
    }

    private fun checkBacklogTransition() {
        if (sideBar.isBacklogPresent()) {
            meter.measure(MeasureType.TRANSITION_VIEW_BOARD) {
                meter.measure(ActionType(MeasureType.TRANSITION_VIEW_BOARD.label + " - Backlog") { Unit }) {
                    sideBar
                        .clickBacklog()
                        .waitForBoardPageToLoad()
                }
            }
        } else {
            logger.debug("There are no backlog button on side bar")
        }
    }
}
