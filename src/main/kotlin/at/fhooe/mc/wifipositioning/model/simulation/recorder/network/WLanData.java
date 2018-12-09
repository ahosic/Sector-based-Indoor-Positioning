package at.fhooe.mc.wifipositioning.model.simulation.recorder.network;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class WLanData {
    private String SSID;

    private List<ScannedAccessPoint> scannedAccessPoints;

    public WLanData(String SSID){
        this.SSID = SSID;
        scannedAccessPoints = new ArrayList<ScannedAccessPoint>();
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

    public List<ScannedAccessPoint> getScannedAccessPoints() {
        return scannedAccessPoints;
    }

    @JsonProperty("accessPoints")
    public void setScannedAccessPoints(List<ScannedAccessPoint> scannedAccessPoints) {
        this.scannedAccessPoints = scannedAccessPoints;
    }
}
