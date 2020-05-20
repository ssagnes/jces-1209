package jces1209.vu.page.boards.browse.cloud

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.FalliblePage
import jces1209.vu.page.boards.browse.BrowseBoardsPage
import org.openqa.selenium.By
import java.time.Duration

class CloudBrowseBoardsPage(
    private val jira: WebJira
) : BrowseBoardsPage {
    private val tableLocator = By.cssSelector(
        "[data-test-id='global-pages.directories.directory-base.content.table.container']"
    )

    private val falliblePage = FalliblePage.Builder(
        expectedContent = listOf(tableLocator),
        webDriver = jira.driver
    )
        .cloudErrors()
        .timeout(Duration.ofSeconds(25))
        .build()

    override fun goToBoardsPage(): CloudBrowseBoardsPage {
        jira.navigateTo("secure/ManageRapidViews.jspa")
        return this;
    }

    override fun waitForBoards(): CloudBoardList {
        falliblePage.waitForPageToLoad()
        val tableElement = jira.driver.findElement(tableLocator)
        return CloudBoardList(tableElement, jira.driver)
    }
}
