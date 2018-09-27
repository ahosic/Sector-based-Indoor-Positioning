package at.fhhgb.mc.config.domain;

import at.fhhgb.mc.component.base.IComponent;
import at.fhhgb.mc.domain.MethodDefinition;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@XmlRootElement(name = "ComponentDefinition")
@XmlType(propOrder = {"name", "description", "className", "methodDefinitions"})
public class ComponentDefinition {
    private String name;
    private String description;
    private String className;
    private List<MethodDefinition> methodDefinitions;

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @XmlElement(name = "Class")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @XmlElement(name = "MethodDefinition")
    public List<MethodDefinition> getMethodDefinitions() {
        return methodDefinitions;
    }

    public void setMethodDefinitions(List<MethodDefinition> methodDefinitions) {
        this.methodDefinitions = methodDefinitions;
    }

    public IComponent createComponent() {
        Class c = this.getClass();
        IComponent component;
        try {
            component = (IComponent) c.forName(getClassName()).newInstance();
            List<MethodDefinition> methodDefinitions = getMethodDefinitions();
            if (methodDefinitions != null) {
                for (MethodDefinition methodDefinition : methodDefinitions) {
                    methodDefinition.callMethod(component);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            component = null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            component = null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            component = null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            component = null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            component = null;
        }
        return component;
    }
}
