package at.fhhgb.mc.component.sim.sectorizing;

import at.fhhgb.mc.component.sim.domain.FloorManager;
import at.fhhgb.mc.component.sim.domain.Matrix;
import at.fhhgb.mc.component.sim.model.Position;

import java.awt.*;

public interface ISectorizing {
    public void createSectors(FloorManager floorManager, Graphics g, Matrix m_tMatrix);

    public void addCurrentPosition(Position position);
}
