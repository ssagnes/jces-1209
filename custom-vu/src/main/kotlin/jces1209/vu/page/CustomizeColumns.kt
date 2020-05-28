package jces1209.vu.page

import jces1209.vu.page.boards.view.BoardPage
import org.openqa.selenium.WebDriver

abstract class CustomizeColumns(protected val driver: WebDriver) {
    fun goToSearchPage(): CustomizeColumns {
        driver.navigate().to("/issues")
        return this
    }

    abstract fun openColumnsList()
    abstract fun selectColumnByIndex(columnIndex: Int)
    abstract fun selectColumns(columnsCount: Int)
}
