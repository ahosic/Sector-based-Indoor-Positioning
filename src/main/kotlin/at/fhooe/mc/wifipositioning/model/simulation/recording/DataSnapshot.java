package at.fhooe.mc.wifipositioning.model.simulation.recording;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DataSnapshot {
    private List<WLanData> wLanDataList;
    private long timeStamp;
    private int wayPointID;
    private String routeIdentifier;

    public DataSnapshot(List<WLanData> wLanDataList, long timeStamp) {
        this.wLanDataList = wLanDataList;
        this.timeStamp = timeStamp;
    }

    public DataSnapshot(String routeIdentifier, int wayPointID, List<WLanData> wLanDataList, long timeStamp) {
        this.wLanDataList = wLanDataList;
        this.timeStamp = timeStamp;
        this.wayPointID = wayPointID;
        this.routeIdentifier = routeIdentifier;
    }

    public DataSnapshot(){}


    public List<WLanData> getwLanDataList() {
        return wLanDataList;
    }

    @JsonProperty("WLanData")
    public void setwLanDataList(List<WLanData> wLanDataList) {
        this.wLanDataList = wLanDataList;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @JsonProperty("TimeStamp")
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getWayPointID() {
        return wayPointID;
    }

    @JsonProperty("WaypointID")
    public void setWayPointID(int wayPointID) {
        this.wayPointID = wayPointID;
    }

    public String getRouteIdentifier() {
        return routeIdentifier;
    }

    @JsonProperty("RouteIdentifier")
    public void setRouteIdentifier(String routeIdentifier) {
        this.routeIdentifier = routeIdentifier;
    }
}
