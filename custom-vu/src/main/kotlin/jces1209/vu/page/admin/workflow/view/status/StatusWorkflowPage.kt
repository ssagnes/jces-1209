package jces1209.vu.page.admin.workflow.view.status

import com.atlassian.performance.tools.jiraactions.api.page.wait
import jces1209.vu.page.admin.workflow.view.ViewWorkflowPage
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.*
import java.time.Duration

class StatusWorkflowPage(
    private val driver: WebDriver
) {

    fun fillStatus(status: String): StatusWorkflowPage {
        driver
            .wait(visibilityOfElementLocated(By.id("status-name-field")))
            .sendKeys(status)

        driver
            .wait(visibilityOfElementLocated(By.className("aui-list-item-link")))
            .click()
        return this
    }

    private val createButtonLocator = By.cssSelector("[name='submit'][value='Create']")

    fun clickAdd(): ViewWorkflowPage {
        driver
            .wait(elementToBeClickable(By.name("submit")))
            .click()

        driver
            .wait(
                or(
                    and(
                        invisibilityOfElementLocated(By.className("top-label")),
                        visibilityOfElementLocated(By.className("properties-view"))
                    ),
                    visibilityOfElementLocated(createButtonLocator)
                ))

        val createButtons = driver.findElements(createButtonLocator)
        if (createButtons.isNotEmpty()) {
            createButtons.first().click()
            driver.wait(Duration.ofSeconds(40), invisibilityOfElementLocated(createButtonLocator))
        }
        return ViewWorkflowPage(driver)
    }
}
