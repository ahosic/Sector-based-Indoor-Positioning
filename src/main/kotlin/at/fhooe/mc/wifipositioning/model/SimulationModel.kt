package at.fhooe.mc.wifipositioning.model

import at.fhooe.mc.wifipositioning.interfaces.PlaybackCallbackInterface
import at.fhooe.mc.wifipositioning.model.configuration.ConfigurationModel
import at.fhooe.mc.wifipositioning.model.graphics.FloorManager
import at.fhooe.mc.wifipositioning.model.positioning.Positioning
import at.fhooe.mc.wifipositioning.model.graphics.DrawingContext
import at.fhooe.mc.wifipositioning.model.building.Floor
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.building.Position
import at.fhooe.mc.wifipositioning.model.recording.Route
import at.fhooe.mc.wifipositioning.model.recording.Waypoint
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint
import at.fhooe.mc.wifipositioning.model.recording.Player
import at.fhooe.mc.wifipositioning.sectoring.Sectoring

import java.awt.*
import java.awt.image.BufferedImage
import java.util.*

/**
 * The Model of the MVC pattern. The new positions and polygons are drawn here.
 */
class SimulationModel(var config: ConfigurationModel) : BaseModel(), PlaybackCallbackInterface, Observer {
    private var accessPoints: List<InstalledAccessPoint>? = ArrayList()
    private var person = Position(-1000, 1000)
    private var actualPosition = Position(-1000, 1000)

    private var route: Route? = null
        get() = config.route

    private var wayPointCount: IntArray? = null
    private var wayPointNumber = 1
    private var interpolationStep = 0

    private var player: Player? = null
    private var playerThread: Thread? = null

    val sectoring: Sectoring?
        get() = config.sectoring

    val positioning: Positioning?
        get() = config.positioning

    init {
        config.addObserver(this)
    }

    fun reloadConfiguration() {
        config.loadBuilding()
        config.loadFloors()

        config.building?.let { building ->
            generateFloorMap(building.floors[4])
        }

        config.loadBuildingGraph()
        config.loadPositioningMethod()
        config.loadWaypoints()
        player = config.loadWalkRecording(this)
    }

    fun toggleSimulation() {
        if (player?.isRunning == true) {
            player?.pausePlayback = player?.pausePlayback != true
            return
        }

        player?.let { player ->
            playerThread = (object : Thread() {
                override fun run() {
                    player.startPlayback()
                }
            })

            playerThread?.let {
                it.start()
            }
        }
    }

    fun resetSimulation() {
        player?.stopPlayback = true
        playerThread = null

        person = Position(-1000, 1000)
        actualPosition = Position(-1000, 1000)

        wayPointNumber = 1
        interpolationStep = 0

        config?.resetPositioning()
        config?.resetSectoring()
    }

    override fun generateFloorMap(floor: Floor) {
        accessPoints = floor.accessPoints
        if (floorManager == null) {
            floorManager = FloorManager(floor)
        } else {
            floorManager?.changeFloor(floor)
        }
    }

    private fun checkForInitializedWaypoints() {
        if(player?.isRunning == false || player?.stopPlayback == true || route == null) {
            return
        }

        var pos: Position? = null
        route?.let { route ->
            if (wayPointNumber < wayPointCount!!.size) {
                pos = interpolate(route.waypointList[wayPointNumber - 1].position, route.waypointList[wayPointNumber].position, wayPointCount!![wayPointNumber - 1], interpolationStep)
            } else {
                wayPointCount?.let {
                    pos = interpolate(route.waypointList[wayPointNumber - 1].position, route.waypointList[0].position, wayPointCount!![wayPointNumber - 1], interpolationStep)
                }
            }
        }

        pos?.let {pos ->
            actualPosition.x = pos.x
            actualPosition.y = pos.y

            interpolationStep += 1
            if (interpolationStep == wayPointCount!![wayPointNumber - 1]) {
                wayPointNumber += 1
                interpolationStep = 0
            }
        }
    }

    private fun calculateSimplePersonPosition(scannedAccessPoints: List<ScannedAccessPoint>) {
        checkForInitializedWaypoints()
        for (accessPoint in scannedAccessPoints) {
            for (ap in this.accessPoints!!) {
                if (ap.bssid.toLowerCase() == accessPoint.bssid.toLowerCase()) {
                    person.x = ap.position.x
                    person.y = ap.position.y
                    callObserver(createBufferedImage() ?: return)
                    return
                }
            }
        }

        callObserver(createBufferedImage() ?: return)
    }

