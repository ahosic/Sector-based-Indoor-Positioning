package at.fhhgb.mc.domain;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;


public class ParamDefinitionAdapter extends XmlAdapter<Element, ParamDefinition> {
    private static final String XML_ROOT = "Param";
    private static final String XML_NAME = "Name";
    private static final String XML_SIGNATURE_TYPE = "SignatureType";
    private static final String XML_INSTANCE_TYPE = "InstanceType";
    private static final String XML_INSTANCE_VALUE = "InstanceValue";

    private ClassLoader classLoader;
    private DocumentBuilder documentBuilder;
    private JAXBContext jaxbContext;

    public ParamDefinitionAdapter() {
        classLoader = Thread.currentThread().getContextClassLoader();
    }

    public ParamDefinitionAdapter(JAXBContext jaxbContext) {
        this();
        this.jaxbContext = jaxbContext;
    }

    private DocumentBuilder getDocumentBuilder() throws Exception {
        // Lazy load the DocumentBuilder as it is not used for unmarshalling.
        if (null == documentBuilder) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            documentBuilder = dbf.newDocumentBuilder();
        }
        return documentBuilder;
    }

    private JAXBContext getJAXBContext(Class<?> type) throws Exception {
        if (null == jaxbContext) {
            // A JAXBContext was not set, so create a new one based  on the type.
            return JAXBContext.newInstance(type);
        }
        return jaxbContext;
    }

    @Override
    public Element marshal(ParamDefinition paramDefinition) throws Exception {
        if (null == paramDefinition) {
            return null;
        }
        //load document and create root node
        Document document = getDocumentBuilder().newDocument();
        Element documentElement = document.createElement(XML_ROOT);

        if (paramDefinition.getName() != null) {
            Object nameValue = paramDefinition.getName();
            Class<?> nameType = nameValue.getClass();
            JAXBElement nameJAXB = new JAXBElement(new QName(XML_NAME), nameType, nameValue);

            Marshaller instanceMarshaller = getJAXBContext(nameType).createMarshaller();
            instanceMarshaller.marshal(nameJAXB, documentElement);
        }
        if (paramDefinition.getSignatureType() != null) {
            Object typeValue = paramDefinition.getSignatureType();
            Class<?> typeType = typeValue.getClass();
            JAXBElement typeJAXB = new JAXBElement(new QName(XML_SIGNATURE_TYPE), typeType, typeValue);

            Marshaller instanceMarshaller = getJAXBContext(typeType).createMarshaller();
            instanceMarshaller.marshal(typeJAXB, documentElement);
        }
        if (paramDefinition.getInstanceType() != null) {
            Object instanceTypeValue = paramDefinition.getInstanceType();
            Class<?> instanceTypeType = instanceTypeValue.getClass();
            JAXBElement instanceTypeJAXB = new JAXBElement(new QName(XML_INSTANCE_TYPE), instanceTypeType, instanceTypeValue);

            Marshaller instanceMarshaller = getJAXBContext(instanceTypeType).createMarshaller();
            instanceMarshaller.marshal(instanceTypeJAXB, documentElement);
        }
        if (paramDefinition.getInstanceValue() != null) {
            //add instanceValue to document
            Object instanceValueValue = paramDefinition.getInstanceValue(); //TODO
            Class<?> instanceValueType = instanceValueValue.getClass();
            if (paramDefinition.getInstanceType() == null) {
                JAXBElement instanceTypeJAXB = new JAXBElement(new QName(XML_INSTANCE_TYPE), String.class, instanceValueType.getName());
                Marshaller instanceMarshaller = getJAXBContext(String.class).createMarshaller();
                instanceMarshaller.marshal(instanceTypeJAXB, documentElement);
            }
            JAXBElement instanceValueJAXB = new JAXBElement(new QName(XML_INSTANCE_VALUE), instanceValueType, instanceValueValue);
            Marshaller instanceMarshaller = getJAXBContext(instanceValueType).createMarshaller();
            instanceMarshaller.marshal(instanceValueJAXB, documentElement);
        }
        return documentElement;
    }

    @Override
    public ParamDefinition unmarshal(Element element) throws Exception {
        if (null == element) {
            return null;
        }

        ParamDefinition paramDefinition = new ParamDefinition();

        if (!element.getTagName().equals(XML_ROOT)) {
            return null;
        }

        NodeList names = element.getElementsByTagName(XML_NAME);
        NodeList signatureTypes = element.getElementsByTagName(XML_SIGNATURE_TYPE);
        NodeList instanceTypes = element.getElementsByTagName(XML_INSTANCE_TYPE);
        NodeList instanceValues = element.getElementsByTagName(XML_INSTANCE_VALUE);

        if (names.getLength() > 0) {
            Element name = (Element) names.item(0);
            DOMSource source = new DOMSource(name);
            Unmarshaller unmarshaller = getJAXBContext(String.class).createUnmarshaller();
            JAXBElement nameJAXB = unmarshaller.unmarshal(source, String.class);
            paramDefinition.setName((String) nameJAXB.getValue());
        }
        if (signatureTypes.getLength() > 0) {
            Element signatureType = (Element) signatureTypes.item(0);
            DOMSource source = new DOMSource(signatureType);
            Unmarshaller unmarshaller = getJAXBContext(String.class).createUnmarshaller();
            JAXBElement signatureTypeJAXB = unmarshaller.unmarshal(source, String.class);
            paramDefinition.setSignatureType((String) signatureTypeJAXB.getValue());
        }
        if (instanceTypes.getLength() > 0) {
            Element instanceType = (Element) instanceTypes.item(0);
            DOMSource source = new DOMSource(instanceType);
            Unmarshaller unmarshaller = getJAXBContext(String.class).createUnmarshaller();
            JAXBElement instanceTypeJAXB = unmarshaller.unmarshal(source, String.class);
            paramDefinition.setInstanceType((String) instanceTypeJAXB.getValue());
        }
        if (instanceValues.getLength() > 0 && paramDefinition.getInstanceType() != null) {
            Element instanceValue = (Element) instanceValues.item(0);
            DOMSource source = new DOMSource(instanceValue);
            Class<?> type = classLoader.loadClass(paramDefinition.getInstanceType());
            Unmarshaller unmarshaller = getJAXBContext(type).createUnmarshaller();
            JAXBElement instanceValueJAXB = unmarshaller.unmarshal(source, type);
            paramDefinition.setInstanceValue(instanceValueJAXB.getValue());
        }

        return paramDefinition;
    }
}