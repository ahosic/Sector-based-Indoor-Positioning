package at.fhooe.mc.wifipositioning.model

import at.fhooe.mc.wifipositioning.App
import at.fhooe.mc.wifipositioning.debug.ApplicationState
import at.fhooe.mc.wifipositioning.interfaces.PlaybackCallbackInterface
import at.fhooe.mc.wifipositioning.model.building.Building
import at.fhooe.mc.wifipositioning.model.configuration.ConfigurationModel
import at.fhooe.mc.wifipositioning.model.graphics.FloorManager
import at.fhooe.mc.wifipositioning.model.positioning.Positioning
import at.fhooe.mc.wifipositioning.model.graphics.DrawingContext
import at.fhooe.mc.wifipositioning.model.building.Floor
import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint
import at.fhooe.mc.wifipositioning.model.building.Position
import at.fhooe.mc.wifipositioning.model.positioning.SectorEstimation
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

    private var currentEstimation: SectorEstimation? = null

    private var wayPointCount: IntArray? = null
    private var wayPointNumber = 1
    private var interpolationStep = 0

    private var playerThread: Thread? = null

    private val route: Route
        get() = config.route

    private val sectoring: Sectoring
        get() = config.sectoring

    private val positioning: Positioning
        get() = config.positioning

    private val building: Building
        get() = config.building

    private val player: Player
        get() = config.player

    init {
        config.addObserver(this)
    }

    fun reloadConfiguration() {
        // Reload

        config.loadBuilding()
        config.loadFloors()

        generateFloorMap(building.floors[4])

        config.loadBuildingGraph()
        config.loadPositioningMethod()
        config.loadWaypoints()
        config.loadWalkRecording(this)
    }

    fun toggleSimulation() {
        if (player.isRunning) {
            player.pausePlayback = !player.pausePlayback
            return
        }

        player.let { player ->
            playerThread = (object : Thread() {
                override fun run() {
                    player.startPlayback()
                }
            })

            player.stopPlayback = false
            playerThread?.start()
        }
    }

    fun resetSimulation() {
        if (player.isRunning) {
            player.stopPlayback = true
        }

        playerThread = null
        accessPoints = ArrayList()

        person = Position(-1000, 1000)
        actualPosition = Position(-1000, 1000)

        wayPointNumber = 1
        interpolationStep = 0

        currentEstimation = null
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
        if(!player.isRunning || player.stopPlayback) {
            return
        }

        lateinit var pos: Position
        if (wayPointNumber < wayPointCount!!.size) {
            pos = interpolate(route.waypointList[wayPointNumber - 1].position, route.waypointList[wayPointNumber].position, wayPointCount!![wayPointNumber - 1], interpolationStep)
        } else {
            wayPointCount?.let {
                pos = interpolate(route.waypointList[wayPointNumber - 1].position, route.waypointList[0].position, wayPointCount!![wayPointNumber - 1], interpolationStep)
            }
        }

        actualPosition.x = pos.x
        actualPosition.y = pos.y

        interpolationStep += 1
        if (interpolationStep == wayPointCount!![wayPointNumber - 1]) {
            wayPointNumber += 1
            interpolationStep = 0
        }
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

        currentEstimation = positioning.estimateSector(scannedAccessPointList)

        currentEstimation?.let { estimation ->
            floorManager?.let { floorManager ->
                val sectorPositions = estimation.sectors.map { sector -> floorManager.calculatePixelPositionFromMeter(sector.position.x, sector.position.y) }.toList()
                val transitionPositions = estimation.inTransition?.map { sector -> floorManager.calculatePixelPositionFromMeter(sector.position.x, sector.position.y) }?.toList()
                sectoring.addPositionsOfEstimatedSectors(sectorPositions, transitionPositions)
            }
        }

        callObserver(createBufferedImage() ?: return)
    }

    /**
     * Creates a buffered image of all the current GeoObjects. It is used because it is faster
     * than the usage of single objects.
     *
     * @return the created buffered image.
     */
    override fun createBufferedImage(): BufferedImage? {
        val floorManager = this.floorManager ?: return null

        val images = applyMatrixToBufferedImage(floorManager.floor.floorImage ?: return null)

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

        i = 0
        val wayPointPositions = route.waypointList
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

        sectoring.createSectors(floorManager, g, matrix)

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

    override fun allAccessPoints(scannedAccessPointList: List<ScannedAccessPoint>) {
        App.debugger.state = ApplicationState(scannedAccessPointList,
                currentEstimation,
                null,
                wayPointNumber,
                interpolationStep)

        addPlayerData(scannedAccessPointList)
    }

    override fun wayPointCount(wayPointCount: IntArray) {
        this.wayPointCount = wayPointCount
    }

    override fun update(o: Observable?, arg: Any?) {
        if(o != config) return

        reloadConfiguration()

        println("Reloaded settings.")
    }
}
