package jces1209

import com.amazonaws.regions.Regions
import com.atlassian.performance.tools.jiraactions.api.scenario.Scenario
import com.atlassian.performance.tools.virtualusers.api.TemporalRate
import com.atlassian.performance.tools.virtualusers.api.VirtualUserLoad
import com.atlassian.performance.tools.virtualusers.api.browsers.Browser
import com.atlassian.performance.tools.virtualusers.api.config.VirtualUserBehavior
import jces1209.vu.ConfigProperties
import jces1209.vu.EagerChromeBrowser
import java.time.Duration

class SlowAndMeaningful private constructor(
    private val browser: Class<out Browser>,
    private val region: Regions,
    private val duration: Duration
) : BenchmarkQuality {

    override fun provide(configProperties: String?): VirtualUsersSource = AwsVus(duration, region,
        System.getenv("vpcId"),
        System.getenv("subnetId"),
        configProperties
    )

    override fun behave(scenario: Class<out Scenario>, configProperties: String?): VirtualUserBehavior = VirtualUserBehavior.Builder(scenario)
        .browser(browser)
        .load(
            getVirtualUserLoad(configProperties)
        )
        .skipSetup(true)
        .seed(12345L)
        .build()

    private fun getVirtualUserLoad(configProperties: String?): VirtualUserLoad {
        return if (configProperties.isNullOrEmpty()) {
            VirtualUserLoad.Builder()
                .virtualUsers(72)
                .ramp(Duration.ofMinutes(1))
                .maxOverallLoad(TemporalRate(15.0, Duration.ofSeconds(1)))
                .flat(duration)
                .build()
        } else {
            VirtualUserLoad.Builder()
                .virtualUsers((ConfigProperties.load(configProperties).virtualUsers) ?: 72)
                .ramp(Duration.ofMinutes((ConfigProperties.load(configProperties).ramp) ?: 1))
                .maxOverallLoad(TemporalRate((ConfigProperties.load(configProperties).maxOverallLoad)
                    ?: 15.0, Duration.ofSeconds(1)))
                .flat(duration)
                .build()
        }
    }

    class Builder {
        private var browser: Class<out Browser> = EagerChromeBrowser::class.java
        private var region: Regions = Regions.US_EAST_1
        private var duration: Duration = Duration.ofMinutes(20)

        fun browser(browser: Class<out Browser>) = apply { this.browser = browser }
        fun region(region: Regions) = apply { this.region = region }
        fun duration(duration: Duration) = apply { this.duration = duration }

        fun build(): BenchmarkQuality {
            return SlowAndMeaningful(
                browser,
                region,
                duration
            )
        }
    }
}



