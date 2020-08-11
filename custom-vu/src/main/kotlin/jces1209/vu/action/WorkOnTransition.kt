package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.ActionType
import com.atlassian.performance.tools.jiraactions.api.action.Action
import jces1209.vu.Measure
import jces1209.vu.MeasureType
import jces1209.vu.memory.SeededMemory
import jces1209.vu.page.IssueNavigator
import jces1209.vu.page.bars.side.SideBar
import jces1209.vu.page.boards.view.ScrumSprintPage
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class WorkOnTransition(
    private val measure: Measure,
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
        measure.silent {
            issueNavigator
                .openNavigator()
                .waitForNavigator()

            measure.measure(MeasureType.TRANSITION_ISSUE_NAVIGATOR) {
                measure.measure(ActionType(MeasureType.TRANSITION_ISSUE_NAVIGATOR.label + " - Open Issues") { Unit }, isSilent = false) {
                    sideBar
                        .clickOpenIssues()
                        .waitForNavigator()
                }
            }

            measure.measure(MeasureType.TRANSITION_ISSUE_NAVIGATOR) {
                measure.measure(ActionType(MeasureType.TRANSITION_ISSUE_NAVIGATOR.label + " - Search Issues") { Unit }, isSilent = false) {
                    sideBar
                        .clickAllIssues()
                        .waitForNavigator()
                }
            }
        }
    }

    private fun workOnBoardTransition() {
        measure.silent {
            val board = boardsMemory.recall()
            if (board == null) {
                logger.debug("I cannot recall board, skipping...")
                return@silent
            }

            board
                .goToBoard()
                .waitForBoardPageToLoad()

            checkBacklogTransition()
            checkSprintTransition()
        }
    }

    private fun checkSprintTransition() {
        if (sideBar.isSprintPresent()) {
            measure.measure(MeasureType.TRANSITION_VIEW_BOARD) {
                measure.measure(ActionType(MeasureType.TRANSITION_VIEW_BOARD.label + " - Sprint") { Unit }, isSilent = false) {
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
            measure.measure(MeasureType.TRANSITION_VIEW_BOARD) {
                measure.measure(ActionType(MeasureType.TRANSITION_VIEW_BOARD.label + " - Backlog") { Unit }, isSilent = false) {
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
