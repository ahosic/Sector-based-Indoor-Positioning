package at.fhhgb.mc.config;

import at.fhhgb.mc.component.base.IComponent;
import at.fhhgb.mc.config.domain.ComponentComposition;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ComponentFactory {
    String backupFile;

    public ComponentFactory(String backupFile) {
        this.backupFile = backupFile;
    }

    public List<IComponent> getComponents() {
        InputStream inputStream = null;
        if (inputStream == null) {
            try {
                ClassLoader classLoader = getClass().getClassLoader();
                inputStream = new FileInputStream(new File(classLoader.getResource(backupFile).getFile()));
            } catch (FileNotFoundException e) {
                System.out.println("Error: No configuration file found!");
                e.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                JAXBContext context = JAXBContext.newInstance(ComponentComposition.class);
                Unmarshaller um = context.createUnmarshaller();
                ComponentComposition componentComposition = (ComponentComposition) um.unmarshal(inputStream);
                return componentComposition.createComponents();
            } catch (JAXBException e) {
                e.printStackTrace();
                System.out.println("Error: Parsing configuration file failed!");
            }
        }
        return new ArrayList<>();
    }
}
