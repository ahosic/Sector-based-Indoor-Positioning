package at.fhooe.mc.wifipositioning.model.slidingwindow

class MedianMetric: Metric {

    private var values: MutableList<Double> = mutableListOf()

    override fun add(value: Double) {
        values.add(value)
    }

    override fun compute(): Double {
        if (values.isEmpty()) return 0.0

        values.sort()

        var result = 0.0
        if (values.size % 2 == 0) {
            val idx = (values.size / 2) - 1
            result = 0.5 * (values[idx] + values[idx + 1])
        } else {
            val idx = ((values.size + 1) / 2) - 1
            result = values[idx]
        }

        return result
    }
}