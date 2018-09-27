package at.fhhgb.mc.model;


import at.fhhgb.mc.component.base.IComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MainComponents extends Observable {
    private List<IComponent> components = new ArrayList<>();

    public void addComponent(IComponent component) {
        if (component != null) {
            components.add(component);
            updateComponent(component);
        }
    }

    public void removeComponent(IComponent component) {
        if (components.contains(component)) {
            components.remove(component);
            updateComponent(component);
        }
    }

    public List<IComponent> getComponents() {
        return components;
    }

    private void updateComponent(IComponent component) {
        setChanged();
        notifyObservers(component);
    }
}
