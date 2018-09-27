package at.fhhgb.mc.component.bui;

import at.fhhgb.mc.component.base.BaseComponent;
import at.fhhgb.mc.component.bui.controller.BUIController;
import at.fhhgb.mc.component.bui.model.BUIModel;
import at.fhhgb.mc.component.bui.view.BUIView;
import at.fhhgb.mc.mediator.IMediator;

import java.awt.*;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class BUIComponent extends BaseComponent {
    BUIView view;
    BUIController controller;
    BUIModel buiModel;

    public BUIComponent() {
        buiModel = new BUIModel();
        controller = new BUIController(buiModel);
        view = new BUIView(controller);
        controller.setViewControllerInterface(view);
        buiModel.addObserver(view);
    }

    @Override
    public void setMediator(IMediator mediator) {
        super.setMediator(mediator);
    }

    @Override
    public Panel getView() {
        return view;
    }

    @Override
    public String getName() {
        return "BUI";
    }

    @Override
    public String getDescription() {
        return "BuildingComponent";
    }

    @Override
    public void update(Object data) {

    }
}
