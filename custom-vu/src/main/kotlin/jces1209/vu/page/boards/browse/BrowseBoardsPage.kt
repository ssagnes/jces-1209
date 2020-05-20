package jces1209.vu.page.boards.browse

interface BrowseBoardsPage {

    fun goToBoardsPage(): BrowseBoardsPage
    fun waitForBoards(): BoardList
}
