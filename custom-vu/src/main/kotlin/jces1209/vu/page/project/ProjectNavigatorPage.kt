package jces1209.vu.page.project

interface ProjectNavigatorPage {

    fun openProject(projectKey: String): ProjectNavigatorPage
    fun waitForNavigator()
}
