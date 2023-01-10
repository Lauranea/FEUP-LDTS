package com.l14gr07.jsb.viewer.dead;

import com.l14gr07.jsb.Game;
import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.controller.dead.DeadAnimController;
import com.l14gr07.jsb.model.DeadAnim;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

class DeadAnimViewerTest {
    DeadAnim deadAnim;
    
    DeadAnimViewer deadAnimViewer;
    Gui gui;
    Game game;
    Set<Integer> pressedKeys;
    DeadAnim model;

    DeadAnimViewerTest() throws IOException, URISyntaxException {
        deadAnim = new DeadAnim();
        deadAnimViewer = new DeadAnimViewer(deadAnim);
        gui = Mockito.mock(Gui.class);
        game = Mockito.mock(Game.class);
        model = Mockito.mock(DeadAnim.class);
    }
    @Test
    void stepTestLoop() throws IOException, URISyntaxException {
        deadAnimViewer.step(gui);
        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                Mockito.verify(gui, Mockito.times(1)).drawImage(i * 10, j * 10 + 136, deadAnimViewer.getModel().getCo());
            }
        }
    }
    @Test
    void stepTestAlways() throws IOException, URISyntaxException {
        deadAnimViewer.step(gui);
        Mockito.verify(gui, Mockito.times(1)).drawImage(121,120,deadAnimViewer.getModel().getPo());
        Mockito.verify(gui, Mockito.times(1)).drawImage(119, deadAnimViewer.getModel().get_fo_pos(), deadAnimViewer.getModel().getFo());
    }
}