    private fun interpolate(start: Position, end: Position, steps: Int, iterationStep: Int): Position {
        if (steps < 2) {
            return Position(person.x, person.y)
        }

        val xPos = Math.round((start.x + iterationStep * (end.x - start.x) / steps).toFloat())
        val yPos = Math.round((start.y + iterationStep * (end.y - start.y) / steps).toFloat())

        return Position(xPos, yPos)
    }

    private fun addPlayerData(scannedAccessPointList: List<ScannedAccessPoint>) {
        checkForInitializedWaypoints()
        val p = positioning?.calculatePosition(scannedAccessPointList)
        if (sectoring == null || p == null) {
            callObserver(createBufferedImage() ?: return)
            return
        }
        val newPos = floorManager?.calculatePixelPositionFromMeter(p.position.x, p.position.y)
        newPos?.let {
            sectoring!!.addCurrentPosition(Position(Math.round(newPos.x.toFloat()), Math.round(newPos.y.toFloat())))
            callObserver(createBufferedImage() ?: return)
        }
    }

    /**
     * Creates a buffered image of all the current GeoObjects. It is used because it is faster
     * than the usage of single objects.
     *
     * @return the created buffered image.
     */
    override fun createBufferedImage(): BufferedImage? {
        val floorManager = this.floorManager ?: return null

        val images = applyMatrixToBufferedImage(floorManager.floor?.floorImage ?: return null)

        val g = images.createGraphics()
        val clipRect = Rectangle(floorManager.offsetLeftInPixel - 10, floorManager.offsetTopInPixel - 10, floorManager.offsetRightInPixel - floorManager.offsetLeftInPixel + 15, floorManager.offsetBottomInPixel - floorManager.offsetTopInPixel + 15)
        g.clip = Rectangle(matrix.multiply(clipRect))
        g.color = Color(0, 0, 0)

        g.drawOval(0, 0, 10, 10)
        var i = 0

        i = 0

        accessPoints?.let { accessPoints ->
            while (i < accessPoints.size) {
                val ap = accessPoints[i]
                val pos = floorManager.calculatePixelPositionFromMeter(ap.position.x, ap.position.y)
                DrawingContext.drawAccessPoint(ap, pos.x, pos.y, g, matrix)
                i++
            }
        }

        if (route != null) {
            i = 0
            val wayPointPositions = route!!.waypointList
            val wayPointPositions2 = ArrayList<Waypoint>()

            while (i < wayPointPositions.size) {
                val waypointPosition = wayPointPositions[i]
                val pos = floorManager.calculatePixelPositionFromMeter(waypointPosition.position.x, waypointPosition.position.y)
                val waypointPosition1 = Waypoint(waypointPosition.id, waypointPosition.description, waypointPosition.floor, Position(pos.x, pos.y))
                wayPointPositions2.add(waypointPosition1)

                DrawingContext.drawWayPointPosition(waypointPosition1, g, matrix)
                i++
            }
            DrawingContext.drawWayPointLine(wayPointPositions2, g, matrix)
        }

        if (sectoring != null) {
            sectoring!!.createSectors(floorManager, g, matrix)
        }

        var pos = floorManager.calculatePixelPositionFromMeter(person.x, person.y)
        person.x = pos.x
        person.y = pos.y
        DrawingContext.drawPerson(person, g, matrix)

        pos = floorManager.calculatePixelPositionFromMeter(actualPosition.x, actualPosition.y)
        actualPosition.x = pos.x
        actualPosition.y = pos.y
        DrawingContext.drawActualPosition(actualPosition, g, matrix)


        return images
    }

    override fun nearestAccessPoint(scannedAccessPoints: List<ScannedAccessPoint>) {
        scannedAccessPoints?.let {
            calculateSimplePersonPosition(scannedAccessPoints)
        }
    }

    override fun allAccessPoints(scannedAccessPointList: List<ScannedAccessPoint>) {
        scannedAccessPointList?.let {
            addPlayerData(scannedAccessPointList)
        }
    }

    override fun wayPointCount(wayPointCount: IntArray) {
        wayPointCount?.let {
            this.wayPointCount = wayPointCount
        }
    }

    override fun update(o: Observable?, arg: Any?) {
        if(o != config) return

        resetSimulation()
        reloadConfiguration()

        println("Reloaded configuration.")
    }
}
