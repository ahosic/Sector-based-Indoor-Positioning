package at.fhooe.mc.wifipositioning.model.initialisations


import at.fhooe.mc.wifipositioning.model.simulation.Position
import at.fhooe.mc.wifipositioning.model.simulation.recorder.network.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.simulation.recorder.network.ScannedAccessPoint

import java.util.ArrayList

object InitSimulatorData {
    private var accessPointListList: MutableList<List<InstalledAccessPoint>>? = null

    fun getTargetFloorAccessPoints(floor: Int): MutableList<InstalledAccessPoint> {
        if (accessPointListList == null) {
            accessPointListList = ArrayList()
            accessPointListList!!.add(initAccessPointsFloor0())
            accessPointListList!!.add(initAccessPointsFloor1())
            accessPointListList!!.add(initAccessPointsFloor2())
            accessPointListList!!.add(initAccessPointsFloor3())
            accessPointListList!!.add(initAccessPointsFloor4())
        }

        return accessPointListList!![floor].toMutableList()

    }

    private fun initAccessPointsFloor0(): List<InstalledAccessPoint> {
        val accessPoints = ArrayList<InstalledAccessPoint>()
        var accessPoint = InstalledAccessPoint("", Position(40, 10), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(59, 6), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(78, 18), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(12, 25), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(37, 25), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(6, 40), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(11, 38), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(16, 40), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(35, 36), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(48, 34), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(5, 53), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(16, 52), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(42, 55), 0)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(44, 54), 0)
        accessPoints.add(accessPoint)

        return accessPoints
    }

    private fun initAccessPointsFloor1(): List<InstalledAccessPoint> {
        val accessPoints = ArrayList<InstalledAccessPoint>()
        var accessPoint = InstalledAccessPoint("", Position(15, 7), 1)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(7, 21), 1)
        accessPoints.add(accessPoint)

        return accessPoints
    }

    private fun initAccessPointsFloor2(): List<InstalledAccessPoint> {
        val accessPoints = ArrayList<InstalledAccessPoint>()
        var accessPoint = InstalledAccessPoint("08:EA:44:37:29:14", Position(16, 14), 2)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("9C:5D:12:5E:13:14", Position(45, 10), 2)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("9C:5D:12:5E:1D:14", Position(1, 29), 2)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:24:14", Position(12, 47), 2)
        accessPoints.add(accessPoint)

        return accessPoints
    }

    private fun initAccessPointsFloor3(): List<InstalledAccessPoint> {
        val accessPoints = ArrayList<InstalledAccessPoint>()
        var accessPoint = InstalledAccessPoint("", Position(1, 1), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(28, 1), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(51, 1), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(73, 1), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(39, 9), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(19, 12), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(33, 14), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(48, 19), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(65, 13), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(1, 26), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(14, 32), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(79, 20), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(23, 47), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(63, 45), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(41, 54), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(57, 52), 3)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("", Position(73, 50), 3)
        accessPoints.add(accessPoint)

        return accessPoints
    }

    private fun initAccessPointsFloor4(): List<InstalledAccessPoint> {
        val accessPoints = ArrayList<InstalledAccessPoint>()
        var accessPoint = InstalledAccessPoint("08:EA:44:37:29:14", Position(10, 2), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("9C:5D:12:5E:13:14", Position(30, 2), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("9C:5D:12:5E:1D:14", Position(53, 2), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:24:14", Position(73, 2), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("00:19:77:4E:5D:94", Position(20, 12), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:63:D4", Position(31, 15), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:25:D4", Position(39, 8), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("9C:5D:12:5D:02:94", Position(55, 15), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:62:94", Position(64, 12), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:69:94", Position(73, 17), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:63:54,", Position(1, 27), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:29:94", Position(16, 31), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:64:54", Position(7, 48), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:25:14", Position(20, 56), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:2A:94", Position(22, 50), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:63:94", Position(47, 54), 4)
        accessPoints.add(accessPoint)
        accessPoint = InstalledAccessPoint("08:EA:44:37:64:94", Position(64, 50), 4)
        accessPoints.add(accessPoint)

        return accessPoints
    }

    fun getFloorBounds(index: Int): DoubleArray {
        when (index) {
            0 -> return doubleArrayOf(5.2, 5.4, 20.0, 20.5, 80.0, 60.0)
            1 -> return doubleArrayOf(4.4, 70.0, 24.0, 8.5, 20.0, 35.0)
            2 -> return doubleArrayOf(4.1, 30.9, 11.0, 8.1, 60.0, 60.0)
            3 -> return doubleArrayOf(3.9, 6.2, 9.3, 9.0, 80.0, 60.0)
            4 -> return doubleArrayOf(4.3, 6.0, 9.3, 9.0, 80.0, 60.0)
            else -> return doubleArrayOf(0.0, 0.0, 0.0, 0.0, 80.0, 60.0)
        }
    }
}
