package at.fhhgb.mc.component.bui.controller.input;

import at.fhhgb.mc.component.bui.view.input.AccessPointPanel;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class AccessPointController implements ActionListener {

    private static final AccessPointController instance = new AccessPointController();
    private AccessPointPanel accessPointPanel;
    private AccessPoint accessPoint;

    private AccessPointController() {
        accessPointPanel = new AccessPointPanel(this);
        accessPoint = new AccessPoint();
    }

    public static AccessPointController getInstance() {
        return instance;
    }

    public void changeActiveAccessPoint(AccessPoint accessPoint) {
        this.accessPoint = accessPoint;
        accessPointPanel.setAccessPointData(accessPoint);
    }

    public Panel getAccessPointPanel() {
        return accessPointPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        accessPointPanel.modifyDataElement(accessPoint);
    }
}
