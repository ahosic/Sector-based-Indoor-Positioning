package at.fhhgb.mc.controller;

import at.fhhgb.mc.component.base.IComponent;
import at.fhhgb.mc.mediator.IMediator;
import at.fhhgb.mc.mediator.Mediator;
import at.fhhgb.mc.model.MainComponents;
import at.fhhgb.mc.view.MainFrame;

import java.util.List;

public class MainController {

    MainFrame view;
    MainComponents model;

    public MainController(MainFrame view, MainComponents model) {
        this.view = view;
        this.model = model;
        view.setController(this);
    }

    public void start() {
        //view start getting updates from model
        model.addObserver(view);

        //Mediator
        IMediator mediator = new Mediator("config/componentComposition.xml");

        List<IComponent> components = mediator.getComponents();

        components.stream().forEach(c -> model.addComponent(c));
    }

    public void close() {
        System.exit(0);
    }
}
