package at.fhhgb.mc.component.sim.model;

import at.fhhgb.mc.component.base.BaseModel;
import at.fhhgb.mc.component.sim.domain.FloorManager;
import at.fhhgb.mc.component.sim.domain.PixelPosition;
import at.fhhgb.mc.component.sim.model.initialisations.WaypointPosition;
import at.fhhgb.mc.component.sim.model.initialisations.WaypointRoute;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;
import at.fhhgb.mc.component.sim.model.simulator.Floor;
import at.fhhgb.mc.component.sim.positioning.IPositioning;
import at.fhhgb.mc.component.sim.sectorizing.ISectorizing;
import at.fhhgb.mc.component.sim.view.DrawingContext;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * The Model of the MVC pattern. The new positions and polygons are drawn here.
 * 
 * @author Michael Nigl
 */
public class SimulationModel extends BaseModel {

	List<AccessPoint> accessPoints = new ArrayList<>();
	Person person = new Person(-1000,1000);
    Person actualPosition = new Person(-1000,1000);
	WaypointRoute waypointRoute;
	private int m_mode = 4;
	private int xGrid = 1;
    private int yGrid = 1;
    private int[] wayPointCount;
    private int wayPointNumber = 1;
    private int interpolationStep = 0;
	private ISectorizing sectorizing;

	private IPositioning positioning;


	/**
	 * Empty default constructor
	 */
	public SimulationModel() {
	}

	public void generateFloorMap(Floor floor, int[] dimensions) {
		setPanelSize(dimensions[0], dimensions[1]);
		accessPoints = floor.getAccessPointList();
		if (floorManager == null) {
			floorManager = new FloorManager(floor);
		} else {
			floorManager.changeFloor(floor);
		}
		zoomToFit(true);
	}

    private void checkForinitializedWaypoints(){
        if(waypointRoute == null){
            waypointRoute = new WaypointRoute();
        }
        Position pos = null;
        if(wayPointNumber < wayPointCount.length){
            pos = interpolate(waypointRoute.getWayPointPositionAtIndex(wayPointNumber-1).getWayPointPosition(),waypointRoute.getWayPointPositionAtIndex
                    (wayPointNumber).getWayPointPosition(),wayPointCount[wayPointNumber-1],interpolationStep);
        }else{
            pos = interpolate(waypointRoute.getWayPointPositionAtIndex(wayPointNumber-1).getWayPointPosition(),waypointRoute.getWayPointPositionAtIndex
                    (0).getWayPointPosition(),wayPointCount[wayPointNumber-1],interpolationStep);
        }

        actualPosition.setX(pos.getX());
        actualPosition.setY(pos.getY());

        interpolationStep+=1;
        if(interpolationStep == wayPointCount[wayPointNumber-1]){
            wayPointNumber+=1;
            interpolationStep = 0;
        }
    }


    public void setWayPointCount(int[] wayPointCount){
        this.wayPointCount = wayPointCount;
    }

    public void calculateSimplePersonPosition(List<AccessPoint> accessPoints){
        checkForinitializedWaypoints();
        for(AccessPoint accessPoint : accessPoints){
            for(AccessPoint ap : this.accessPoints){
                if(ap.getBSSID().toLowerCase().equals(accessPoint.getBSSID().toLowerCase())){
                    person.setX(ap.getPosition().getX());
                    person.setY(ap.getPosition().getY());
                    callObserver(createBufferedImage());
                    return;
                }
            }
        }

		callObserver(createBufferedImage());
	}

    public void calculateWeightedPersonPosition(List<AccessPoint> accessPointList){
        checkForinitializedWaypoints();
        int offset = 12;
        List<AccessPoint> matchingAccesspoints = new ArrayList<>();
        for(AccessPoint accessPoint : accessPointList){
            for(AccessPoint ap : accessPoints){
                if(ap.getBSSID().toLowerCase().equals(accessPoint.getBSSID().toLowerCase())){
                    ap.setSignalLevel(accessPoint.getSignalLevel());
                    matchingAccesspoints.add(ap);
                }
            }
        }

        double weightsum = 0;
        double positionSumX = 0;
        double positionSumY = 0;

        for(AccessPoint accessPoint : matchingAccesspoints){

            positionSumX += (accessPoint.getPosition().getX() * (accessPoint.getSignalLevel()*-1));
            positionSumY += (accessPoint.getPosition().getY() * (accessPoint.getSignalLevel()*-1));

            weightsum += (accessPoint.getSignalLevel()*-1);
        }

        person.setX((int)(positionSumX/weightsum));
        person.setY((int)(positionSumY/weightsum));
		callObserver(createBufferedImage());
    }


