package com.l14gr07.jsb.controller.dead;

import com.googlecode.lanterna.graphics.BasicTextImage;
import com.l14gr07.jsb.Game;
import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.Loader;
import com.l14gr07.jsb.model.DeadAnim;

import com.l14gr07.jsb.model.Menu;
import com.l14gr07.jsb.state.MenuState;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

class DeadAnimControllerTest {
    DeadAnim deadAnim;
    DeadAnimController deadAnimController;
    Gui gui;
    Game game;
    Set<Integer> pressedKeys;
    DeadAnim model;
    
    DeadAnimControllerTest() throws IOException, URISyntaxException {
        deadAnim = Mockito.mock(DeadAnim.class);
        deadAnimController = new DeadAnimController(deadAnim);
        gui = Mockito.mock(Gui.class);
        game = Mockito.mock(Game.class);
        model = Mockito.mock(DeadAnim.class);
    }
    @Property
    void stepTestIf2(@ForAll Integer StartF, @ForAll Integer frames, @ForAll Integer Start_fo_pos) throws IOException, URISyntaxException {
        deadAnimController.getModel().set_f(StartF);
        deadAnimController.getModel().set_fo_pos(Start_fo_pos);
        deadAnimController.getModel().set_frames(frames);
        deadAnimController.step(game, pressedKeys);
        if(deadAnimController.getModel().get_fo_pos() < -57)
        {
            Mockito.verify(deadAnimController.getModel(), Mockito.times(1)).set_fo_pos(deadAnimController.getModel().get_fo_pos()+3);
        }
    }
    /*
    @Property
    void stepTestSetF(@ForAll Integer StartF, @ForAll Integer frames, @ForAll Integer Start_fo_pos) throws IOException, URISyntaxException {
        deadAnimController.getModel().set_f(StartF);
        deadAnimController.getModel().set_fo_pos(Start_fo_pos);
        deadAnimController.getModel().set_frames(frames);
        deadAnimController.step(game, pressedKeys);
        if(deadAnimController.getModel().get_fo_pos() < -57)
        {
            assertEquals(deadAnimController.getModel().get_f(), 0);
        }
        else if(deadAnimController.getModel().get_f() > deadAnimController.getModel().get_frames())
        {
            assertEquals(deadAnimController.getModel().get_f(), 0);
        }
        else
        {
            assertEquals(deadAnimController.getModel().get_f(), StartF + 1);
        }
    }
    */
}