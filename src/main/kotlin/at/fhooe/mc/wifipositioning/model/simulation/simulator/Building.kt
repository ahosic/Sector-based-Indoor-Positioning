package at.fhooe.mc.wifipositioning.model.simulation.simulator

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint

import java.util.ArrayList

class Building @JvmOverloads constructor(private val name: String, floorList: ArrayList<Floor> = ArrayList()) {

    private val floorList: MutableList<Floor>

    val numberOfFloors: Int
        get() = floorList.size

    val allFloors: List<Floor>
        get() = floorList

    val allAccessPoints: List<InstalledAccessPoint>
        get() {
            val accessPointList = ArrayList<InstalledAccessPoint>()
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
