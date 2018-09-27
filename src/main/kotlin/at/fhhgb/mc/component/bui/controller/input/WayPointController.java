package at.fhhgb.mc.component.bui.controller.input;

import at.fhhgb.mc.component.bui.view.input.WayPointPanel;
import at.fhhgb.mc.component.sim.model.recorder.features.Waypoint;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class WayPointController implements ActionListener {

    private static final WayPointController instance = new WayPointController();
    private WayPointPanel wayPointPanel;
    private Waypoint waypoint;

    private WayPointController() {
        wayPointPanel = new WayPointPanel(this);
        waypoint = new Waypoint();
    }

    public static WayPointController getInstance() {
        return instance;
    }

    public void changeActiveWayPoint(Waypoint waypoint) {
        this.waypoint = waypoint;
        wayPointPanel.setWayPointData(waypoint);
    }

    public Panel getWayPointPanel() {
        return wayPointPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        wayPointPanel.modifyDataElement(waypoint);
    }
}
