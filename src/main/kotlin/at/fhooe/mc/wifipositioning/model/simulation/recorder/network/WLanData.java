package at.fhooe.mc.wifipositioning.model.simulation.recorder.network;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class WLanData {
    private String SSID;

    private List<AccessPoint> accessPoints;

    public WLanData(String SSID){
        this.SSID = SSID;
        accessPoints = new ArrayList<AccessPoint>();
    }

    public WLanData(){}

    public void addScanResultAsAccessPoint(){

    }

    public String getSSID() {
        return SSID;
    }

    @JsonProperty("ssid")
    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public List<AccessPoint> getAccessPoints() {
        return accessPoints;
    }

    @JsonProperty("accessPoints")
    public void setAccessPoints(List<AccessPoint> accessPoints) {
        this.accessPoints = accessPoints;
    }
}
