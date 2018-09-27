package at.fhhgb.mc.component.sim.model;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class PlayerResult {
    private final int signalLevel;
    private final String bssid;

    public PlayerResult(int signalLevel, String bssid) {
        this.signalLevel = signalLevel;
        this.bssid = bssid;
    }

    public int getSignalLevel() {
        return signalLevel;
    }

    public String getBssid() {
        return bssid;
    }
}
