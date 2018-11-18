package at.fhooe.mc.wifipositioning.model.configuration

import at.fhooe.mc.wifipositioning.interfaces.PlayBackEnum
import at.fhooe.mc.wifipositioning.interfaces.PlaybackCallbackInterface
import at.fhooe.mc.wifipositioning.model.initialisations.InitSimulatorData
import at.fhooe.mc.wifipositioning.model.positioning.FilteredPositioning
import at.fhooe.mc.wifipositioning.model.positioning.IPositioning
import at.fhooe.mc.wifipositioning.model.sectoring.ISectoring
import at.fhooe.mc.wifipositioning.model.sectoring.VoronoiSectors
import at.fhooe.mc.wifipositioning.model.simulation.recorder.network.DataSnapshot
import at.fhooe.mc.wifipositioning.model.simulation.simulator.Building
import at.fhooe.mc.wifipositioning.model.simulation.simulator.Floor
import at.fhooe.mc.wifipositioning.utility.Player
import com.beust.klaxon.Klaxon
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.*
import javax.imageio.ImageIO
import javax.imageio.ImageReader

class ConfigurationModel(val configPath: String): Observable() {
    var building: Building? = null
        private set
    var configuration: Configuration
        private set
    var sectoring: ISectoring? = null
        private set
    var positioning: IPositioning? = null
        private set

    init {
        configuration = loadConfiguration()!!
    }

    fun resetSectoring() {
        sectoring = VoronoiSectors()
    }

    fun resetPositioning() {
        positioning = FilteredPositioning(building!!.allAccessPoints)
    }

    fun loadConfiguration(): Configuration? {
        return Klaxon().parse<Configuration>(File(configPath))
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

    fun loadFloors() {
        val floorFile = File(configuration.floorsPath)
        val inputStream = ImageIO.createImageInputStream(floorFile)

        val iterator = ImageIO.getImageReaders(inputStream!!)
        if (iterator == null || !iterator.hasNext()) {
            throw IOException("Image file format not supported by ImageIO")
        }

        building = Building("Building")


        val reader = iterator.next() as ImageReader
        reader.input = inputStream

        for (i in 0 until reader.getNumImages(true)) {
            val bounds = InitSimulatorData.getFloorBounds(i)
            building?.addFloor(Floor("Floor", bounds[0], bounds[1], bounds[2], bounds[3], bounds[4], bounds[5], InitSimulatorData.getTargetFloorAccessPoints(i), reader.read(i)))
        }

        sectoring = VoronoiSectors()
        positioning = FilteredPositioning(building!!.allAccessPoints)
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
}