package at.fhooe.mc.wifipositioning.model.simulation

import at.fhooe.mc.wifipositioning.model.building.Floor
import at.fhooe.mc.wifipositioning.model.graphics.FloorManager
import at.fhooe.mc.wifipositioning.model.graphics.Matrix

import java.awt.*
import java.awt.image.BufferedImage
import java.awt.image.ColorModel
import java.util.Observable

abstract class BaseModel : Observable() {
    var matrix: Matrix
        protected set
    var floorManager: FloorManager? = null
        protected set
    private var panelWidth = 300
    private var panelHeight = 300
    private val zoomRect: Rectangle? = null

    init {
        matrix = Matrix()
    }

    /**
     * Sets the panel size in case the window is resized.
     *
     * @param width  - the new width of the panel
     * @param height - the new height of the panel
     */
    fun setPanelSize(width: Int, height: Int) {
        panelWidth = width - 1
        panelHeight = height - 1
    }

    abstract fun generateFloorMap(floor: Floor)

    /**
     * Zooms the entire world or the zooming rectangle to the screen size.
     *
     * @param wholeWorld - determines if the world or only a zooming rectangle are scaled
     */
    fun zoomToFit(wholeWorld: Boolean) {
        if (floorManager != null && floorManager!!.floor!!.floorImage != null) {
            var world: Rectangle?

            if (wholeWorld) {
                world = Rectangle(floorManager!!.floor!!.floorImage!!.raster.bounds)
            } else {
                world = Rectangle(zoomRect!!)
            }

            val window = Rectangle(0, 0, panelWidth, panelHeight)

            matrix = Matrix.zoomToFit(world, window).multiply(Matrix())


            callObserver(createBufferedImage() ?: return)
        }
    }

    protected fun applyMatrixToBufferedImage(originalImage: BufferedImage): BufferedImage {
        val colorModel = ColorModel.getRGBdefault()
        val raster = colorModel.createCompatibleWritableRaster(panelWidth, panelHeight)
        val originalRaster = originalImage.raster

        for (i in 0 until originalRaster.width) {
            for (j in 0 until originalRaster.height) {
                val data = originalRaster.getPixel(i, j, null as IntArray?)
                val p = matrix.multiply(Point(i, j))
                if (p.getX() < raster.width && p.getY() < raster.height && p.getX() >= 0 && p.getY() >= 0) {
                    raster.setPixel(p.getX().toInt(), p.getY().toInt(), data)
                }
            }
        }

        return BufferedImage(colorModel, raster, true, null)
    }

    /**
     * Calls the observer if the buffered image changed.
     *
     * @param _data - the current buffered image
     */
    fun callObserver(_data: BufferedImage) {
        setChanged()

        //notifies the Observer of the changed objects
        notifyObservers(_data)
    }

    abstract fun createBufferedImage(): BufferedImage?

    fun resetImage() {
        callObserver(createBufferedImage() ?: return)
    }
}
