package jces1209.vu.page.admin.workflow.view.publish

import jces1209.vu.page.admin.workflow.view.ViewWorkflowPage
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.*

class PublishWorkflowPage(
    private val driver: WebDriver
) {
    fun selectSaveBackup(): PublishWorkflowPage {
        driver
            .wait(visibilityOfElementLocated(By.cssSelector("[for='publish-workflow-true']")))
            .click()
        return this
    }

    fun clickPublish(): ViewWorkflowPage {
        driver
            .wait(elementToBeClickable(By.id("publish-workflow-submit")))
            .click()

        driver
            .wait(invisibilityOfElementLocated(By.className("form-body")))
        return ViewWorkflowPage(driver)
    }
}
