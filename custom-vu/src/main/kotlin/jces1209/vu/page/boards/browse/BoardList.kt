package jces1209.vu.page.boards.browse

import jces1209.vu.page.boards.view.BoardPage

abstract class BoardList {
    val boardNameKanban = "Kanban"
    val boardNameScrum = "Scrum"
    val boardNameNextGen = "Next-gen"

    /**
     * @return key - board type, value - list of board pages
     */
    abstract fun listBoards(): Map<String, Collection<BoardPage>>
}
