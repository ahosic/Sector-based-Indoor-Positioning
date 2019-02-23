package at.fhooe.mc.wifipositioning.interfaces

import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint

interface PlaybackCallbackInterface {
    fun nearestAccessPoint(scannedAccessPoints: List<ScannedAccessPoint>)
    fun allAccessPoints(scannedAccessPointList: List<ScannedAccessPoint>)
    fun wayPointCount(wayPointCount: IntArray)
}
