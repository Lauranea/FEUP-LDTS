package com.l14gr07.jsb.viewer.game;

import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.net.URISyntaxException;

class PlayerViewerTest 
{
    PlayerViewer playerViewer;
    Gui gui;
    Player player;
    
    @BeforeEach
    void startup()  throws IOException, URISyntaxException
    {
        playerViewer = new PlayerViewer();
        gui = Mockito.mock(Gui.class);
        player = new Player(0, 0, 10);
    }

    @Test
    void drawPlayer()  throws IOException, URISyntaxException
    {
        playerViewer.draw(gui, player);

        Mockito.verify(gui, Mockito.times(1)).drawPlayer(player);
    }
}