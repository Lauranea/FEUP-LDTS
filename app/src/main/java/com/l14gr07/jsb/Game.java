package com.l14gr07.jsb;

import com.l14gr07.jsb.state.MenuState;
import com.l14gr07.jsb.state.State;
import com.l14gr07.jsb.model.Menu;

import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.FontFormatException;

public class Game
{
    State<?> state;

    Gui gui;

    public Game()  throws IOException, URISyntaxException, FontFormatException
    {
        state = new MenuState(new Menu());
        gui = new Gui();
    }

    public void setState(State<?> nstate)
    {
        state = nstate;
    }

    public void run()  throws IOException, URISyntaxException, InterruptedException
    {
        while (gui.get_run())
        {
            gui.clear();

            state.step(this, gui);

            gui.refresh();

            Thread.sleep(15);
        }
        gui.close();
    }
}