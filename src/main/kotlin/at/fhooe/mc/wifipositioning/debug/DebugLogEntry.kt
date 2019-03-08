package at.fhooe.mc.wifipositioning.debug

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class DebugLogEntry(val message: String,
                         val category: DebugLogEntryCategory = DebugLogEntryCategory.General,
                         val timestamp: LocalDateTime = LocalDateTime.now()) {

    override fun toString(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val formattedTime = timestamp.format(formatter)

        return "[$formattedTime] $message"
    }
}