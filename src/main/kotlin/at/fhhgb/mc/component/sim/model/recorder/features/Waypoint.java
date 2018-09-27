package at.fhhgb.mc.component.sim.model.recorder.features;

import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.utility.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Neidschel on 08/03/16.
 */
public class Waypoint {
    private int id;
    private String description;
    private int floor;
    private long timestamp;
    private Position position;


    public Waypoint(int id, String description, int floor, long timestamp) {
        this.id = id;
        this.description = description;
        this.floor = floor;
        this.timestamp = timestamp;
    }

    public Waypoint(int id, String description, int floor) {
        this(id, description, floor, 0);
    }

    public Waypoint() {
    }

    public int getId() {
        return id;
    }

    @JsonProperty("WaypointID")
    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    @JsonProperty("WaypointDescription")
    public void setDescription(String description) {
        this.description = description;
    }

    public int getFloor() {
        return floor;
    }

    @JsonProperty("Floor")
    public void setFloor(int floor) {
        this.floor = floor;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @JsonProperty("WaypointTimeStamp")
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Waypoint ID: "+id+"\n");
        stringBuffer.append("Description: "+description+"\n");
        stringBuffer.append("Floor: "+floor+"\n");
        stringBuffer.append("Timestamp: "+ DateUtil.getDate(timestamp)+"\n");
        return  stringBuffer.toString();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
