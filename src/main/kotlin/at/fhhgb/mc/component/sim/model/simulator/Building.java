package at.fhhgb.mc.component.sim.model.simulator;

import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;

import java.util.ArrayList;
import java.util.List;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class Building {

    private List<Floor> floorList;
    private String name;


    public Building(String name) {
        this(name, new ArrayList<Floor>());
    }

    public Building(String name, ArrayList<Floor> floorList) {
        this.name = name;
        this.floorList = floorList;
    }

    public void addFloor(Floor floor) {
        floorList.add(floor);
    }

    public Floor getFloor(int index) {
        if (index < floorList.size()) {
            return floorList.get(index);
        }
        throw new IndexOutOfBoundsException(); //TODO: custom exception
    }

    public int getNumberOfFloors() {
        return floorList.size();
    }

    public List<Floor> getAllFloors() {
        return floorList;
    }

    public List<AccessPoint> getAllAccessPoints() {
        ArrayList<AccessPoint> accessPointList = new ArrayList<AccessPoint>();
        for (Floor floor : floorList) {
            accessPointList.addAll(floor.getAccessPointList());
        }
        return accessPointList;
    }


}
