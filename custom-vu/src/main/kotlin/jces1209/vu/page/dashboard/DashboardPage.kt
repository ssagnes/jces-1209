package jces1209.vu.page.dashboard

import org.openqa.selenium.WebDriver
import java.net.URI

interface DashboardPage {

    fun openDashboardsPage()
    fun createDashboard()
    fun createGadget()
    fun loadGadget()
}
