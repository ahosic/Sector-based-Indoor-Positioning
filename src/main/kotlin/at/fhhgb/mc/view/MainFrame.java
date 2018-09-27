package at.fhhgb.mc.view;

import at.fhhgb.mc.component.base.IComponent;
import at.fhhgb.mc.controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class MainFrame extends JFrame implements Observer {

    private MainController controller;

    private JTabbedPane tabbedPane;

    public MainFrame() {

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                if (controller != null) {
                    controller.close();
                }
            }
        });

        tabbedPane = new JTabbedPane();

        this.setLayout(new BorderLayout());

        this.add(tabbedPane, BorderLayout.CENTER);

        this.setLocation(100, 100);
        this.setSize(1440, 900);
        this.setVisible(true);
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof IComponent) {
            SwingUtilities.invokeLater(() -> {
                IComponent component = (IComponent) arg;
                int index = tabbedPane.indexOfTabComponent(component.getView());
                if (index == -1) {
                    addComponent(component);
                } else {
                    removeComponent(index);
                }
            });

        }
    }

    private void addComponent(IComponent component) {
        if (tabbedPane == null) {
            return;
        }
        tabbedPane.addTab(component.getName(), null, component.getView(), component.getDescription());
    }

    private void removeComponent(int index) {
        if (tabbedPane == null || tabbedPane.getTabCount() <= index) {
            return;
        }
        tabbedPane.removeTabAt(index);
    }
}
