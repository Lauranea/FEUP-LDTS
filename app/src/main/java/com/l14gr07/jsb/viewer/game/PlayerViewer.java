package com.l14gr07.jsb.viewer.game;

import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.model.Player;

import java.io.IOException;
import java.net.URISyntaxException;

public class PlayerViewer
{
    public void draw(Gui gui, Player player) throws IOException, URISyntaxException
    {
        gui.drawPlayer(player);
    }    
}
