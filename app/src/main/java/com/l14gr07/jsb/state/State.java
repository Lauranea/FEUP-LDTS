package com.l14gr07.jsb.state;

import java.io.IOException;
import java.net.URISyntaxException;

import com.l14gr07.jsb.Game;
import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.controller.Controller;
import com.l14gr07.jsb.viewer.Viewer;

public abstract class State<T>
{
    final T model;
    private final Controller<T> controller;
    private final Viewer<T> viewer;

    public State(T nmodel)  throws IOException, URISyntaxException
    {
        model = nmodel;

        viewer = getViewer();
        controller = getController();
    }

    public T getModel()
    {
        return model;
    }

    protected abstract Viewer<T> getViewer();

    protected abstract Controller<T> getController() throws IOException, URISyntaxException;

    public void step(Game game, Gui gui) throws IOException, URISyntaxException
    {
        controller.step(game, gui.get_pressedKeys());
        viewer.step(gui);
    }
}
