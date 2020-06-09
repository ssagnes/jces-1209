package jces1209.vu.page.project

import jces1209.vu.page.CloudIssueNavigator
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions


class CloudProjectNavigatorPage(
    private val driver: WebDriver
) : ProjectNavigatorPage {

    override fun openProject(projectKey: Int) {
        driver
            .wait(ExpectedConditions
                .elementToBeClickable(By.xpath("(//*[@data-testid='Content']//a)[$projectKey]")))
            .click()
        waitForNavigator(driver)
    }

    override fun waitForNavigator(driver: WebDriver) {
        CloudIssueNavigator(driver).waitForNavigator()
    }
}
