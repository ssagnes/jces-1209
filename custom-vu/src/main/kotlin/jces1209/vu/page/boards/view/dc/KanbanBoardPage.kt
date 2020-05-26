package jces1209.vu.page.boards.view.dc

import com.atlassian.performance.tools.jiraactions.api.WebJira

class KanbanBoardPage(
    jira: WebJira,
    boardId: String
) : DcBoardPage(
    driver = jira.driver,
    uri = jira.base.resolve("secure/RapidBoard.jspa?rapidView=$boardId")
)
