package at.fhooe.mc.wifipositioning.model.simulation.recording;


import at.fhooe.mc.wifipositioning.model.simulation.Position;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScannedAccessPoint implements Comparable<ScannedAccessPoint> {
    private String bssid;
    private String ssid;
    private int signalLevel;
    private int frequency;
    private String capabilities;
    private long timeStamp;
//    private Position position;
//    private int floor;

    public ScannedAccessPoint(String bssid, String ssid, int signalLevel, int frequency, String capabilities, long timeStamp, int floor) {
        this.bssid = bssid;
        this.ssid = ssid;
        this.signalLevel = signalLevel;
        this.frequency = frequency;
        this.capabilities = capabilities;
        this.timeStamp = timeStamp;
//        this.floor = floor;
    }

    public ScannedAccessPoint(){}

    public ScannedAccessPoint(String bssid, Position position, int floor) {
        this.bssid = bssid;
//        this.position = position;
//        this.floor = floor;
    }

    public ScannedAccessPoint(ScannedAccessPoint ap) {
        this.bssid = ap.bssid;
        this.ssid = ap.ssid;
        this.signalLevel = ap.signalLevel;
        this.frequency = ap.frequency;
        this.capabilities = ap.capabilities;
        this.timeStamp = ap.timeStamp;
//        this.position = new Position(ap.position.getX(), ap.position.getY());
//        this.floor = ap.floor;
    }


    public String getBssid() {
        return bssid;
    }

    @JsonProperty("bssid")
    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getSsid() {
        return ssid;
    }

    @JsonProperty("ssid")
    public void setSsid(String ssid) {
        this.ssid = ssid;
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

    public String getCapabilities() {
        return capabilities;
    }

    @JsonProperty("capabilities")
    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
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
        return bssid;
    }

//    public Position getPosition() {
//        return position;
//    }
//
//    public void setPosition(Position position) {
//        this.position = position;
//    }
//
//    public int getFloor() {
//        return floor;
//    }
//
//    public void setFloor(int floor) {
//        this.floor = floor;
//    }

    @Override
    public int compareTo(ScannedAccessPoint o) {
        return Integer.compare(signalLevel,o.signalLevel);
    }
}
