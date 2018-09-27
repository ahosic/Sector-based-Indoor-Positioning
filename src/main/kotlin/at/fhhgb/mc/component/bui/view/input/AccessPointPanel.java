package at.fhhgb.mc.component.bui.view.input;

import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;

import java.awt.*;
import java.awt.event.ActionListener;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class AccessPointPanel extends Panel {


    private Label bssidLabel;
    private TextField bssidTextField;
    private Label xPosLabel;
    private TextField xPosTextfield;
    private Label yPosLabel;
    private TextField yPosTextField;
    private Button save;

    public AccessPointPanel(ActionListener c) {

        bssidLabel = new Label("BSSID");
        xPosLabel = new Label("X - Position(m)");
        yPosLabel = new Label("Y - Position(m)");


        xPosTextfield = new TextField();
        yPosTextField = new TextField();
        bssidTextField = new TextField(17);


        save = new Button("save");


        save.addActionListener(c);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(bssidLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(bssidTextField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(xPosLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(xPosTextfield, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(yPosLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(yPosTextField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(save, constraints);
    }

    public void modifyDataElement(AccessPoint accessPoint) {
        accessPoint.setBSSID(bssidTextField.getText());
        accessPoint.setPosition(new Position(Integer.parseInt(xPosLabel.getText()), Integer.parseInt(yPosLabel.getText())));

    }

    public void setAccessPointData(AccessPoint accessPoint) {
        bssidTextField.setText(accessPoint.getBSSID());
        xPosTextfield.setText("" + accessPoint.getPosition().getX());
        yPosTextField.setText("" + accessPoint.getPosition().getY());
    }

}
