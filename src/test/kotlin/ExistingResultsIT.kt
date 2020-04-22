import com.atlassian.performance.tools.jiraactions.api.format.MetricCompactJsonFormat
import com.atlassian.performance.tools.report.api.FullReport
import com.atlassian.performance.tools.report.api.FullTimeline
import com.atlassian.performance.tools.report.api.result.EdibleResult
import com.atlassian.performance.tools.report.api.result.RawCohortResult
import com.atlassian.performance.tools.workspace.api.RootWorkspace
import com.atlassian.performance.tools.workspace.api.TaskWorkspace
import extract.to.lib.jpt.report.Apdex
import extract.to.lib.jpt.report.ApdexPerExperience
import org.junit.Test
import java.nio.file.Paths

class ExistingResultsIT {

    private val existingResultsDir = "jces-1317-comparision"

    @Test
    fun shouldOnlyProcessGatheredData() {
        val taskWorkspace = RootWorkspace(Paths.get("build")).isolateTask(existingResultsDir)

        val results = listOf(
            processResults("DC Generic Base", taskWorkspace),
            processResults("DC Mimic 2k Base", taskWorkspace),
            processResults("DC Mimic 6k Base", taskWorkspace),
            processResults("JCES-1317", taskWorkspace)
        )

        FullReport().dump(results, taskWorkspace.isolateTest("Compare"))
        ApdexPerExperience(Apdex()).report(results, taskWorkspace)
    }

    private fun processResults(
        cohort: String,
        taskWorkspace: TaskWorkspace
    ): EdibleResult = RawCohortResult.Factory()
        .fullResult(
            cohort,
            taskWorkspace
                .directory
                .resolve("vu-results")
                .resolve(cohort),
            MetricCompactJsonFormat()
        )
        .prepareForJudgement(FullTimeline())
}
