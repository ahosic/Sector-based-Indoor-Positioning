package at.fhhgb.mc.component.sim.positioning;

import at.fhhgb.mc.component.sim.model.Position;

public class PositionLowPassFilter implements IFilterable<Position> {

    /**
     * Smoothing value 0 <= alpha <= 1 (smaller value means more smoothing)
     */
    private float alpha;
    private Position previousPos;

    PositionLowPassFilter(float alpha) {
        this.alpha = alpha;
    }

    /**
     * Filters input data by smoothing the output based on the provided <code>alpha</code> value.
     *
     * @param input data that should be filtered
     * @return smoothed data based on <code>input</code> and <code>alpha</code>
     */
    @Override
    public Position filter(Position input) {
        if (input == null) return previousPos;

        if (previousPos == null) {
            previousPos = input;
            return input;
        }

        int smoothX = Math.round(previousPos.getX() + alpha * (input.getX() - previousPos.getX()));
        int smoothY = Math.round(previousPos.getY() + alpha * (input.getY() - previousPos.getY()));

        Position output = new Position(smoothX, smoothY);
        previousPos = output;

        return output;
    }
}
