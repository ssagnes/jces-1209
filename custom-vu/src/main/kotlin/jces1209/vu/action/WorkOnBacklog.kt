package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.action.Action
import jces1209.vu.Measure
import jces1209.vu.MeasureType.Companion.INLINE_ISSUE_CREATE_BACKLOG
import jces1209.vu.memory.SeededMemory
import jces1209.vu.page.boards.view.ScrumBacklogPage
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class WorkOnBacklog(
    private val measure: Measure,
    private val backlogsMemory: SeededMemory<ScrumBacklogPage>
) : Action {
    private val logger: Logger = LogManager.getLogger(this::class.java)

    override fun run() {
        val backlog = backlogsMemory.recall()
        if (backlog == null) {
            logger.debug("I cannot recall backlog, skipping...")
            return
        }
        backlog
            .goToBoard()
            .waitForBoardPageToLoad()
        measure.measure(INLINE_ISSUE_CREATE_BACKLOG) {
            backlog.inlineCreateIssue("TestInlineCreate")
        }
    }
}
