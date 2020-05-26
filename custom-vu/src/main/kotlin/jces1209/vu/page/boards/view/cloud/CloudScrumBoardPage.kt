package jces1209.vu.page.boards.view.cloud

import jces1209.vu.page.boards.sprint.CloudSprint
import jces1209.vu.page.boards.sprint.Sprint
import jces1209.vu.page.boards.view.ScrumBoardPage
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import java.net.URI

class CloudScrumBoardPage(
    driver: WebDriver, uri: URI
) : CloudClassicBoardPage(driver, uri), ScrumBoardPage {

    override fun createSprint(): Sprint {
        val sprintContainerLocator = By.className("js-sprint-header")

        val createSprintButton = driver.wait(
            ExpectedConditions.elementToBeClickable(By.className("js-add-sprint"))
        )

        val sprintsNumberBeforeCreate = driver
            .findElements(sprintContainerLocator)
            .size

        createSprintButton.click()

        val sprintContainer = driver
            .wait(ExpectedCondition<WebElement> {
                val foundSprintContainers = driver.findElements(sprintContainerLocator)

                if (foundSprintContainers.size > sprintsNumberBeforeCreate) {
                    foundSprintContainers.last()
                } else {
                    null
                }
            })

        return CloudSprint(sprintContainer)
    }
}
