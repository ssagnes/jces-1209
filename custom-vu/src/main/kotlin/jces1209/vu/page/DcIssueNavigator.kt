package jces1209.vu.page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.*
import java.time.Duration

class DcIssueNavigator(
    driver: WebDriver
) {
    private val page = FalliblePage.Builder(
        driver,
        and(
            or(
                presenceOfElementLocated(By.cssSelector("ol.issue-list")),
                presenceOfElementLocated(By.id("issuetable")),
                presenceOfElementLocated(By.id("issue-content"))
            ),
            presenceOfElementLocated(By.id("key-val")),
            presenceOfElementLocated(By.className("issue-body-content"))
        )
    )
        .cloudErrors()
        .timeout(Duration.ofSeconds(30))
        .build()

    fun waitForNavigator() {
        page.waitForPageToLoad()
    }
}
