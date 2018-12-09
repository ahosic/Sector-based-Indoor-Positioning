package at.fhooe.mc.wifipositioning.model.simulation.simulator

class FloorBounds {
    var offsetLeft = -1.0
    var offsetRight = -1.0
    var offsetTop = -1.0
    var offsetBottom = -1.0
    var floorWidth = -1.0
    var floorHeight = -1.0

    constructor(offsetLeft: Double, offsetRight: Double, offsetTop: Double, offsetBottom: Double, floorWidth: Double, floorHeight: Double) {
        this.offsetLeft = offsetLeft
        this.offsetRight = offsetRight
        this.offsetTop = offsetTop
        this.offsetBottom = offsetBottom
        this.floorWidth = floorWidth
        this.floorHeight = floorHeight
    }
}
