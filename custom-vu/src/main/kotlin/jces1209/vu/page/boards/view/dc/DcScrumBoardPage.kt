package jces1209.vu.page.boards.view.dc

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.boards.sprint.Sprint
import jces1209.vu.page.boards.view.ScrumBoardPage
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions

class DcScrumBoardPage(
    jira: WebJira,
    boardId: String
) : DcBoardPage(
    driver = jira.driver,
    uri = jira.base.resolve("secure/RapidBoard.jspa?rapidView=$boardId&view=planning")
), ScrumBoardPage {
    override val issueSelector = By.className("ghx-issue-compact")
    override fun createSprint(): Sprint {
        TODO("Not yet implemented")
    }
}
