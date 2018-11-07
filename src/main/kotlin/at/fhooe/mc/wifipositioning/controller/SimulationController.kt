package at.fhooe.mc.wifipositioning.controller

import at.fhooe.mc.wifipositioning.model.simulation.SimulationModel
import at.fhooe.mc.wifipositioning.model.initialisations.InitSimulatorData
import at.fhooe.mc.wifipositioning.model.simulation.recorder.network.AccessPoint
import at.fhooe.mc.wifipositioning.model.simulation.recorder.network.DataSnapshot
import at.fhooe.mc.wifipositioning.model.simulation.simulator.Building
import at.fhooe.mc.wifipositioning.model.simulation.simulator.Floor
import at.fhooe.mc.wifipositioning.model.positioning.FilteredPositioning
import at.fhooe.mc.wifipositioning.model.sectoring.VoronoiSectors
import at.fhooe.mc.wifipositioning.utility.Player
import at.fhooe.mc.wifipositioning.interfaces.PlayBackEnum
import at.fhooe.mc.wifipositioning.interfaces.PlaybackCallbackInterface
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import javafx.event.ActionEvent
import java.awt.Dimension
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.imageio.ImageReader

class SimulationController(val model: SimulationModel) : PlaybackCallbackInterface {

    private var player: Player? = null
    private var building: Building? = null
    var dimensions: Dimension? = null

    init {
        ImageIO.scanForPlugins()
    }

    fun onPlay(event: ActionEvent) {
        print("Start playing")

        loadFloors()
        setSectoring()
        setPositioningMethod()
        loadWalkRecording()

        player?.let { player ->
            object : Thread() {
                override fun run() {
                    player.startPlayback()
                }
            }.start()
        }
    }

    private fun loadFloors() {
        val floorFile = File("/Users/almin/Documents/projects/master/resources/final-nigl/all_floors.tiff")
//		ImageInputStream is = ImageIO.createImageInputStream(viewControllerInterface.getFileFromFileChoose("tiff"));
        val `is` = ImageIO.createImageInputStream(floorFile)
        if (`is` == null || `is`.length() == 0L) {
            // handle error
        }
        val iterator = ImageIO.getImageReaders(`is`!!)
        if (iterator == null || !iterator.hasNext()) {
            throw IOException("Image file format not supported by ImageIO")
        }

        building = Building("FHHGB - Building 2")


        //iterator = null;
        val reader = iterator.next() as ImageReader
        reader.setInput(`is`)
        //viewControllerInterface.createFLoorChoice(reader.getNumImages(true), 0)

        for (i in 0 until reader.getNumImages(true)) {
            val bounds = InitSimulatorData.getFloorBounds(i)
            building?.addFloor(Floor("FH2 Floor", bounds[0], bounds[1], bounds[2], bounds[3], bounds[4], bounds[5], InitSimulatorData.getTargetFloorAccessPoints(i), reader.read(i)))
        }

        building?.let {building ->
            dimensions?.let { dimension ->
                model.generateFloorMap(building.getFloor(4), intArrayOf(dimension.width, dimension.height))
            }
        }
    }

    private fun setSectoring() {
        model.sectoring = VoronoiSectors()
    }

    private fun setPositioningMethod() {
        building?.let {building ->
            model.positioning = FilteredPositioning(building.allAccessPoints)
        }
    }

    private fun loadWalkRecording() {
        try {
            val mapper = ObjectMapper()
            //DataSnapshot dataSnapshot = mapper.readValue(viewControllerInterface.getFileFromFileChoose(), DataSnapshot.class);
            val simulationFile = File("/Users/almin/Documents/projects/master/resources/final-nigl/FH2F4CCL_11202421.03.2016.txt")
            //				List<DataSnapshot> dataSnapshots = mapper.readValue(viewControllerInterface.getFileFromFileChoose("txt"), new TypeReference<List<DataSnapshot>>(){});
            val dataSnapshots: List<DataSnapshot> = mapper.readValue(simulationFile, object : TypeReference<List<DataSnapshot>>() {

            })

            player = Player(dataSnapshots, this, PlayBackEnum.SIMPLE)
//            if (model.getPositioning() == null) {
//                model.setPositioning(FilteredPositioning(building.getAllAccessPoints()))
//            }

        } catch (e1: IOException) {
            e1.printStackTrace()
        }

    }

    override fun nearestAccessPoint(accessPoints: MutableList<AccessPoint>?) {
        accessPoints?.let {
            model.calculateSimplePersonPosition(accessPoints)
        }
    }

    override fun allAccessPoints(accessPointList: MutableList<AccessPoint>?) {
        accessPointList?.let {
            model.addPlayerData(accessPointList)
        }
    }

    override fun wayPointCount(wayPointCount: IntArray?) {
        wayPointCount?.let {
            model.wayPointCount = wayPointCount
        }
    }
}