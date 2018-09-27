package at.fhhgb.mc.component.base;


import at.fhhgb.mc.mediator.IMediator;

import java.awt.*;


/*

 */
public abstract class BaseComponent implements IComponent {

    protected IMediator mediator;

    @Override
    public void setMediator(IMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public abstract Panel getView();

    @Override
    public abstract String getName();

    @Override
    public abstract String getDescription();

    @Override
    public abstract void update(Object data);
}
