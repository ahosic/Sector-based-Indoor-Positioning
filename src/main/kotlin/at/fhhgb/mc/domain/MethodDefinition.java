package at.fhhgb.mc.domain;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@XmlRootElement(name = "MethodDefinition")
@XmlType(propOrder = {"name", "params"})
public class MethodDefinition {
    private String name;
    private List<ParamDefinition> params;

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //@XmlElement(name = "paramDefinition")
    @XmlAnyElement
    public List<ParamDefinition> getParams() {
        return params;
    }

    public void setParams(List<ParamDefinition> params) {
        this.params = params;
    }

    public Object callMethod(Object o) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class c = this.getClass();
        Class[] classes = null;
        Object[] values = null;
        if (params != null || params.size() == 0) {
            classes = new Class[params.size()];
            values = new Object[params.size()];
            for (int i = 0; i < params.size(); i++) {
                ParamDefinition paramDefinition = params.get(i);
                classes[i] = c.forName(paramDefinition.getSignatureType());
                if (paramDefinition.getInstanceValue() == null) {
                    values[i] = c.forName(paramDefinition.getInstanceType()).newInstance();
                } else {
                    values[i] = paramDefinition.getInstanceValue();
                }
            }
        }

        Method method = o.getClass().getMethod(getName(), classes);
        return method.invoke(o, values);
    }
}