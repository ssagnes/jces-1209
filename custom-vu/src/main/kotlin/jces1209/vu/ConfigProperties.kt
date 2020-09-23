package jces1209.vu

import java.io.File
import java.io.FileInputStream
import java.net.URI
import java.util.*

class ConfigProperties(
    val jira: URI,
    val userName: String,
    val userPassword: String,
    val cohort: String,
    val jiraType: String?,
    val workAnData: Int,
    val createIssue : Int,
    val workAnIssue : Int,
    val manageProjects : Int,
    val projectSummary : Int,
    val browseProjects : Int,
    val browseBoards : Int,
    val viewBoard : Int,
    val workOnDashboard : Int,
    val workOnSprint : Int,
    val browseProjectIssues : Int,
    val workOnBacklog : Int,
    val workOnSearch : Int,
    val workOntopBar : Int,
    val bulkEdit : Int,
    val workOnTransition : Int,
    val browseWorkflows : Int,
    val browseFieldScreens : Int,
    val browseFieldConfigurations : Int,
    val browseCustomFields : Int,
    val browseIssueTypes : Int,
    val browseProjectRoles : Int
) {
    companion object {
        fun load(secretsName: String): ConfigProperties {
            val secrets = File("cohort-secrets/").resolve(secretsName)
            val properties = Properties()
            secrets.bufferedReader().use { properties.load(it) }
            return ConfigProperties(
                jira = URI(properties.getProperty("jira.uri")!!),
                userName = properties.getProperty("user.name")!!,
                userPassword = properties.getProperty("user.password")!!,
                cohort = properties.getProperty("cohort")!!,
                jiraType = properties.getProperty("jira.type"),
                workAnData = properties.getProperty("workAnIssue").toInt()!!,
                 createIssue = properties.getProperty("createIssue").toInt(),
                 workAnIssue = properties.getProperty("workAnIssue").toInt(),
                 manageProjects = properties.getProperty("manageProjects").toInt(),
                 projectSummary = properties.getProperty("projectSummary").toInt(),
                 browseProjects = properties.getProperty("browseProjects").toInt()!!,
                 browseBoards = properties.getProperty("browseBoards").toInt()!!,
                 viewBoard = properties.getProperty("viewBoard").toInt()!!,
                 workOnDashboard = properties.getProperty("workOnDashboard").toInt()!!,
                 workOnSprint = properties.getProperty("workOnSprint").toInt()!!,
                 browseProjectIssues = properties.getProperty("browseProjectIssues").toInt()!!,
                 workOnBacklog = properties.getProperty("workOnBacklog").toInt()!!,
                 workOnSearch = properties.getProperty("workOnSearch").toInt()!!,
                 workOntopBar = properties.getProperty("workOntopBar").toInt()!!,
                 bulkEdit = properties.getProperty("bulkEdit").toInt()!!,
                 workOnTransition = properties.getProperty("workOnTransition").toInt()!!,
                 browseWorkflows = properties.getProperty("browseWorkflows").toInt()!!,
                 browseFieldScreens = properties.getProperty("browseFieldScreens").toInt()!!,
                 browseFieldConfigurations = properties.getProperty("browseFieldConfigurations").toInt()!!,
                 browseCustomFields = properties.getProperty("browseCustomFields").toInt()!!,
                 browseIssueTypes = properties.getProperty("browseIssueTypes").toInt()!!,
                 browseProjectRoles = properties.getProperty("browseProjectRoles").toInt()!!
            )
        }
    }
}
