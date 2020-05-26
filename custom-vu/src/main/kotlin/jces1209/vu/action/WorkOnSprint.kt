package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import jces1209.vu.MeasureType
import jces1209.vu.memory.SeededMemory
import jces1209.vu.page.boards.view.BoardPage
import jces1209.vu.page.boards.view.ScrumBoardPage
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class WorkOnSprint(
    private val meter: ActionMeter,
    private val scrumBoardsMemory: SeededMemory<BoardPage>
) : Action {
    private val logger: Logger = LogManager.getLogger(this::class.java)

    override fun run() {
        val scrumBoard = scrumBoardsMemory.recall()
        if (scrumBoard == null) {
            logger.debug("I cannot recall scrum board, skipping...")
            return
        }

        if (scrumBoard is ScrumBoardPage) {
            meter.measure(MeasureType.SPRINT_START) {
                scrumBoard.createSprint()
            }
        } else {
            logger.error("scrumBoardsMemory must contain ScrumBoardPage implementations")
        }
    }
}
