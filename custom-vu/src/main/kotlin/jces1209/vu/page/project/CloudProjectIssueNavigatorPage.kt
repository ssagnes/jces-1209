package jces1209.vu.page.project

import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions


class CloudProjectIssueNavigatorPage(
    private val driver: WebDriver
) : ProjectIssueNavigatorPage {
    override fun openProjectByIndex(index: Int) {
        driver
            .wait(ExpectedConditions
                .elementToBeClickable(By.xpath("(//*[@data-testid='Content']//a)[$index]")))
            .click()
        waitForProjectIssueNavigator(driver)
    }
}
