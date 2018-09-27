package at.fhhgb.mc.component.bui.controller.input;

import at.fhhgb.mc.component.bui.view.input.BoundsPanel;
import at.fhhgb.mc.component.sim.model.simulator.FloorBounds;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class BoundsController implements ActionListener {
    private static final BoundsController instance = new BoundsController();
    private BoundsPanel boundsPanel;
    private FloorBounds floorBounds;

    private BoundsController() {
        boundsPanel = new BoundsPanel(this);
        floorBounds = new FloorBounds();
    }

    public static BoundsController getInstance() {
        return instance;
    }

    public void changeFloorBounds(FloorBounds floorBounds) {
        this.floorBounds = floorBounds;
        boundsPanel.setFloorBoundsData(floorBounds);
    }

    public Panel getBoundsPanel() {
        return boundsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boundsPanel.modifyDataElement(floorBounds);
    }
}
