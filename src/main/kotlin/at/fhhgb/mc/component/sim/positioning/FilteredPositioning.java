package at.fhhgb.mc.component.sim.positioning;

import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;

import java.util.List;

public class FilteredPositioning implements IPositioning {

    private List<AccessPoint> allAccessPoints;
    private IPositioning positioning;
    private IFilterable<Position> filtering;

    public FilteredPositioning(List<AccessPoint> allAccessPoints) {
        this.allAccessPoints = allAccessPoints;
        positioning = new AveragePositioning(allAccessPoints, 5);
        filtering = new PositionLowPassFilter(0.04f);
    }

    @Override
    public Position calculatePosition(List<AccessPoint> accessPointList) {
        // Calculate Position
        Position averagePos = positioning.calculatePosition(accessPointList);

        // Filter Position
        Position filteredPos = filtering.filter(averagePos);

        return filteredPos;
    }
}
