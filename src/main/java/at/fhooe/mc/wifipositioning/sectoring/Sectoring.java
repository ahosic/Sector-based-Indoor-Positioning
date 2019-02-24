package at.fhooe.mc.wifipositioning.sectoring;

import at.fhooe.mc.wifipositioning.model.building.Position;
import at.fhooe.mc.wifipositioning.model.graphics.FloorManager;
import at.fhooe.mc.wifipositioning.model.graphics.Matrix;

import java.awt.*;

public interface Sectoring {
    void createSectors(FloorManager floorManager, Graphics g, Matrix m_tMatrix);
    void addCurrentPosition(Position position);
}
