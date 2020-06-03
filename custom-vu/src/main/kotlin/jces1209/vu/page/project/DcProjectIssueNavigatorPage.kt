package jces1209.vu.page.project

import jces1209.vu.page.DcIssueNavigator
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions

class DcProjectIssueNavigatorPage(
    private val driver: WebDriver
) : ProjectIssueNavigatorPage {
    override fun openProjectByIndex(index: Int) {
        driver
            .wait(ExpectedConditions
                .elementToBeClickable(By.xpath("(//*[@id='projects']//a)[$index]")))
            .click()
        waitForProjectIssueNavigator(driver)
    }

    override fun waitForProjectIssueNavigator(driver: WebDriver) {
        DcIssueNavigator(driver).waitForNavigator()
    }
}
