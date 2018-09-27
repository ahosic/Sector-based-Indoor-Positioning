package at.fhhgb.mc.interfaces;

import at.fhhgb.mc.component.base.BaseModel;

import java.awt.*;
import java.io.File;

public interface ViewControllerInterface {
    public File getFileFromFileChoose(String extension);

    public void setObervableModel(BaseModel model);

    public void createFLoorChoice(int numberOfFloors, int startindex);

    public Panel getViewPanel();

    public int[] getDimensions();
}
