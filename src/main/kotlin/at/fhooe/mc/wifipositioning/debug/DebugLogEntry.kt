package at.fhooe.mc.wifipositioning.debug

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class DebugLogEntry(val tag: String,
                         val message: String,
                         val category: DebugLogEntryCategory = DebugLogEntryCategory.General,
                         val timestamp: LocalDateTime = LocalDateTime.now()) {

    override fun toString(): String {
        return "[$tag] $message"
    }
}