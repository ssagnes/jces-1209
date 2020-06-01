package jces1209.vu.page.project

import com.atlassian.performance.tools.jiraactions.api.page.wait
import jces1209.vu.page.CommonIssueNavigator
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

interface ProjectIssueNavigatorPage {

    fun openProjectByIndex(index: Int)
    fun waitForProjectIssueNavigator(driver: WebDriver)
}
