package at.fhooe.mc.wifipositioning.model.simulation.simulator

import at.fhooe.mc.wifipositioning.model.simulation.recorder.network.AccessPoint

import java.util.ArrayList

class Building @JvmOverloads constructor(private val name: String, floorList: ArrayList<Floor> = ArrayList()) {

    private val floorList: MutableList<Floor>

    val numberOfFloors: Int
        get() = floorList.size

    val allFloors: List<Floor>
        get() = floorList

    val allAccessPoints: List<AccessPoint>
        get() {
            val accessPointList = ArrayList<AccessPoint>()
            for (floor in floorList) {
                floor.accessPointList?.let { accessPoints ->
                    accessPointList.addAll(accessPoints)
                }
            }
            return accessPointList
        }

    init {
        this.floorList = floorList
    }

    fun addFloor(floor: Floor) {
        floorList.add(floor)
    }

    fun getFloor(index: Int): Floor {
        if (index > floorList.size) {
            throw IndexOutOfBoundsException()
        }

        return floorList[index]
    }


}