package jces1209.vu.page.project

import org.openqa.selenium.WebDriver

interface ProjectNavigatorPage {

    fun openProject(projectKey: Int)
    fun waitForNavigator(driver: WebDriver)
}
