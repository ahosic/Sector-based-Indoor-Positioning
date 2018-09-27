package at.fhhgb.mc.component.bui.view.input;

import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.recorder.features.Waypoint;

import java.awt.*;
import java.awt.event.ActionListener;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class WayPointPanel extends Panel {


    private Label idLabel;
    private Label idNumberLabel;
    private Label xPosLabel;
    private TextField xPosTextfield;
    private Label yPosLabel;
    private TextField yPosTextField;
    private Label descriptionLabel;
    private TextArea descriptionArea;
    private Button save;

    public WayPointPanel(ActionListener c) {

        idLabel = new Label("ID");
        idNumberLabel = new Label();
        xPosLabel = new Label("X - Position(m)");
        yPosLabel = new Label("Y - Position(m)");
        descriptionLabel = new Label("Description");


        xPosTextfield = new TextField(1);
        yPosTextField = new TextField(1);
        descriptionArea = new TextArea(3, 30);


        save = new Button("save");


        save.addActionListener(c);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(idLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(idNumberLabel, constraints);
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
        add(descriptionLabel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(descriptionArea, constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(save, constraints);
    }

    public void modifyDataElement(Waypoint waypoint) {
        waypoint.setId(Integer.parseInt(idNumberLabel.getText()));
        waypoint.setDescription(descriptionArea.getText());
        waypoint.setPosition(new Position(Integer.parseInt(xPosLabel.getText()), Integer.parseInt(yPosLabel.getText())));
    }

    public void setWayPointData(Waypoint waypoint) {
        idNumberLabel.setText("" + waypoint.getId());
        xPosTextfield.setText("" + waypoint.getPosition().getX());
        yPosTextField.setText("" + waypoint.getPosition().getY());
        descriptionArea.setText(waypoint.getDescription());
    }


}
