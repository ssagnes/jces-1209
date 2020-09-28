package jces1209.vu

import java.io.InputStream
import java.util.*

class ConfigProperties(
    val createIssue: Int,
    val workAnIssue: Int,
    val manageProjects: Int,
    val projectSummary: Int,
    val browseProjects: Int,
    val browseBoards: Int,
    val viewBoard: Int,
    val workOnDashboard: Int,
    val workOnSprint: Int,
    val browseProjectIssues: Int,
    val workOnBacklog: Int,
    val workOnSearch: Int,
    val workOnTopBar: Int,
    val bulkEdit: Int,
    val workOnTransition: Int,
    val workOnWorkflow: Int,
    val browseWorkflows: Int,
    val browseFieldScreens: Int,
    val browseFieldConfigurations: Int,
    val browseCustomFields: Int,
    val browseIssueTypes: Int,
    val browseProjectRoles: Int,
    val virtualUsers: Int?,
    val ramp: Long?,
    val maxOverallLoad: Double?,
    val nodes: Int?
) {

    companion object {
        fun load(resourceName: String?): ConfigProperties {
            val fileReadStream: InputStream? = this::class.java.getResourceAsStream("/$resourceName")
            val properties = Properties()
            fileReadStream.use {
                properties.load(it)

            }
            return ConfigProperties(
                createIssue = properties.getProperty("createIssue").toInt(),
                workAnIssue = properties.getProperty("workAnIssue").toInt(),
                manageProjects = properties.getProperty("manageProjects").toInt(),
                projectSummary = properties.getProperty("projectSummary").toInt(),
                browseProjects = properties.getProperty("browseProjects").toInt(),
                browseBoards = properties.getProperty("browseBoards").toInt(),
                viewBoard = properties.getProperty("viewBoard").toInt(),
                workOnDashboard = properties.getProperty("workOnDashboard").toInt(),
                workOnSprint = properties.getProperty("workOnSprint").toInt(),
                browseProjectIssues = properties.getProperty("browseProjectIssues").toInt(),
                workOnBacklog = properties.getProperty("workOnBacklog").toInt(),
                workOnSearch = properties.getProperty("workOnSearch").toInt(),
                workOnTopBar = properties.getProperty("workOnTopBar").toInt(),
                bulkEdit = properties.getProperty("bulkEdit").toInt(),
                workOnTransition = properties.getProperty("workOnTransition").toInt(),
                workOnWorkflow = properties.getProperty("workOnWorkflow").toInt(),
                browseWorkflows = properties.getProperty("browseWorkflows").toInt(),
                browseFieldScreens = properties.getProperty("browseFieldScreens").toInt(),
                browseFieldConfigurations = properties.getProperty("browseFieldConfigurations").toInt(),
                browseCustomFields = properties.getProperty("browseCustomFields").toInt(),
                browseIssueTypes = properties.getProperty("browseIssueTypes").toInt(),
                browseProjectRoles = properties.getProperty("browseProjectRoles").toInt(),
                virtualUsers = properties.getProperty("virtualUsers")?.toInt(),
                maxOverallLoad = properties.getProperty("maxOverallLoad")?.toDouble(),
                ramp = properties.getProperty("nodes")?.toLong(),
                nodes = properties.getProperty("nodes")?.toInt()
            )
        }
    }

}
