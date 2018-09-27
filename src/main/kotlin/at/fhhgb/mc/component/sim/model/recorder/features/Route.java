package at.fhhgb.mc.component.sim.model.recorder.features;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Neidschel on 08/03/16.
 */
public class Route {

    private String routeIdentifier;
    private List<? extends Waypoint> waypointList;
    private String description;
    private long timestamp;
    private int currentWaypointNumber = -1;

    public Route(String routeIdentifier, List<? extends Waypoint> waypointList, String description, long timestamp) {
        this.routeIdentifier = routeIdentifier;
        this.waypointList = waypointList;
        this.description = description;
        this.timestamp = timestamp;
    }

    public Route() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    @JsonProperty("RouteTimestamp")
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getRouteIdentifier() {
        return routeIdentifier;
    }

    @JsonProperty("RouteIdentifier")
    public void setRouteIdentifier(String routeIdentifier) {
        this.routeIdentifier = routeIdentifier;
    }

    public List<? extends Waypoint> getWaypointList() {
        return waypointList;
    }

    @JsonProperty("WaypointList")
    public void setWaypointList(List<Waypoint> waypointList) {
        this.waypointList = waypointList;
    }

    public String getDescription() {
        return description;
    }

    @JsonProperty("RouteDescription")
    public void setDescription(String description) {
        this.description = description;
    }

    public Waypoint getWayPointById(int id) {
        for (Waypoint waypoint : waypointList) {
            if (id == waypoint.getId()) {
                return waypoint;
            }
        }
        return null;
    }

    public int numberOfRecordedWaypoints(){
        return currentWaypointNumber+1;
    }

    public Waypoint getNextWaypoint(){
       if(currentWaypointNumber < waypointList.size()-1){
           currentWaypointNumber +=1;
           Waypoint waypoint = waypointList.get(currentWaypointNumber);
           return waypoint;
       }
        return null;
    }

    public Waypoint getLastWaypoint(){
        if(currentWaypointNumber > 0){
            currentWaypointNumber -=1;
            Waypoint waypoint = waypointList.get(currentWaypointNumber);
            return waypoint;
        }
        return null;
    }

    public Waypoint getCurrentWaypoint(){
        return waypointList.get(currentWaypointNumber);
    }
}
