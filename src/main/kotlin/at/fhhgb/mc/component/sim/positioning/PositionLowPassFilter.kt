package at.fhhgb.mc.component.sim.positioning

import at.fhhgb.mc.component.sim.model.Position

/**
 * A Low Pass-Filter for filtering positions.
 *
 * @property alpha the smoothing value
 * @property previousPos the previously filtered position
 *
 * @constructor Initializes a new Low Pass-Filter for Positions.
 */
class PositionLowPassFilter(private val alpha: Float) : IFilterable<Position> {
    private var previousPos: Position? = null

    /**
     * Filters input data by smoothing the output based on the provided `alpha` value.
     *
     * @param input data that should be filtered
     * @return smoothed data based on `input` and `alpha`
     */
    override fun filter(input: Position?): Position {
        if (input == null) return previousPos ?: Position(0, 0)

        previousPos?.let { previous ->
            val smoothX = Math.round(previous.x + alpha * (input.x - previous.x))
            val smoothY = Math.round(previous.y + alpha * (input.y - previous.y))

            val output = Position(smoothX, smoothY)
            previousPos = output

            return output
        } ?: run {
            previousPos = input
            return input
        }
    }
}
