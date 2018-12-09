package at.fhooe.mc.wifipositioning.utility

import java.text.SimpleDateFormat
import java.util.Date

object DateUtil {
    fun getDate(timeStamp: Long): String {
        val scanResultTimestampInMillisSinceEpoch = System.currentTimeMillis() - System.currentTimeMillis() + timeStamp / 1000

        try {
            val dateFormat = SimpleDateFormat("HH:mm:ss dd.MM.yyyy")
            val netDate = Date(scanResultTimestampInMillisSinceEpoch)
            return dateFormat.format(netDate)
        } catch (ex: Exception) {
            return "timestamp error"
        }

    }

}
