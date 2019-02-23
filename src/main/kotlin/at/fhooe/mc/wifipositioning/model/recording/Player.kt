package at.fhooe.mc.wifipositioning.model.recording

import at.fhooe.mc.wifipositioning.interfaces.PlaybackCallbackInterface

class Player(private var dataSnapshots: List<DataSnapshot>, private var callback: PlaybackCallbackInterface) {
    private var playBackSpeed: Long = 1
    private var wayPointCount: IntArray? = null
    var isRunning = false
    private set
    var pausePlayback = false
    var stopPlayback = false

    init {
        setNumberOfAPSteps()
    }

    private fun setNumberOfAPSteps() {
        wayPointCount = IntArray(dataSnapshots[dataSnapshots.size - 1].wayPointID)
        wayPointCount?.let { wayPointCount ->
            for (dataSnapshot in dataSnapshots) {
                wayPointCount[dataSnapshot.wayPointID - 1] += 1
            }
            callback.wayPointCount(wayPointCount)
        }
    }

    fun startPlayback() {
        println(dataSnapshots.size)
        isRunning = true
        var i = 1
        for (dataSnapshot in dataSnapshots) {

            while (pausePlayback) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {}
            }

            if (stopPlayback) {
                isRunning = false
                pausePlayback = false
                stopPlayback = false
                return
            }

            //println("Snapshot $i")
            i++

            dataSnapshot.wifiDataList
                    .stream()
                    .filter { wd -> wd.ssid == "fhhgb" }
                    .forEach {
                        callback.allAccessPoints(it.scannedAccessPoints)
                    }

            try {
                Thread.sleep(playBackSpeed)
            } catch (e: InterruptedException) {
            }

        }

        isRunning = false
        pausePlayback = false
        return
    }
}
