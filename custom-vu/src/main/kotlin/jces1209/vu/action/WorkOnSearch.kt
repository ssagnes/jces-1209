package jces1209.vu.action

import com.atlassian.performance.tools.jiraactions.api.SEARCH_WITH_JQL
import com.atlassian.performance.tools.jiraactions.api.SeededRandom
import com.atlassian.performance.tools.jiraactions.api.WebJira
import com.atlassian.performance.tools.jiraactions.api.action.Action
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import com.atlassian.performance.tools.jiraactions.api.memories.IssueKeyMemory
import com.atlassian.performance.tools.jiraactions.api.memories.JqlMemory
import com.atlassian.performance.tools.jiraactions.api.memories.Memory
import com.atlassian.performance.tools.jiraactions.api.observation.SearchJqlObservation
import com.atlassian.performance.tools.jiraactions.api.page.IssueNavigatorPage
import jces1209.vu.MeasureType
import jces1209.vu.MeasureType.Companion.SWITCH_BETWEEN_ISSUES_IN_SEARCH_RESULTS
import jces1209.vu.page.IssueNavigator
import jces1209.vu.page.customizecolumns.ColumnsEditor
import java.net.URI
import javax.json.JsonObject

class WorkOnSearch(
    private val issueNavigator: IssueNavigator,
    private val jira: WebJira,
    private val meter: ActionMeter,
    private val columnsEditor: ColumnsEditor,
    private val random: SeededRandom,
    private val filters: Memory<URI>,
    private val jqlMemory: JqlMemory,
    private val issueKeyMemory: IssueKeyMemory,
    private val searchFilterProbability: Float,
    private val searchJclProbability: Float,
    private val globalSearchProbability: Float,
    private val customizeColumnsProbability: Float,
    private val switchBetweenIssuesProbability: Float
) : Action {
    override fun run() {
        if (roll(switchBetweenIssuesProbability)) {
            switchBetweenIssues()
        }
        if (roll(globalSearchProbability)) {
            openGlobalIssueSearch()
        }
        if (roll(customizeColumnsProbability)) {
            customizeColumns()
        }
        if (roll(searchFilterProbability)) {
            searchFilter()
        }
        if (roll(searchJclProbability)) {
            searchJcl()
        }
    }

    private fun roll(
        probability: Float
    ): Boolean = (random.random.nextFloat() < probability)

    private fun switchBetweenIssues() {
        jira.goToIssueNavigator("resolution = Unresolved ORDER BY priority DESC")
        issueNavigator.waitForNavigator()

        meter.measure(
            key = SWITCH_BETWEEN_ISSUES_IN_SEARCH_RESULTS,
            action = {
                issueNavigator.selectIssue()
            }
        )
    }

    private fun openGlobalIssueSearch(): IssueNavigator {
        val querryList: MutableList<String> = ArrayList()
        querryList.add("/issues/?jql=order%20by%20created%20DESC")
        querryList.add("/issues/?jql=project%20%3D%20ACISDATA%20order%20by%20key%20DESC")
        querryList.add("/issues/?jql=project%20%3D%20ACISDATA%20AND%20" +
            "resolution%20%3D%20Fixed%20AND%20assignee%20in%20(EMPTY)%20order%20by%20updated%20DESC%20%2C%20key%20DESC")//assig
        querryList.add("/issues/?jql=order%20by%20lastViewed%20DESC%2C%20key%20DESC")
        querryList.add("/issues/?jql=reporter%20in%20(EMPTY)%20order%20by%20created%20DESC%2C%20key%20DESC")
        querryList.add("/issues/?jql=project%20in%20(GCSDA%2C%20BCI1)%20AND%20status%20in%20(Accepted%2C%20Done)%20" +
            "AND%20assignee%20in%20(membersOf(acis_dev)%2C%20EMPTY)%20order%20by%20key%20DESC")
        querryList.add("/issues/?jql=")
        querryList.add("/issues/?jql=project%20in%20(ACISDATA%2C%20AMSP%2C%20GLOCOMMDIS)%20" +
            "AND%20status%20not%20in%20(%20Resolved)%20" +
            "AND%20creator%20in%20(EMPTY)%20order%20by%20key%20DESC%2C%20created%20DESC")
        querryList.add("/issues/?jql=issuetype%20in%20(Bug%2C%20Change)%20" +
            "AND%20assignee%20is%20EMPTY%20AND%20\"Assignee%20Group\"%20%3D%20administrators%20" +
            "and%20status%20not%20in%20(Done%2C%20Cancelled)%20ORDER%20BY%20issuetype%20ASC%2C%20" +
            "priority%20DESC%2C%20created%20DESC%2C%20status%20ASC%2C%20lastViewed%20DESC")
        querryList.add("/issues/?jql=Application%20%3D%20\"Access%20Management\"%20" +
            "and%20assignee%20%3D%20557057%3A68425415-a8ec-439d-a05b-0b03720dcadf%20" +
            "and%20status%20in%20(Done)%20or%20assignee%20is%20empty%20" +
            "and%20component%20in%20(.NET)%20AND%20\"Reporting%20Country\"%20%3D%20Canada%20" +
            "And%20status%20in%20(ToDo)%20ORDER%20BY%20priority%20DESC%2C%20created%20ASC")

        querryList.forEach {
            meter.measure(
                key = MeasureType.OPEN_GLOBAL_SEARCH,
                action = {
                    jira.navigateTo(it)
                    issueNavigator.waitForNavigator()
                }
            )
        }
        return issueNavigator
    }

    private fun customizeColumns() {
        jira.goToIssueNavigator("resolution = Unresolved ORDER BY priority DESC")
        meter.measure(
            key = MeasureType.CUSTOMIZE_COLUMNS,
            action = {
                columnsEditor.openEditor()
                columnsEditor.selectItems(2)
                columnsEditor.submitSelection()
            }
        )
    }

    private fun searchFilter() {
        val filter = filters.recall()!!
        meter.measure(
            key = SEARCH_WITH_JQL,
            action = {
                jira.navigateTo(filter.toString())
                issueNavigator.waitForNavigator()
            }
        )
    }

    private fun searchJcl() {
        val jqlQuery = jqlMemory.recall()!!
        meter.measure(
            key = SEARCH_WITH_JQL,
            action = fun(): IssueNavigatorPage {
                return jira.goToIssueNavigator(jqlQuery)
            },
            observation = fun(navigator: IssueNavigatorPage): JsonObject {
                val issueKeys = navigator.getIssueKeys()
                issueKeyMemory.remember(issueKeys)
                return SearchJqlObservation(
                    navigator.jql,
                    issueKeys.size,
                    -1 // work around https://ecosystem.atlassian.net/browse/JPERF-605
                ).serialize()
            }
        )
    }
}
