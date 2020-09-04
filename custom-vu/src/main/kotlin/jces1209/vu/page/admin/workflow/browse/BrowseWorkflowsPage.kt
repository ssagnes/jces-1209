package jces1209.vu.page.admin.workflow.browse

import com.atlassian.performance.tools.jiraactions.api.WebJira
import jces1209.vu.page.admin.workflow.view.ViewWorkflowPage
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions

abstract class BrowseWorkflowsPage(
    private val jira: WebJira
) {
    private val driver = jira.driver

    abstract fun waitForBeingLoaded(): BrowseWorkflowsPage

    fun open(): BrowseWorkflowsPage {
        jira.navigateTo("secure/admin/workflows/ListWorkflows.jspa")
        return this
    }

    fun viewRandom(): ViewWorkflowPage {
        driver
            .findElements(By.cssSelector("[data-operation='view']"))
            .shuffled()
            .first()
            .click()
        return ViewWorkflowPage(driver)
            .waitForBeingLoaded()
    }

    fun addWorkflow(name: String): ViewWorkflowPage {
        driver
            .wait(ExpectedConditions.elementToBeClickable(By.id("add-workflow")))
            .click()

        driver
            .wait(ExpectedConditions.visibilityOfElementLocated(By.id("add-workflow-newWorkflowName")))
            .sendKeys(name)

        driver
            .wait(ExpectedConditions.elementToBeClickable(By.id("add-workflow-submit")))
            .click()
        return ViewWorkflowPage(driver)
            .waitForBeingLoaded()
    }
}
