package com.l14gr07.jsb.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import com.l14gr07.jsb.Game;
import com.l14gr07.jsb.model.World;

public abstract class Controller<T>
{
    private final T model;

    public Controller(T model)
    {
        this.model = model;
    }

    public T getModel()
    {
        return model;
    }

    public abstract void step(Game game, Set<Integer> pressedKeys) throws IOException, URISyntaxException;
}