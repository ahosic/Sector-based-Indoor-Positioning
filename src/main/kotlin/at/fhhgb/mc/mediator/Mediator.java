package at.fhhgb.mc.mediator;


import at.fhhgb.mc.component.base.IComponent;
import at.fhhgb.mc.config.ComponentFactory;

import java.util.List;

public class Mediator implements IMediator {

    List<IComponent> components;

    public Mediator(String file) {
        ComponentFactory componentFactory = new ComponentFactory(file);
        components = componentFactory.getComponents();
        for (IComponent component : components) {
            component.setMediator(this);
        }
    }

    @Override
    public List<IComponent> getComponents() {
        return components;
    }
}
