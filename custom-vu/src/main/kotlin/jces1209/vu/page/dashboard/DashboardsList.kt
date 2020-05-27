package jces1209.vu.page.dashboard

import org.openqa.selenium.WebElement

abstract class DashboardsList {
    abstract fun getDashboardslist(): List<WebElement>
}
