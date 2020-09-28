package jces1209

import com.atlassian.performance.tools.jiraactions.api.scenario.Scenario
import com.atlassian.performance.tools.virtualusers.api.config.VirtualUserBehavior

interface BenchmarkQuality {

    fun provide(configProperties: String?): VirtualUsersSource

    fun behave(
        scenario: Class<out Scenario>,
        configProperties: String?
    ): VirtualUserBehavior
}
