package at.fhooe.mc.wifipositioning.model

import at.fhooe.mc.wifipositioning.model.building.Position
import java.io.FileWriter
import java.io.IOException
import java.lang.Exception

/**
 * The Evaluator performs the evaluation of the performance of the system.
 *
 * @property totalEstimations Number of estimations made
 * @property matchedStableEstimations Number of positive estimations made with stable sectors
 * @property matchedInTransitioningEstimations Number of positive estimations made with in Transitioning sectors
 * @property distances A list of all error distances calculated
 * @property distanceFileName Name of the file, where the error distances should be saved (CSV-Format)
 */
class Evaluator {
    private var totalEstimations = 0
    private var matchedStableEstimations = 0
    private var matchedInTransitioningEstimations = 0
    var distances: MutableList<Double> = mutableListOf()
    var distanceFileName: String = "defaultEvaluation.csv"

    /**
     * Increments number of estimations.
     */
    fun incrementTotalEstimations() {
        totalEstimations++
    }

    /**
     * Increments number of positive estimations made with stable sectors.
     */
    fun incrementMatchedStableEstimations() {
        matchedStableEstimations++
    }

    /**
     * Increments number of positive estimations made with in Transitioning sectors.
     */
    fun incrementMatchedInTransitioningEstimations() {
        matchedInTransitioningEstimations++
    }

    /**
     * Resets all counters.
     */
    fun reset() {
        totalEstimations = 0
        matchedStableEstimations = 0
        matchedInTransitioningEstimations = 0
    }

    /**
     * Prints the summary of the evaluation.
     */
    fun printSummary() {
        println("*** Evaluation Summary ***")

        println("Total Estimations: $totalEstimations")
        println("Matched Stable Estimations: $matchedStableEstimations")
        println("Matched In Transitioning Estimations: $matchedInTransitioningEstimations")
        println("Min euclidean distance in meters: ${distances.min()}")
        println("Max euclidean distance in meters: ${distances.max()}")
        println("Average euclidean distance in meters: ${distances.average()}")

        println("*** End of Summary ***")
    }

    /**
     * Saves the list of error distances as a CSV file.
     */
    fun saveDistancesToFile() {
        println("Start writing evaluation errors to file.")

        var fileWriter = FileWriter(distanceFileName)
        try {
            for (dist in distances) {
                fileWriter.appendln(dist.toString())
            }

            println("Finished writing evaluation errors to file.")
        } catch (e: Exception) {
            println("Error while writing evaluation errors to file.")
            e.printStackTrace()
        } finally {
            try {
                fileWriter.flush()
                fileWriter.close()
            } catch (e: IOException) {
                println("Flushing/closing error!")
                e.printStackTrace()
            }
        }
    }

    companion object {
        /**
         * Calculates the Euclidean distance between two points.
         */
        fun euclideanDistanceBetween(p1: Position, p2: Position): Double {
            return Math.sqrt(Math.pow(p1.x.toDouble() - p2.x.toDouble(), 2.0) + Math.pow(p1.y.toDouble() - p2.y.toDouble(), 2.0))
        }
    }
}