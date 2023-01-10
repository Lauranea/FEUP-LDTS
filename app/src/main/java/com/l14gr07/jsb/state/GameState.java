package com.l14gr07.jsb.state;

import com.l14gr07.jsb.Game;
import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.model.Player;
import com.l14gr07.jsb.model.World;
import com.l14gr07.jsb.controller.Controller;
import com.l14gr07.jsb.controller.game.GameController;
import com.l14gr07.jsb.viewer.Viewer;
import com.l14gr07.jsb.viewer.game.GameViewer;

import java.io.IOException;
import java.net.URISyntaxException;

public class GameState extends State<World>
{
    World world = new World();
    String cur_room = "";

    public GameState(World model)  throws IOException, URISyntaxException
    {
        super(model);
    }

    @Override
    protected Viewer<World> getViewer()
    {
        return new GameViewer(getModel());
    }

    @Override
    protected Controller<World> getController() throws IOException, URISyntaxException
    {
        return new GameController(getModel());
    }

    public Boolean get_won()
    {
        return world.get_current_room().get_won();
    }
}
