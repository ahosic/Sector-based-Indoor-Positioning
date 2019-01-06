package at.fhooe.mc.wifipositioning.positioning;

import at.fhooe.mc.wifipositioning.model.simulation.recording.ScannedAccessPoint;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A sliding window for access points.
 */
public class AccessPointSlidingWindow extends BaseSlidingWindow<ScannedAccessPoint> {

    private Map<String, SNRDataAverage> averageMap;

    public AccessPointSlidingWindow(int windowSize) {
        super(windowSize);
    }

    /**
     * Gets the BSSID of the access point that has the highest average signal strength at a specific point in time.
     * @return the BSSID of the access point
     */
    public String getBestAverageBSSID() {
        averageMap = new HashMap<>();
        List<ScannedAccessPoint> allScannedAccessPoints = arrayLists.stream().flatMap(List::stream).collect(Collectors.toList());

        // Generate Average Signal Levels per BSSID and store into Map
        for (ScannedAccessPoint scannedAccessPoint : allScannedAccessPoints) {
            SNRDataAverage average = averageMap.get(scannedAccessPoint.getBssid());
            if (average != null) {
                average.addSNRValues(scannedAccessPoint.getSignalLevel());
            } else {
                averageMap.put(scannedAccessPoint.getBssid(), new SNRDataAverage(scannedAccessPoint.getSignalLevel()));
            }
        }

        // Find BSSID with best average signal level
        Optional<Map.Entry<String, SNRDataAverage>> averageEntry = averageMap.entrySet().stream()
                .max(Comparator.comparingDouble(entry -> entry.getValue().getAverage()));

        if (averageEntry.isPresent()) return averageEntry.get().getKey();

        return "";
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
