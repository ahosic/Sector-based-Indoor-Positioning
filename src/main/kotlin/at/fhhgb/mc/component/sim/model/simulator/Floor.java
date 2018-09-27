package at.fhhgb.mc.component.sim.model.simulator;

import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.image.BufferedImage;
import java.util.List;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class Floor {
    private String name;

    private FloorBounds floorBounds;

    @JsonIgnore
    private BufferedImage floorImage;

    private List<AccessPoint> accessPointList;

    public Floor(String name, double offsetLeft, double offsetRight, double offsetTop, double offsetBottom, double floorWidth, double floorHeight, List<AccessPoint> accessPointList, BufferedImage floorImage) {
        this.name = name;
        this.floorBounds = new FloorBounds(offsetLeft, offsetRight, offsetTop, offsetBottom, floorWidth, floorHeight);
        this.accessPointList = accessPointList;
        this.floorImage = floorImage;
    }

    public Floor(String name, FloorBounds floorBounds, List<AccessPoint> accessPointList, BufferedImage floorImage) {
        this.name = name;
        this.floorBounds = floorBounds;
        this.accessPointList = accessPointList;
        this.floorImage = floorImage;
    }

    public Floor(String name, BufferedImage floorImage) {
        this.name = name;
        this.floorBounds = floorBounds;
        this.accessPointList = accessPointList;
        this.floorImage = floorImage;
    }

    public String getName() {
        return name;
    }

    public double getOffsetLeft() {
        return floorBounds.getOffsetLeft();
    }

    public double getOffsetRight() {
        return floorBounds.getOffsetRight();
    }

    public double getOffsetTop() {
        return floorBounds.getOffsetTop();
    }

    public double getOffsetBottom() {
        return floorBounds.getOffsetBottom();
    }

    public double getFloorWidth() {
        return floorBounds.getFloorWidth();
    }

    public double getFloorHeight() {
        return floorBounds.getFloorHeight();
    }

    public BufferedImage getFloorImage() {
        return floorImage;
    }

    public void addAccessPoint(AccessPoint accessPoint) {
        accessPointList.add(accessPoint);
    }

    public AccessPoint getAccessPoint(int index) {
        return accessPointList.get(index);
    }

    public List<AccessPoint> getAccessPointList() {
        return accessPointList;
    }

    public void setAccessPointList(List<AccessPoint> accessPointList) {
        this.accessPointList = accessPointList;
    }

    public FloorBounds getFloorBounds() {
        return floorBounds;
    }

    public void setFloorBounds(FloorBounds floorBounds) {
        this.floorBounds = floorBounds;
    }
}
