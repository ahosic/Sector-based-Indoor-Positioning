package at.fhooe.mc.wifipositioning.model.simulation.simulator

import at.fhooe.mc.wifipositioning.model.simulation.recorder.network.AccessPoint
import com.fasterxml.jackson.annotation.JsonIgnore

import java.awt.image.BufferedImage


class Floor {
    var name: String? = null
        private set

    var floorBounds: FloorBounds? = null

    @JsonIgnore
    var floorImage: BufferedImage? = null
        private set

    var accessPointList: MutableList<AccessPoint>? = null

    val offsetLeft: Double
        get() = floorBounds?.offsetLeft ?: 0.0

    val offsetRight: Double
        get() = floorBounds?.offsetRight ?: 0.0

    val offsetTop: Double
        get() = floorBounds?.offsetTop ?: 0.0

    val offsetBottom: Double
        get() = floorBounds?.offsetBottom ?: 0.0

    val floorWidth: Double
        get() = floorBounds?.floorWidth ?: 0.0

    val floorHeight: Double
        get() = floorBounds?.floorHeight ?: 0.0

    constructor(name: String, offsetLeft: Double, offsetRight: Double, offsetTop: Double, offsetBottom: Double, floorWidth: Double, floorHeight: Double, accessPointList: MutableList<AccessPoint>, floorImage: BufferedImage) {
        this.name = name
        this.floorBounds = FloorBounds(offsetLeft, offsetRight, offsetTop, offsetBottom, floorWidth, floorHeight)
        this.accessPointList = accessPointList
        this.floorImage = floorImage
    }

    constructor(name: String, floorBounds: FloorBounds, accessPointList: MutableList<AccessPoint>, floorImage: BufferedImage) {
        this.name = name
        this.floorBounds = floorBounds
        this.accessPointList = accessPointList
        this.floorImage = floorImage
    }

    constructor(name: String, floorImage: BufferedImage) {
        this.name = name
        this.floorBounds = floorBounds
        this.accessPointList = accessPointList
        this.floorImage = floorImage
    }

    fun getAccessPoint(index: Int): AccessPoint {
        return accessPointList!![index]
    }
}
