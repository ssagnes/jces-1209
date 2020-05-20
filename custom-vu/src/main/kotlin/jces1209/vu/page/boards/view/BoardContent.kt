package jces1209.vu.page.boards.view

interface BoardContent {

    fun getIssueCount(): Int
    fun getIssueKeys(): Collection<String>
}
