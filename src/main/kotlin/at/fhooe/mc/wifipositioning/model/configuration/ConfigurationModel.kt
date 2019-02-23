package at.fhooe.mc.wifipositioning.model.configuration

import at.fhooe.mc.wifipositioning.interfaces.PlayBackEnum
import at.fhooe.mc.wifipositioning.interfaces.PlaybackCallbackInterface
import at.fhooe.mc.wifipositioning.model.building.BuildingGraphNode
import at.fhooe.mc.wifipositioning.model.building.NewBuilding
import at.fhooe.mc.wifipositioning.model.initialisations.InitSimulatorData
import at.fhooe.mc.wifipositioning.model.initialisations.Route
import at.fhooe.mc.wifipositioning.model.positioning.*
import at.fhooe.mc.wifipositioning.model.sectoring.ISectoring
import at.fhooe.mc.wifipositioning.model.sectoring.VoronoiSectors
import at.fhooe.mc.wifipositioning.model.simulation.recording.DataSnapshot
import at.fhooe.mc.wifipositioning.model.simulation.simulator.Building
import at.fhooe.mc.wifipositioning.model.simulation.simulator.Floor
import at.fhooe.mc.wifipositioning.utility.Player
import com.beust.klaxon.Klaxon
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO
import javax.imageio.ImageReader

class ConfigurationModel(val configPath: String): Observable() {
    var configuration: Configuration
        private set
    var building: NewBuilding? = null
        private set
    var sectoring: ISectoring? = null
        private set
    var positioning: IPositioning? = null
        private set
    var graph: List<BuildingGraphNode>? = null
        private set
    var route: Route? = null
        private set

    init {
        configuration = loadConfiguration()!!
    }

    fun resetSectoring() {
        sectoring = VoronoiSectors()
    }

    fun resetPositioning() {
        when(configuration.positioningType) {
            PositioningType.STRONGEST_AP_POSITIONING -> positioning = StrongestAccessPointPositioning(building!!.allAccessPoints)
            PositioningType.AVERAGE_POSITIONING -> positioning = AveragePositioning(building!!.allAccessPoints, 5)
            PositioningType.FILTERED_POSITIONING -> positioning = FilteredPositioning(building!!.allAccessPoints)
            PositioningType.GRAPH_POSITIONING -> positioning = GraphPositioning(building!!.allAccessPoints, graph!!, 5)
        }
    }

    fun loadConfiguration(): Configuration? {
        val config = Klaxon().parse<Configuration>(File(configPath))
        return config
    }

    fun saveConfiguration(configuration: Configuration) {
        this.configuration = configuration

        val klaxon = Klaxon()
        val json = klaxon.toJsonString(configuration)

        val configFile = File(configPath)
        configFile.bufferedWriter().use { out -> out.write(json) }

        setChanged()
        notifyObservers()
    }

    fun loadBuilding() {
        try {
            val mapper = ObjectMapper().registerKotlinModule()
            val buildingFile = File("/Users/almin/Documents/projects/master/resources/building.json")
            building = mapper.readValue(buildingFile, object: TypeReference<NewBuilding>() {})
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadFloors() {
        val floorFile = File(configuration.floorsPath)
        val inputStream = ImageIO.createImageInputStream(floorFile)

        val iterator = ImageIO.getImageReaders(inputStream!!)
        if (iterator == null || !iterator.hasNext()) {
            throw IOException("Image file format not supported by ImageIO")
        }

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
            val waypointFile = File(configuration.routePath)
            route = mapper.readValue(waypointFile, object : TypeReference<Route>() {})
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadWalkRecording(playbackCallback: PlaybackCallbackInterface): Player? {
        try {
            val mapper = ObjectMapper()
            val simulationFile = File(configuration.walkRecordingPath)
            val dataSnapshots: List<DataSnapshot> = mapper.readValue(simulationFile, object : TypeReference<List<DataSnapshot>>() {})

            return Player(dataSnapshots, playbackCallback, PlayBackEnum.SIMPLE)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun loadBuildingGraph() {
        println("Load building graph.")
        graph = Klaxon().parseArray<BuildingGraphNode>(FileInputStream(File(configuration.buildingGraphPath)))
        graph?.let {
            print("Loaded " + it.size + " graph nodes.")
        }
    }

    fun loadPositioningMethod() {
        when(configuration.positioningType) {
            PositioningType.STRONGEST_AP_POSITIONING -> positioning = StrongestAccessPointPositioning(building!!.allAccessPoints)
            PositioningType.AVERAGE_POSITIONING -> positioning = AveragePositioning(building!!.allAccessPoints, 5)
            PositioningType.FILTERED_POSITIONING -> positioning = FilteredPositioning(building!!.allAccessPoints)
            PositioningType.GRAPH_POSITIONING -> positioning = GraphPositioning(building!!.allAccessPoints, graph!!, 5)
        }
    }
}