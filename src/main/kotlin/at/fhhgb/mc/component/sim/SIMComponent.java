package at.fhhgb.mc.component.sim;


import at.fhhgb.mc.component.base.BaseComponent;
import at.fhhgb.mc.component.sim.controller.SimulationController;
import at.fhhgb.mc.component.sim.model.SimulationModel;
import at.fhhgb.mc.component.sim.view.SimulationView;
import at.fhhgb.mc.interfaces.ViewControllerInterface;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class SIMComponent extends BaseComponent implements Observer {

    ViewControllerInterface viewControllerInterface;
    SimulationController simulationController;
    SimulationModel model;

    public SIMComponent() {
        model = new SimulationModel();
        simulationController = new SimulationController(model);
        viewControllerInterface = new SimulationView(simulationController);
        simulationController.setViewControllerInterface(viewControllerInterface);
        model.addObserver(this);
    }

    @Override
    public Panel getView() {
        return viewControllerInterface.getViewPanel();
    }

    @Override
    public String getName() {
        return "SIM";
    }

    @Override
    public String getDescription() {
        return "SimulationComponent";
    }

    @Override
    public void update(Object data) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
