package jces1209.vu.page

import com.atlassian.performance.tools.jiraactions.api.memories.User
import jces1209.vu.wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions

class DcAdminLogInPage(
    private val driver: WebDriver,
    private val user: User
) {
    fun logIn(): DcAdminLogInPage {
        driver
            .wait(ExpectedConditions.visibilityOfElementLocated(By.id("login-form-authenticatePassword")))
            .sendKeys(user.password)

        val submitButton = driver.wait(ExpectedConditions.elementToBeClickable(By.id("login-form-submit")))
        submitButton.click()
        driver.wait(ExpectedConditions.invisibilityOf(submitButton))
        return this
    }
}
