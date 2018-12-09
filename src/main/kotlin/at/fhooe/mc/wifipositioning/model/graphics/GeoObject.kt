package at.fhooe.mc.wifipositioning.model.graphics

import java.awt.*
import java.util.Vector

/**
 * This class is a GeoObject representation. The geometry, the type and the name are stored here.
 */
class GeoObject {

    /**
     * Gets the polygon(s) of this GeoObject.
     *
     * @return - the polygon(s)
     */
    var polygon: Vector<Polygon>? = Vector()
        internal set
    /**
     * Gets the id of this GeoObject.
     *
     * @return - the id
     */
    var id: String? = null
        private set
    /**
     * Gets the type of this GeoObject.
     *
     * @return - the type
     */
    var type: Int = 0
        private set
    /**
     * Gets if this GeoObject is selected.
     *
     * @return true if it is selected; false otherwise
     */
    /**
     * Sets if this GeoObject is selected or not.
     *
     * @param _bool - the selection status
     */
    var selected = false

    /**
     * Calculates the bounding rectangle of this GeoObject and returns it.
     *
     * @return - the bounding box
     */
    val bounds: Rectangle?
        get() {
            if (polygon != null) {
                if (polygon!!.size > 0) {
                    var rect = Rectangle(polygon!![0].bounds)
                    var i = 1
                    while (i < polygon!!.size) {
                        rect = rect.union(polygon!![i].bounds)
                        i++
                    }
                    return rect
                }
            }
            return null

        }

    constructor(_id: String, _type: Int, _poly: Polygon) {
        id = _id
        type = _type
        polygon!!.add(Polygon(_poly.xpoints, _poly.ypoints, _poly.npoints))
    }

    constructor(_id: String, _type: Int, _polygons: Vector<Polygon>) {
        id = _id
        type = _type
        var i = 0
        while (i < _polygons.size) {
            polygon!!.add(_polygons[i])
            i++
        }

    }

    constructor(_geo: GeoObject) {
        id = _geo.id
        type = _geo.type
        var i = 0
        while (i < _geo.polygon!!.size) {
            polygon!!.add(_geo.polygon!![i])
            i++
        }
    }

    /**
     * Paints a graphic representation of this GeoObject.
     *
     * @param _g      - the graphics context
     * @param _matrix - the transformation matrix for the position and size values.
     */
    fun paint(_g: Graphics, _matrix: Matrix) {
        var i = 0
        while (i < polygon!!.size) {
            val poly = _matrix.multiply(polygon!![i])
            if (selected == true) {
                _g.color = Color(255, 0, 0)
            } else {
                _g.color = Color(0, 0, 255)
            }
            _g.drawPolygon(poly)
            i++
        }

    }

    /**
     * Prints a String representation of this GeoObject.
     *
     * @return the String of this GeoObject.
     */
    override fun toString(): String {
        var output = ""
        var i = 0
        while (i < polygon!!.size) {

            output = output + "ID: " + id + "\n" + "Type: " + type + "\n" + polygon!![i].toString() + "\n"
            i++
        }
        return output
    }

}
