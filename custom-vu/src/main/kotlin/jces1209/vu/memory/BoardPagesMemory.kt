package jces1209.vu.memory

import com.atlassian.performance.tools.jiraactions.api.SeededRandom
import jces1209.vu.page.boards.browse.BoardList
import jces1209.vu.page.boards.view.BoardPage

class BoardPagesMemory(
    val seededRandom: SeededRandom
) {
    val kanban = SeededMemory<BoardPage>(seededRandom)
    val scrum = SeededMemory<BoardPage>(seededRandom)
    val nextGen = SeededMemory<BoardPage>(seededRandom)

    fun rememberBoardsList(boardsList: BoardList.MixedBoards) {
        kanban.remember(boardsList.kanban)
        scrum.remember(boardsList.scrum)
        nextGen.remember(boardsList.nextGen)
    }

    fun getMap() : Map<String, SeededMemory<BoardPage>>{
        return mapOf(
            BoardList.boardNameKanban to kanban,
            BoardList.boardNameScrum to scrum,
            BoardList.boardNameNextGen to nextGen)
    }
}
