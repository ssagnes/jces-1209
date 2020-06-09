package jces1209.vu.page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.*
import java.time.Duration

class DcIssueNavigator(
    driver: WebDriver
) : IssueNavigator {
    private val falliblePage = FalliblePage.Builder(
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
        .serverErrors()
        .timeout(Duration.ofSeconds(30))
        .build()

    override fun waitForNavigator() {
        falliblePage.waitForPageToLoad()
    }
}
