package at.fhooe.mc.wifipositioning.model.slidingwindow

class ArithmeticMeanMetric: Metric<Double, Double> {
    private var cumulativeValue = 0.0
    private var numberOfOccurrences = 0.0

    override fun add(value: Double) {
        cumulativeValue += value
        numberOfOccurrences++
    }

    override fun compute(): Double {
        return cumulativeValue / numberOfOccurrences
    }
}