package at.fhooe.mc.wifipositioning.model.configuration

import at.fhooe.mc.wifipositioning.interfaces.PlaybackCallbackInterface
import at.fhooe.mc.wifipositioning.model.building.BuildingGraphNode
import at.fhooe.mc.wifipositioning.model.building.Building
import at.fhooe.mc.wifipositioning.model.recording.Route
import at.fhooe.mc.wifipositioning.model.positioning.*
import at.fhooe.mc.wifipositioning.sectoring.VoronoiSectors
import at.fhooe.mc.wifipositioning.model.recording.DataSnapshot
import at.fhooe.mc.wifipositioning.model.recording.Player
import at.fhooe.mc.wifipositioning.sectoring.Sectoring
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO
import javax.imageio.ImageReader

class ConfigurationModel(private val settingsPath: String): Observable() {
    var settings: Settings
        private set
    lateinit var building: Building
        private set
    lateinit var sectoring: Sectoring
        private set
    lateinit var positioning: Positioning
        private set
    lateinit var graph: List<BuildingGraphNode>
        private set
    lateinit var route: Route
        private set
    lateinit var player: Player
        private set

    init {
        settings = loadConfiguration()!!
    }

    fun resetSectoring() {
        sectoring = VoronoiSectors()
    }

    fun resetPositioning() {
        when(settings.positioningType) {
            PositioningType.STRONGEST_AP_POSITIONING -> positioning = StrongestAccessPointPositioning(building.accessPoints)
            PositioningType.AVERAGE_POSITIONING -> positioning = AveragePositioning(building.accessPoints, 5)
            PositioningType.FILTERED_POSITIONING -> positioning = FilteredPositioning(building.accessPoints)
            PositioningType.GRAPH_POSITIONING -> positioning = GraphPositioning(building.accessPoints, graph, 5)
        }
    }

    private fun loadConfiguration(): Settings? {
        var settings: Settings? = null
        try {
            val mapper = ObjectMapper().registerKotlinModule()
            val configFile = File(settingsPath)
            settings = mapper.readValue(configFile, object : TypeReference<Settings>() {})
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return settings
    }

    fun saveConfiguration(settings: Settings) {
        this.settings = settings

        try {
            val mapper = ObjectMapper().registerKotlinModule()
            val configFile = File(settingsPath)
            mapper.writeValue(configFile, settings)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        setChanged()
        notifyObservers()
    }

    fun loadBuilding() {
        try {
            val mapper = ObjectMapper().registerKotlinModule()
            val buildingFile = File(settings.buildingPath)
            building = mapper.readValue(buildingFile, object: TypeReference<Building>() {})
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadFloors() {
        val floorFile = File(settings.floorsPath)
        val inputStream = ImageIO.createImageInputStream(floorFile)
        val iterator = ImageIO.getImageReaders(inputStream!!)

        if (iterator == null || !iterator.hasNext()) throw IOException("Image file format not supported by ImageIO")

        val reader = iterator.next() as ImageReader
        reader.input = inputStream

        for (i in 0 until reader.getNumImages(true)) {
            building!!.floors[i].floorImage = reader.read(i)
        }

        sectoring = VoronoiSectors()
    }

    fun loadWaypoints() {
        try {
            val mapper = ObjectMapper().registerKotlinModule()
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            val waypointFile = File(settings.routePath)
            route = mapper.readValue(waypointFile, object : TypeReference<Route>() {})
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadWalkRecording(playbackCallback: PlaybackCallbackInterface) {
        try {
            val mapper = ObjectMapper()
            val simulationFile = File(settings.walkRecordingPath)
            val dataSnapshots: List<DataSnapshot> = mapper.readValue(simulationFile, object : TypeReference<List<DataSnapshot>>() {})
            player = Player(dataSnapshots, playbackCallback)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadBuildingGraph() {
        println("Load building graph.")

        try {
            val mapper = ObjectMapper().registerKotlinModule()
            val graphFile = File(settings.buildingGraphPath)
            graph = mapper.readValue(graphFile, object: TypeReference<List<BuildingGraphNode>>() {})
        } catch (e: IOException) {
            e.printStackTrace()
        }

        print("Loaded " + graph.size + " graph nodes.")
    }

    fun loadPositioningMethod() {
        when(settings.positioningType) {
            PositioningType.STRONGEST_AP_POSITIONING -> positioning = StrongestAccessPointPositioning(building.accessPoints)
            PositioningType.AVERAGE_POSITIONING -> positioning = AveragePositioning(building.accessPoints, 5)
            PositioningType.FILTERED_POSITIONING -> positioning = FilteredPositioning(building.accessPoints)
            PositioningType.GRAPH_POSITIONING -> positioning = GraphPositioning(building.accessPoints, graph, 5)
        }
    }
}