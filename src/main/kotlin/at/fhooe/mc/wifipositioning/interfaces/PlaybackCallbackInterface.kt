package at.fhooe.mc.wifipositioning.interfaces

import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

interface PlaybackCallbackInterface {
    fun allAccessPoints(scannedAccessPointList: List<ScannedAccessPoint>)
    fun wayPointCount(wayPointCount: IntArray)
}
