package at.fhooe.mc.wifipositioning.model.slidingwindow

class AverageMetric: Metric {
    private var cumulativeSNR = 0.0
    private var numberOfOccurences = 0.0

    override fun add(value: Double) {
        cumulativeSNR += value
        numberOfOccurences++
    }

    override fun compute(): Double {
        return cumulativeSNR / numberOfOccurences
    }
}