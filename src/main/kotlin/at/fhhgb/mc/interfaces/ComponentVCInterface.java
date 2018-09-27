package at.fhhgb.mc.interfaces;

import java.awt.*;
import java.io.File;

public interface ComponentVCInterface extends ViewControllerInterface {
    public void changeInputPanel(Panel panel);

    public File exportBuilding();

    public String getRouteName();

}
