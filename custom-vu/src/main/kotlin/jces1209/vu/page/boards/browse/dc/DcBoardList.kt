package jces1209.vu.page.boards.browse.dc

import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.page.wait
import jces1209.vu.page.boards.browse.BoardList
import jces1209.vu.page.boards.view.BoardPage
import jces1209.vu.page.boards.view.dc.DcKanbanBoardPage
import jces1209.vu.page.boards.view.dc.DcScrumBoardPage
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class DcBoardList(
    private val jira: WebJira
) : BoardList() {

    override fun listBoards(): MixedBoards {
        return MixedBoards(getKanbanBoards(), getScrumBoards(), emptyList())
    }

    private fun getKanbanBoards(): Collection<BoardPage> =
        getBoards()
            .map {
                DcKanbanBoardPage(jira, it)
            }

    private fun getBoards(): Collection<String> =
        jira.driver
            .findElements(By.cssSelector(".boards-list tr"))
            .mapNotNull {
                it.getAttribute("data-board-id")
            }

    private fun getScrumBoards(): Collection<BoardPage> {
        val boardsBeforeFiltering = jira.driver.findElements(By.cssSelector(".boards-list tr"))
        if (boardsBeforeFiltering.isEmpty()) {
            return emptyList()
        }

        jira.driver.wait(
            Duration.ofSeconds(5),
            ExpectedConditions.elementToBeClickable(By.cssSelector("#ghx-manage-boards-filter a"))
        ).click()

        jira.driver.wait(
            Duration.ofSeconds(5),
            ExpectedConditions.elementToBeClickable(By.className("type-filter-scrum"))
        ).click()

        jira.driver.wait(
            Duration.ofSeconds(15),
            ExpectedConditions.and(
                *boardsBeforeFiltering.map { ExpectedConditions.stalenessOf(it) }.toTypedArray()
            )
        )

        return getBoards()
            .map {
                DcScrumBoardPage(jira, it)
            }
    }
}
