package at.fhhgb.mc.component.sim.model.recorder.network;


import at.fhhgb.mc.component.sim.domain.Matrix;
import at.fhhgb.mc.component.sim.model.Position;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;

/**
 * Created by Neidschel on 03/03/16.
 */
public class AccessPoint implements Comparable<AccessPoint> {


    private String BSSID;
    private String SSID;
    private int signalLevel;
    private int frequency;
    private String capabilites;
    private long timeStamp;
    private Position position;
    private int floor;

    public AccessPoint(String BSSID, String SSID, int signalLevel, int frequency, String capabilites, long timeStamp, int floor) {
        this.BSSID = BSSID;
        this.SSID = SSID;
        this.signalLevel = signalLevel;
        this.frequency = frequency;
        this.capabilites = capabilites;
        this.timeStamp = timeStamp;
        this.floor = floor;
    }

    public AccessPoint(){}

    public AccessPoint(String BSSID, Position position, int floor) {
        this.BSSID = BSSID;
        this.position = position;
        this.floor = floor;
    }

    public AccessPoint(AccessPoint ap) {
        this.BSSID = ap.BSSID;
        this.SSID = ap.SSID;
        this.signalLevel = ap.signalLevel;
        this.frequency = ap.frequency;
        this.capabilites = ap.capabilites;
        this.timeStamp = ap.timeStamp;
        this.position = new Position(ap.position.getX(), ap.position.getY());
        this.floor = ap.floor;
    }


    public String getBSSID() {
        return BSSID;
    }

    @JsonProperty("bssid")
    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String getSSID() {
        return SSID;
    }

    @JsonProperty("ssid")
    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public int getSignalLevel() {
        return signalLevel;
    }

    @JsonProperty("signalLevel")
    public void setSignalLevel(int signalLevel) {
        this.signalLevel = signalLevel;
    }

    public int getFrequency() {
        return frequency;
    }

    @JsonProperty("frequency")
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getCapabilites() {
        return capabilites;
    }

    @JsonProperty("capabilities")
    public void setCapabilites(String capabilites) {
        this.capabilites = capabilites;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @JsonProperty("timeStamp")
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString(){
        return BSSID;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    /**
     * Paints a graphic representation of this Accesspoint.
     *
     * @param _g - the graphics context
     * @param _matrix - the transformation matrix for the position and size values.
     */
    public void paint(Graphics _g, Matrix _matrix){
        _g.setColor(new Color(0, 0, 255));
        _g.fillOval(position.getX()-5,position.getY()-5,10,10);


    }

    @Override
    public int compareTo(AccessPoint o) {
        return Integer.compare(signalLevel,o.signalLevel);
    }
}
