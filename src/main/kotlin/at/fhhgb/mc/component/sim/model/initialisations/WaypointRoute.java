package at.fhhgb.mc.component.sim.model.initialisations;

import at.fhhgb.mc.component.sim.model.Position;

import java.util.ArrayList;
import java.util.List;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class WaypointRoute {

    List<WaypointPosition> waypointPositionList;

    public WaypointRoute(){
        initWayPointPositions();
    }

    private void initWayPointPositions(){
        waypointPositionList = new ArrayList<>();

        WaypointPosition waypointPosition = new WaypointPosition(1, new Position(79, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(2, new Position(74, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(3, new Position(71, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(4, new Position(65, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(5, new Position(62, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(6, new Position(54, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(7, new Position(52, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(8, new Position(50, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(9, new Position(48, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(10, new Position(36, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(11, new Position(33, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(12, new Position(31, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(13, new Position(28, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(14, new Position(31, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(15, new Position(11, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(16, new Position(9, 11));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(17, new Position(9, 12));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(18, new Position(9, 15));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(19, new Position(13, 15));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(20, new Position(13, 17));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(21, new Position(13, 25));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(22, new Position(13, 40));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(23, new Position(9, 40));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(24, new Position(9, 47));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(25, new Position(12, 47));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(26, new Position(21, 47));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(27, new Position(23, 47));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(28, new Position(26, 47));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(29, new Position(31, 47));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(30, new Position(39, 47));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(31, new Position(48, 46));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(32, new Position(58, 45));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(33, new Position(64, 44));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(34, new Position(70, 43));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(35, new Position(71, 41));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(36, new Position(71, 35));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(37, new Position(71, 19));
        waypointPositionList.add(waypointPosition);
        waypointPosition = new WaypointPosition(38, new Position(71, 14));
        waypointPositionList.add(waypointPosition);
    }

    public List<WaypointPosition> getWaypointPositionList() {
        return waypointPositionList;
    }

    public WaypointPosition getWayPointPositionAtIndex(int index){
        return waypointPositionList.get(index);
    }
}
