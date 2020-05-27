package jces1209.vu.page.dashboard

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import java.net.URI

abstract class DashboardPage(
    protected val driver: WebDriver,
    protected val uri: URI
) {

    open fun openDashboardsPage(): DashboardPage {
        driver.navigate().to(uri.toURL())
        return this
    }

    abstract fun waitForDashboards()
    abstract fun createDashboard()
    abstract fun loadGadget()
    abstract fun createGadget()

    protected class GeneralDashboardContent(
        private val driver: WebDriver,
        private val issueSelector: By
    ) : DashboardContent {

        private val lazyIssueKeys: Collection<String> by lazy {
            driver
                .findElements(issueSelector)
                .map { it.getAttribute("data-issue-key") }
        }

        override fun getDashboardsCount(): Int = lazyIssueKeys.size
        override fun getDashboardsKeys(): Collection<String> = lazyIssueKeys

    }
}
