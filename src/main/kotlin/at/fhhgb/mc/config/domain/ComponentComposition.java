package at.fhhgb.mc.config.domain;


import at.fhhgb.mc.component.base.IComponent;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "ComponentComposition")
public class ComponentComposition {

    private List<ComponentDefinition> componentDefinitions;

    @XmlElement(name = "ComponentDefinition")
    public List<ComponentDefinition> getComponentDefinitions() {
        return componentDefinitions;
    }

    public void setComponentDefinitions(List<ComponentDefinition> componentDefinitions) {
        this.componentDefinitions = componentDefinitions;
    }

    public List<IComponent> createComponents() {
        List<IComponent> components = new ArrayList<>();
        List<ComponentDefinition> definitions = getComponentDefinitions();
        if (definitions != null) {
            for (ComponentDefinition definition : definitions) {
                IComponent component = definition.createComponent();
                if (component != null) {
                    components.add(component);
                }
            }
        }

        return components;
    }
}
