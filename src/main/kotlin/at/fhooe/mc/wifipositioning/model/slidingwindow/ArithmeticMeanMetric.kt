package at.fhooe.mc.wifipositioning.model.slidingwindow

/**
 * A concrete implementation of a Metric interface based on the Arithmetic Mean calculation.
 *
 * @property cumulativeValue the cumulative value that is increased over time.
 * @property numberOfOccurrences the number of values that were added to the metric.
 */
class ArithmeticMeanMetric: Metric<Double, Double> {
    private var cumulativeValue = 0.0
    private var numberOfOccurrences = 0.0

    /**
     * Adds [value] to the metric.
     */
    override fun add(value: Double) {
        cumulativeValue += value
        numberOfOccurrences++
    }

    /**
     * Computes the result of the metric.
     *
     * @return the result of the metric.
     */
    override fun compute(): Double {
        return cumulativeValue / numberOfOccurrences
    }
}