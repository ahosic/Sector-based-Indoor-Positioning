package at.fhooe.mc.wifipositioning.model.initialisations

import at.fhooe.mc.wifipositioning.model.simulation.Position

import java.util.ArrayList

class WaypointRoute {

    var waypointPositionList: MutableList<WaypointPosition> = ArrayList()
    private set

    init {
        var waypointPosition = WaypointPosition(1, Position(79, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(2, Position(74, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(3, Position(71, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(4, Position(65, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(5, Position(62, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(6, Position(54, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(7, Position(52, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(8, Position(50, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(9, Position(48, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(10, Position(36, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(11, Position(33, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(12, Position(31, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(13, Position(28, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(14, Position(31, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(15, Position(11, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(16, Position(9, 11))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(17, Position(9, 12))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(18, Position(9, 15))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(19, Position(13, 15))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(20, Position(13, 17))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(21, Position(13, 25))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(22, Position(13, 40))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(23, Position(9, 40))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(24, Position(9, 47))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(25, Position(12, 47))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(26, Position(21, 47))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(27, Position(23, 47))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(28, Position(26, 47))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(29, Position(31, 47))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(30, Position(39, 47))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(31, Position(48, 46))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(32, Position(58, 45))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(33, Position(64, 44))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(34, Position(70, 43))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(35, Position(71, 41))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(36, Position(71, 35))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(37, Position(71, 19))
        waypointPositionList.add(waypointPosition)
        waypointPosition = WaypointPosition(38, Position(71, 14))
        waypointPositionList.add(waypointPosition)
    }

    fun getWayPointPositionAtIndex(index: Int): WaypointPosition {
        return waypointPositionList[index]
    }
}
