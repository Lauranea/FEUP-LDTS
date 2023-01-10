package com.l14gr07.jsb.viewer;

import java.io.IOException;
import java.net.URISyntaxException;

import com.l14gr07.jsb.Gui;

public abstract class Viewer<T>
{
    private final T model;

    public Viewer(T model)
    {
        this.model = model;
    }

    public T getModel()
    {
        return model;
    }

    public void draw(Gui gui) throws IOException, URISyntaxException
    {
        gui.clear();
        step(gui);
        gui.refresh();
    }

    public abstract void step(Gui gui) throws IOException, URISyntaxException;
}
