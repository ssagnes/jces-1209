package jces1209.vu

import com.atlassian.performance.tools.jiraactions.api.SeededRandom
import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.action.ProjectSummaryAction
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import com.atlassian.performance.tools.jiraactions.api.memories.adaptive.AdaptiveIssueKeyMemory
import com.atlassian.performance.tools.jiraactions.api.memories.adaptive.AdaptiveJqlMemory
import com.atlassian.performance.tools.jiraactions.api.memories.adaptive.AdaptiveProjectMemory
import jces1209.vu.action.*
import jces1209.vu.memory.BoardPagesMemory
import jces1209.vu.memory.SeededMemory
import jces1209.vu.page.AbstractIssuePage
import jces1209.vu.page.JiraTips
import jces1209.vu.page.admin.customfields.BrowseCustomFieldsPage
import jces1209.vu.page.admin.fieldconfigs.BrowseFieldConfigurationsPage
import jces1209.vu.page.admin.fieldscreen.BrowseFieldScreensPage
import jces1209.vu.page.admin.issuetypes.BrowseIssueTypesPage
import jces1209.vu.page.admin.manageprojects.ManageProjectsPage
import jces1209.vu.page.admin.projectroles.BrowseProjectRolesPage
import jces1209.vu.page.admin.workflow.BrowseWorkflowsPage
import jces1209.vu.page.bars.side.SideBar
import jces1209.vu.page.bars.topBar.TopBar
import jces1209.vu.page.boards.browse.BrowseBoardsPage
import jces1209.vu.page.customizecolumns.ColumnsEditor
import jces1209.vu.page.dashboard.DashboardPage
import jces1209.vu.page.filters.FiltersPage
import jces1209.vu.page.issuenavigator.IssueNavigator
import jces1209.vu.page.project.ProjectNavigatorPage
import java.net.URI
import java.util.*

