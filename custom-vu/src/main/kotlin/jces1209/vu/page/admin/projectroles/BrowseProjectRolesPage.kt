package jces1209.vu.page.admin.projectroles

import com.atlassian.performance.tools.jiraactions.api.WebJira
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions

abstract class BrowseProjectRolesPage(
    private val jira: WebJira
) {
    protected val loadingPageExpectedCondition: ExpectedCondition<Boolean> = ExpectedConditions.and(
        ExpectedConditions.visibilityOfElementLocated(By.id("project_roles")),
        ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[data-project-role-id]")),
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("[action='AddProjectRole.jspa']")),
        ExpectedConditions.presenceOfElementLocated(By.id("role_submit"))
    )

    fun open(): BrowseProjectRolesPage {
        jira.navigateTo("secure/project/ViewProjectRoles.jspa")
        return this
    }

    abstract fun waitForBeingLoaded(): BrowseProjectRolesPage
}
