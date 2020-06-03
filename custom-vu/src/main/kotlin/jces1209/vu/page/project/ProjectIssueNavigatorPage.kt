package jces1209.vu.page.project

import org.openqa.selenium.WebDriver

interface ProjectIssueNavigatorPage {

    fun openProjectByIndex(index: Int)
    fun waitForProjectIssueNavigator(driver: WebDriver)
}
