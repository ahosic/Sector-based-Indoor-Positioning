package at.fhooe.mc.wifipositioning.model.graphics

/**
 * A point representation which calculates its position with polar coordinates.
 */
class GeoDoublePoint(var x: Double, var y: Double) {

    /**
     * Calculates the distance from the center of the coordinate system to the point.
     * (Polar coordinate)
     *
     * @return - the distance of the point
     */
    fun length(): Double {
        return Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0))
    }

    /**
     * Prints a string representation of this point with its coordinates.
     *
     * @return the string of this point
     */
    override fun toString(): String {
        return "Point Coordinates: x: $x y: $y"
    }

}
