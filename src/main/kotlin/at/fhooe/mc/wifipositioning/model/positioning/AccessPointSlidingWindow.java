package at.fhooe.mc.wifipositioning.model.positioning;

import at.fhooe.mc.wifipositioning.model.building.InstalledAccessPoint;
import at.fhooe.mc.wifipositioning.model.recording.ScannedAccessPoint;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A sliding window for access points.
 */
public class AccessPointSlidingWindow extends BaseSlidingWindow<ScannedAccessPoint> {

    private List<String> allowedBSSIDs;
    private AccessPointIdentificationMode mode;

    public AccessPointSlidingWindow(int windowSize, AccessPointIdentificationMode mode) {
        super(windowSize);
        this.mode = mode;
    }

    public AccessPointSlidingWindow(int windowSize) {
        super(windowSize);
        mode = AccessPointIdentificationMode.SIX_BYTE_IDENTIFICATION;
    }

    /**
     * Gets the BSSID of the access point that has the highest average signal strength at a specific point in time.
     * @return the BSSID of the access point
     */
    public String getBestAverageBSSID() {
        Map<String, SNRDataAverage> averages = computeAverages();

        // Find BSSID with best average signal level
        Optional<Map.Entry<String, SNRDataAverage>> averageEntry = averages.entrySet().stream()
                .max(Comparator.comparingDouble(entry -> entry.getValue().getAverage()));

        if (!averageEntry.isPresent()) return "";

        return averageEntry.get().getKey().toLowerCase();
    }

    public String getBestAverageBSSID(List<InstalledAccessPoint> allowedAccessPoints) {
        Map<String, SNRDataAverage> averages = computeAverages();
        setAllowedBSSIDs(allowedAccessPoints);

        // Find BSSID with best average signal level
        Optional<Map.Entry<String, SNRDataAverage>> averageEntry = averages.entrySet()
                .stream()
                .filter(entry -> isValidBSSID(entry.getKey().toLowerCase()))
                .max(Comparator.comparingDouble(entry -> entry.getValue().getAverage()));

        if (!averageEntry.isPresent()) return "";

        return averageEntry.get().getKey();
    }

    private Map<String, SNRDataAverage> computeAverages() {
        Map<String, SNRDataAverage> averages = new HashMap<>();
        List<ScannedAccessPoint> allScannedAccessPoints = arrayLists.stream().flatMap(List::stream).collect(Collectors.toList());

        // Generate Average Signal Levels per BSSID and store into Map
        for (ScannedAccessPoint scannedAccessPoint : allScannedAccessPoints) {
            SNRDataAverage average = averages.get(scannedAccessPoint.getBssid());
            if (average != null) {
                average.addSNRValues(scannedAccessPoint.getSignalLevel());
            } else {
                averages.put(scannedAccessPoint.getBssid(), new SNRDataAverage(scannedAccessPoint.getSignalLevel()));
            }
        }

        return averages;
    }

    private boolean isValidBSSID(String bssid) {
        return allowedBSSIDs.stream().anyMatch(bssid::startsWith);
    }

    private void setAllowedBSSIDs(List<InstalledAccessPoint> allowedAccessPoints) {
        switch (mode) {
            case SIX_BYTE_IDENTIFICATION:
                allowedBSSIDs = allowedAccessPoints
                        .stream()
                        .map(ap -> ap.getBssid().toLowerCase())
                        .collect(Collectors.toList());
                break;
            case FIVE_BYTE_IDENTIFICATION:
                allowedBSSIDs = allowedAccessPoints
                        .stream()
                        .map(ap -> ap.getFiveBytePrefix().toLowerCase())
                        .collect(Collectors.toList());
                break;
        }
    }

    /**
     * An object for storing signal strength and calculating the average signal strength.
     */
    private class SNRDataAverage {
        double cumulativeSNR = 0;
        double numberOfOccurences = 0;

        public SNRDataAverage(double snr) {
            addSNRValues(snr);
        }

        /**
         * Adds a signal strength value to the object.
         *
         * @param snr the signal strength in dBm.
         */
        public void addSNRValues(double snr) {
            cumulativeSNR += snr;
            numberOfOccurences++;
        }

        /**
         * Gets the average signal strength in dBm.
         *
         * @return the average signal strength in dBm.
         */
        public double getAverage() {
            return cumulativeSNR / numberOfOccurences;
        }
    }
}
