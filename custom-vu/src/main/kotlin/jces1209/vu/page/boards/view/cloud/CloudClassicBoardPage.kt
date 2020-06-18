package jces1209.vu.page.boards.view.cloud

import jces1209.vu.page.FalliblePage
import jces1209.vu.page.boards.view.BoardContent
import jces1209.vu.page.boards.view.BoardPage
import jces1209.vu.wait
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.*
import java.time.Duration

class CloudClassicBoardPage(
    private val driver: WebDriver,
    private val issueSelector: By,
    private val waitBoardSelectors: List<By>
) {
    constructor(
        driver: WebDriver,
        issueSelector: By
    ) : this(
        driver,
        issueSelector,
        listOf(issueSelector)
    )

    private val logger: Logger = LogManager.getLogger(this::class.java)
    private val unavailableBoardLocators = listOf(
        By.xpath("//*[contains(text(), 'Your board has too many issues')]"),
        By.xpath("//*[contains(text(), 'Board not accessible')]"),
        By.xpath("//*[contains(text(), 'Set a new location for your board')]")
    )
    private val falliblePage = FalliblePage.Builder(
        webDriver = driver,
        expectedContent = waitBoardSelectors + unavailableBoardLocators
    )
        .timeout(Duration.ofSeconds(25))
        .cloudErrors()
        .build()

    fun waitForBoardPageToLoad(): BoardContent {
        falliblePage.waitForPageToLoad()
        unavailableBoardLocators
            .forEach {
                if (driver.findElements(it).isNotEmpty()) {
                    val errorMessage = "Board is not available"
                    logger.debug(errorMessage)
                    throw InterruptedException(errorMessage)
                }
            }
        return BoardPage.GeneralBoardContent(driver, issueSelector)
    }

    fun previewIssue() {
        driver
            .wait(visibilityOfElementLocated(issueSelector))
            .click()

        driver
            .wait(
                and(
                    visibilityOfElementLocated(By.cssSelector("[role='dialog']")),
                    visibilityOfElementLocated(By.cssSelector("[data-test-id='issue-activity-feed.heading']"))
                ))
    }

    fun closePreviewIssue() {
        val closeButton = driver
            .wait(elementToBeClickable(By.cssSelector("[aria-label='Close']")))
        closeButton.click()

        driver.wait(invisibilityOf(closeButton))
    }
}