    public Position interpolate(Position start, Position end, int steps, int iterationStep) {
        if (steps < 2) {
			return new Position(person.getX(),person.getY());
            //throw new IllegalArgumentException("interpolate: illegal count!");
        }

        int xPos = (int)Math.round(start.getX() + iterationStep * (end.getX() - start.getX()) / steps);
        int yPos = (int)Math.round(start.getY() + iterationStep * (end.getY() - start.getY()) / steps);


        return new Position(xPos,yPos);
    }
	
	/**
	 * Calculates for a point on the screen the values it has in the world coordinate system.
	 * 
	 * @param _pt - the point on the screen
	 * @return - the point in the world coordinate system
	 */
	public Point getMapPoint(Point _pt){
		Point search = new Point(_pt.x,_pt.y);
		search = m_tMatrix.invers().multiply(search);
		return search;
	}


	public void changeXGrid(int gridWitdh){
		this.xGrid = gridWitdh;
		callObserver(createBufferedImage());
	}

	public void changeYGrid(int gridHeight){
		this.yGrid = gridHeight;
		callObserver(createBufferedImage());
	}

	public void setSectorizing(ISectorizing sectorizing) {
		this.sectorizing = sectorizing;
		callObserver(createBufferedImage());
	}

	public IPositioning getPositioning() {
		return positioning;
	}

	public void setPositioning(IPositioning positioning) {
		this.positioning = positioning;
	}

	public void addPlayerData(List<AccessPoint> accessPointList) {
		checkForinitializedWaypoints();
		Position p = positioning.calculatePosition(accessPointList);
		if (sectorizing == null || p == null) {
			callObserver(createBufferedImage());
			return;
		}
		PixelPosition newPos = floorManager.calculatePixelPositionFromMeter(p.getX(), p.getY());
		sectorizing.addCurrentPosition(new Position(Math.round(newPos.getXPosition()), Math.round(newPos.getYPosition())));
		callObserver(createBufferedImage());
	}

	/**
	 * Creates a buffered image of all the current GeoObjects. It is used because it is faster
	 * than the usage of single objects.
	 *
	 * @return the created buffered image.
	 */
	public BufferedImage createBufferedImage(){
		BufferedImage images = applyMatrixToBufferedImage(floorManager.getFloor().getFloorImage());
		Graphics g = images.createGraphics();
		Rectangle clipRect = new Rectangle(floorManager.getOffsetLeftInPixel() - 10, floorManager.getOffsetTopInPixel() - 10, floorManager.getOffsetRightInPixel() - floorManager.getOffsetLeftInPixel() + 15, floorManager.getOffsetBottomInPixel() - floorManager.getOffsetTopInPixel() + 15);
		g.setClip(new Rectangle(m_tMatrix.multiply(clipRect)));
		g.setColor(new Color(0, 0, 0));

		g.drawOval(0, 0, 10, 10);
		int i = 0;

		i = 0;
		while(i < accessPoints.size()) {
			PixelPosition pos = floorManager.calculatePixelPositionFromMeter(accessPoints.get(i).getPosition().getX(), accessPoints.get(i).getPosition().getY());
			DrawingContext.drawAccessPoint(pos.getXPosition(), pos.getYPosition(), g, m_tMatrix);
			i++;
		}

		if(waypointRoute != null) {
			i = 0;
			List<WaypointPosition> wayPointPositions = waypointRoute.getWaypointPositionList();
			List<WaypointPosition> wayPointPositions2 = new ArrayList<>();

			while (i < wayPointPositions.size()) {
				WaypointPosition waypointPosition = wayPointPositions.get(i);
				PixelPosition pos = floorManager.calculatePixelPositionFromMeter(waypointPosition.getWayPointPosition().getX(), waypointPosition.getWayPointPosition().getY());
				WaypointPosition waypointPosition1 = new WaypointPosition(waypointPosition.getWayPointId(),
						new Position(pos.getXPosition(), pos.getYPosition()));
				wayPointPositions2.add(waypointPosition1);

				DrawingContext.drawWayPointPosition(waypointPosition1, g, m_tMatrix);
				i++;
			}
			DrawingContext.drawWayPointLine((ArrayList<WaypointPosition>) wayPointPositions2, g, m_tMatrix);
		}

		if (sectorizing != null) {
			sectorizing.createSectors(floorManager, g, m_tMatrix);
		}

		PixelPosition pos = floorManager.calculatePixelPositionFromMeter(person.getX(), person.getY());
		person.setX(pos.getXPosition());
		person.setY(pos.getYPosition());
		DrawingContext.drawPerson(person, g, m_tMatrix);

		pos = floorManager.calculatePixelPositionFromMeter(actualPosition.getX(), actualPosition.getY());
		actualPosition.setX(pos.getXPosition());
		actualPosition.setY(pos.getYPosition());
		DrawingContext.drawActualPosition(actualPosition,g,m_tMatrix);


		return images;
	}




}
