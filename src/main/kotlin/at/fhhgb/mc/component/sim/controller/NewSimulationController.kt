package at.fhhgb.mc.component.sim.controller

import at.fhhgb.mc.component.sim.model.SimulationModel
import at.fhhgb.mc.component.sim.model.initialisations.InitSimulatorData
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint
import at.fhhgb.mc.component.sim.model.recorder.network.DataSnapshot
import at.fhhgb.mc.component.sim.model.simulator.Building
import at.fhhgb.mc.component.sim.model.simulator.Floor
import at.fhhgb.mc.component.sim.positioning.FilteredPositioning
import at.fhhgb.mc.component.sim.sectorizing.VoronoiSectors
import at.fhhgb.mc.component.utility.Player
import at.fhhgb.mc.interfaces.PlayBackEnum
import at.fhhgb.mc.interfaces.PlaybackCallbackInterface
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import javafx.event.ActionEvent
import java.awt.Dimension
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.imageio.ImageReader

class NewSimulationController(val model: SimulationModel) : PlaybackCallbackInterface {

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
        model.setSectorizing(VoronoiSectors())
    }

    private fun setPositioningMethod() {
        building?.let {building ->
            model.setPositioning(FilteredPositioning(building.getAllAccessPoints()))
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
        model.setWayPointCount(wayPointCount)
    }
}