package jces1209.vu.page.project

import com.atlassian.performance.tools.jiraactions.api.page.wait
import jces1209.vu.page.CommonIssueNavigator
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class DcProjectIssueNavigatorPage(
    private val driver: WebDriver
) : ProjectIssueNavigatorPage, CommonIssueNavigator() {
    override fun openProjectByIndex(index: Int) {
        driver
            .wait(ExpectedConditions
                .elementToBeClickable(By.xpath("(//*[@id='projects']//a)[$index]")))
            .click()
        waitForProjectIssueNavigator(driver)
    }

    override fun waitForProjectIssueNavigator(driver: WebDriver) {
        driver.wait(
            Duration.ofSeconds(30),
            expectedConditionsList())
    }


}
