package at.fhooe.mc.wifipositioning.debug

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A log entry that is used by a debugger.
 *
 * @property tag the Tag of the object that issued the log entry
 * @property message the message of the entry
 * @property category the category of the message
 * @property timestamp the timestamp of the message
 */
data class DebugLogEntry(val tag: String,
                         val message: String,
                         val category: DebugLogEntryCategory = DebugLogEntryCategory.General,
                         val timestamp: LocalDateTime = LocalDateTime.now()) {

    override fun toString(): String {
        return "[$tag] $message"
    }
}