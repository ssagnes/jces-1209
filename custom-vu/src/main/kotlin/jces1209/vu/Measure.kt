package jces1209.vu

import com.atlassian.performance.tools.jiraactions.api.ActionType
import com.atlassian.performance.tools.jiraactions.api.SeededRandom
import com.atlassian.performance.tools.jiraactions.api.measure.ActionMeter
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import javax.json.JsonObject

class Measure(
    private val meter: ActionMeter,
    private val random: SeededRandom
) {
    private val logger: Logger = LogManager.getLogger(this::class.java)
    private val isSilentDefault = false

    fun <T> measure(
        key: ActionType<*>,
        probability: Float = 1.0f,
        preconditions: (() -> Any)? = null,
        observation: ((T) -> JsonObject?)? = null,
        isSilent: Boolean = isSilentDefault,
        action: () -> T
    ): T? {
        return roll(isSilent, probability) {
            preconditions?.invoke()
            if (null == observation) {
                return@roll meter.measure(key, action)
            } else {
                return@roll meter.measure(key, action, observation)
            }
        }
    }

    fun <T> silent(isSilent: Boolean = isSilentDefault, action: () -> T): T? {
        try {
            return action.invoke()
        } catch (e: Throwable) {
            if (isSilent) {
                logger.error("Failed to execute action", e)
            } else {
                throw e
            }
        }
        return null
    }

    fun <T> roll(probability: Float, action: () -> T): T? {
        return roll(isSilentDefault, probability, action)
    }

    fun <T> roll(isSilent: Boolean, probability: Float, action: () -> T): T? {
        if (random.random.nextFloat() < probability) {
            return silent(isSilent, action)
        }
        return null
    }
}
