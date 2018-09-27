package at.fhhgb.mc.domain;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

//@XmlJavaTypeAdapter(ParamDefinitionAdapter.class)

//@XmlRootElement(name = "paramDefinition")
//@XmlType(propOrder = { "name", "signatureType", "instanceType", "instanceValue" })

@XmlJavaTypeAdapter(ParamDefinitionAdapter.class)
public class ParamDefinition {
    private String name;
    private String signatureType; //method signature type
    private String instanceType; //instance type (could be null)
    private Object instanceValue;   //instance String value (could be null)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public Object getInstanceValue() {
        return instanceValue;
    }

    public void setInstanceValue(Object instanceValue) {
        this.instanceValue = instanceValue;
    }
}
