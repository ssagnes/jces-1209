package jces1209.vu.page.project

import jces1209.vu.page.DcIssueNavigator
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions

class DcProjectNavigatorPage(
    private val driver: WebDriver
) : ProjectNavigatorPage {
    override fun openProject(projectKey: Int) {
        driver
            .wait(ExpectedConditions
                .elementToBeClickable(By.xpath("(//*[@id='projects']//a)[$projectKey]")))
            .click()
        waitForNavigator(driver)
    }

    override fun waitForNavigator(driver: WebDriver) {
        DcIssueNavigator(driver).waitForNavigator()
    }
}
