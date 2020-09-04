package jces1209.vu.page.admin.workflow.view

import jces1209.vu.page.FalliblePage
import jces1209.vu.page.admin.workflow.view.publish.PublishWorkflowPage
import jces1209.vu.page.admin.workflow.view.status.StatusWorkflowPage
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.*
import java.time.Duration

class ViewWorkflowPage(
    private val driver: WebDriver
) {
    private val addStatusButtonLocator = By.className("add-status")
    private val publishButtonLocator = By.cssSelector("#publish-draft, #publish_draft_workflow")

    fun clickEdit(): ViewWorkflowPage {
        driver
            .wait(elementToBeClickable(By.id("draft-workflow-trigger")))
            .click()

        FalliblePage.Builder(
            driver,
            and(
                visibilityOfElementLocated(publishButtonLocator),
                visibilityOfElementLocated(By.cssSelector("#discard-draft, #discard_draft_workflow")),
                visibilityOfElementLocated(By.cssSelector("#view-original, #view_live_workflow"))
            )
        )
            .serverErrors()
            .timeout(Duration.ofSeconds(30))
            .build()
            .waitForPageToLoad()
        return waitForBeingLoaded()
    }

    fun clickAddStatus(): StatusWorkflowPage {
        driver
            .wait(elementToBeClickable(addStatusButtonLocator))
            .click()

        driver
            .wait(visibilityOfElementLocated(By.className("top-label")))
        return StatusWorkflowPage(driver)
    }

    fun clickPublish(): PublishWorkflowPage {
        driver
            .wait(elementToBeClickable(publishButtonLocator))
            .click()
        driver
            .wait(visibilityOfElementLocated(By.className("form-body")))
        return PublishWorkflowPage(driver)
    }

    fun waitForBeingLoaded(): ViewWorkflowPage {
        FalliblePage.Builder(
            driver,
            and(
                visibilityOfElementLocated(By.id("workflow-diagram")),
                visibilityOfElementLocated(By.id("workflow-text")),
                visibilityOfElementLocated(By.id("workflows-link")),
                visibilityOfElementLocated(By.className("workflow-name")),
                visibilityOfElementLocated(By.className("workflow-canvas-wrapper")),
                presenceOfElementLocated(By.cssSelector("[aria-controls='download-dropdown']"))
            )
        )
            .serverErrors()
            .timeout(Duration.ofSeconds(30))
            .build()
            .waitForPageToLoad()
        return this
    }
}
