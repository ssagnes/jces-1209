package jces1209.vu.page.dashboard

import jces1209.vu.page.boards.view.BoardPage
import org.openqa.selenium.WebDriver
import java.net.URI

abstract class DashboardPage(
    protected val driver: WebDriver,
    protected val uri: URI
) {

    fun openDashboardsPage() : DashboardPage {
        driver.navigate().to(uri.toURL())
        return this
    }

    abstract fun waitForDashboards()

}
