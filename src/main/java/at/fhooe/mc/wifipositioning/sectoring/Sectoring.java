package at.fhooe.mc.wifipositioning.sectoring;

import at.fhooe.mc.wifipositioning.model.Evaluator;
import at.fhooe.mc.wifipositioning.model.building.Position;
import at.fhooe.mc.wifipositioning.model.graphics.FloorManager;
import at.fhooe.mc.wifipositioning.model.graphics.Matrix;

import java.awt.*;
import java.util.List;

public interface Sectoring {
    void createSectors(FloorManager floorManager, Graphics g, Matrix m_tMatrix);
    void addPositionsOfEstimatedSectors(List<Position> positions, List<Position> inTransition);
    void setCurrentActualPosition(Position actualPosition);
    void setEvaluator(Evaluator evaluator);
    void setFloorManager(FloorManager floorManager);
}
