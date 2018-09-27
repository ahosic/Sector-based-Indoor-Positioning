package at.fhhgb.mc.component.base;


import at.fhhgb.mc.mediator.IMediator;

import java.awt.*;

public interface IComponent {
    void setMediator(IMediator mediator);

    Panel getView();

    String getName();

    String getDescription();

    void update(Object data);
}