class ScenarioSimilarities(
    private val jira: WebJira,
    private val seededRandom: SeededRandom,
    private val meter: ActionMeter
) {
    val measure = Measure(meter, seededRandom)
    val jqlMemory = AdaptiveJqlMemory(seededRandom)
        .also { it.remember(listOf("order by created DESC")) } // work around https://ecosystem.atlassian.net/browse/JPERF-573
    val issueKeyMemory = AdaptiveIssueKeyMemory(seededRandom)
    val projectMemory = AdaptiveProjectMemory(seededRandom)
    val filtersMemory = SeededMemory<URI>(seededRandom)
    val boardsMemory = BoardPagesMemory(seededRandom)

    fun assembleScenario(
        issuePage: AbstractIssuePage,
        filtersPage: FiltersPage,
        browseWorkflowsPage: BrowseWorkflowsPage,
        browseFieldScreensPage: BrowseFieldScreensPage,
        browseCustomFieldsPage: BrowseCustomFieldsPage,
        browseBoardsPage: BrowseBoardsPage,
        dashboardPage: DashboardPage,
        createIssue: Action,
        browseProjects: Action,
        issueNavigator: IssueNavigator,
        columnsEditor: ColumnsEditor,
        manageProjectsPage: ManageProjectsPage,
        topBar: TopBar,
        sideBar: SideBar,
        projectNavigatorPage: ProjectNavigatorPage,
        browseIssueTypesPage: BrowseIssueTypesPage,
        browseProjectRolesPage: BrowseProjectRolesPage
    ): List<Action> = assembleScenario(
        createIssue = createIssue,
        manageProjects = ManageProjects(
            measure = measure,
            manageProjectsPage = manageProjectsPage
        ),
        workOnDashboard = WorkOnDashboard(
            jira = jira,
            measure = measure,
            projectKeyMemory = projectMemory,
            dashboardPage = dashboardPage,
            viewDashboardsProbability = 1.00f,
            viewDashboardProbability = 1.00f,
            createDashboardAndGadgetProbability = 0.00f // 0.10f if we can mutate data
        ),
        workAnIssue = WorkOnIssue(
            issuePage = issuePage,
            jira = jira,
            measure = measure,
            issueKeyMemory = issueKeyMemory,
            editProbability = 0.00f, // 0.10f if we can mutate data
            commentProbability = 0.00f, // 0.04f if we can mutate data
            linkIssueProbability = 0.00f, // 0.10f if we can mutate data
            attachScreenShotProbability = 0.00f, // 0.04f if we can mutate data
            changeAssigneeProbability = 0.00f, // 0.04f if we can mutate data
            mentionUserProbability = 0.00f, // 0.04f if we can mutate data
            transitionProbability = 0.00f, // 0.04f if we can mutate data
            contextOperationProbability = 0.05f
        ),
        projectSummary = ProjectSummaryAction(
            jira = jira,
            meter = meter,
            projectMemory = projectMemory
        ),
        browseProjects = browseProjects,
        browseFilters = BrowsePopularFilters(
            filters = filtersMemory,
            filtersPage = filtersPage,
            meter = meter
        ),
        browseBoards = BrowseBoards(
            jira = jira,
            browseBoardsPage = browseBoardsPage,
            meter = meter,
            boardsMemory = boardsMemory
        ),
        viewBoard = ViewBoard(
            driver = jira.driver,
            measure = measure,
            boardsMemory = boardsMemory.all,
            issueKeyMemory = issueKeyMemory,
            viewIssueProbability = 0.50f,
            configureBoardProbability = 0.05f,
            contextOperationProbability = 0.05f,
            changeIssueStatusProbability = 0.10f
        ),
        workOnSprint = WorkOnSprint(
            meter = meter,
            backlogsMemory = boardsMemory.backlog,
            sprintsMemory = boardsMemory.sprint,
            jiraTips = JiraTips(jira.driver)
        ),
        browseProjectIssues = BrowseProjectIssues(
            meter = meter,
            projectKeyMemory = projectMemory,
            browseProjectPage = projectNavigatorPage
        ),
        workOnBacklog = WorkOnBacklog(
            measure = measure,
            backlogsMemory = boardsMemory.backlog
        ),
        workOnTopBar = WorkOnTopBar(
            topBar = topBar,
            jira = jira,
            meter = meter
        ),
        workOnSearch = WorkOnSearch(
            issueNavigator = issueNavigator,
            jira = jira,
            measure = measure,
            columnsEditor = columnsEditor,
            filters = filtersMemory,
            jqlMemory = jqlMemory,
            issueKeyMemory = issueKeyMemory,
            searchFilterProbability = 0.50f,
            searchJclProbability = 0.05f,
            globalSearchProbability = 0.50f,
            customizeColumnsProbability = 0.05f,
            switchBetweenIssuesProbability = 0.15f
        ),
        bulkEdit = BulkEdit(
            measure = measure,
            issueNavigator = issueNavigator
        ),
        workOnTransition = WorkOnTransition(
            measure = measure,
            boardsMemory = boardsMemory.sprint,
            sideBar = sideBar,
            issueNavigator = issueNavigator,
            switchViewsProbability = 0.75f
        ),
        browseIssueTypes = BrowseIssueTypes(
            measure = measure,
            browseIssueTypesPage = browseIssueTypesPage
        ),
        browseProjectRoles = BrowseProjectRoles(
            meter = meter,
            browseProjectRolesPage = browseProjectRolesPage
        ),
        browseFieldScreens = BrowseFieldScreens(
            measure = measure,
            browseFieldScreensPage = browseFieldScreensPage
        ),
        browseFieldConfigurations = BrowseFieldConfigurations(
            measure = measure,
            browseFieldConfigurationsPage = BrowseFieldConfigurationsPage(jira)
        ),
        browseCustomFields = BrowseCustomFields(
            measure = measure,
            browseCustomFieldsPage = browseCustomFieldsPage
        )
    )

    private fun assembleScenario(
        createIssue: Action,
        manageProjects: Action,
        workAnIssue: Action,
        projectSummary: Action,
        browseProjects: Action,
        browseFilters: Action,
        browseBoards: Action,
        viewBoard: Action,
        workOnDashboard: Action,
        workOnSprint: Action,
        browseProjectIssues: Action,
        workOnBacklog: Action,
        workOnSearch: Action,
        workOnTopBar: Action,
        bulkEdit: Action,
        workOnTransition: Action,
        browseFieldScreens: Action,
        browseFieldConfigurations: Action,
        browseCustomFields: Action,
        browseIssueTypes: Action,
        browseProjectRoles: Action
    ): List<Action> {

        /*JPERF-2968: I tried with CohortProperties class but it's in different module,
         might need to add module dependency
         val properties = CohortProperties.load(secretsName)*/

        // I have tried two methods one is below and another is after function ends


        /* Method 1 :Just to for testing purpose I have added ConfigProperties Class which does same thing as CohortProperties
          Ideally this should pickup properties filename automatically I meant we will be running 5k and 10k instances
          together so it should pick up file accordingly but I am not sure how's that possible ??? */
        val properties = DistributionProperties.load("main_distribution.properties")

        val exploreData = listOf(browseProjects, browseFilters, browseBoards)
        val spreadOut = mapOf(
            createIssue to properties.createIssue, // 5 if we can mutate data
            workAnIssue to properties.workAnIssue,
            manageProjects to properties.manageProjects,
            projectSummary to properties.projectSummary,
            browseProjects to properties.browseProjects,
            browseBoards to properties.browseBoards,
            viewBoard to properties.viewBoard,
            workOnDashboard to properties.workOnDashboard,
            workOnSprint to properties.workOnSprint, // 3 if we can mutate data
            browseProjectIssues to properties.browseProjectIssues,
            workOnBacklog to properties.workOnBacklog, // 3 if we can mutate data
            workOnSearch to properties.workOnSearch,
            workOnTopBar to properties.workOntopBar,
            bulkEdit to properties.bulkEdit, // 5 if we can mutate data
            workOnTransition to properties.workOnTransition,
            browseFieldScreens to properties.browseFieldScreens,
            browseFieldConfigurations to properties.browseFieldConfigurations,
            browseCustomFields to properties.browseCustomFields,
            browseIssueTypes to properties.browseIssueTypes,
            browseProjectRoles to properties.browseProjectRoles
        )
            .map { (action, proportion) -> Collections.nCopies(proportion, action) }
            .flatten()
            .shuffled(seededRandom.random)
        return exploreData + spreadOut

        //Method 2:  Method 1 was reading it from properties file under cohort-secrets
        // I tried to read it via resource properties and it works but again here my concern is how to read file dynamically for each instance

        /*val resourceName = "test.properties" --> check resources/test.properties
        val cookieStream = this::class.java.getResourceAsStream("/$resourceName")
            ?: throw Exception("Copy test.properties as $resourceName and fill in the values")
        val props = Properties()
        cookieStream.use { props.load(it) }

        val exploreData = listOf(browseProjects, browseFilters, browseBoards)
        val spreadOut = mapOf(
            createIssue to props.getProperty("createIssue", 0.toString()).toInt(), // 5 if we can mutate data
            workAnIssue to props.getProperty("workAnIssue",55.toString()).toInt(),
            manageProjects to props.getProperty("manageProjects",5.toString()).toInt(),
            ...
            continue rest of the code...

            ...
            ...
            ..
            ..
            */

    }
}
