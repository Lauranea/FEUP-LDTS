package com.l14gr07.jsb.controller.menu;

import java.util.Set;

import com.l14gr07.jsb.Game;
import com.l14gr07.jsb.controller.Controller;
import com.l14gr07.jsb.model.Menu;
import com.l14gr07.jsb.model.World;
import com.l14gr07.jsb.state.GameState;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;

public class MenuController extends Controller<Menu>
{
    public MenuController(Menu model)
    {
        super(model);
    }

    public void step(Game game, Set<Integer> pressedKeys) throws IOException, URISyntaxException
    {
        for (Integer key : pressedKeys)
        {
            if (key == null)
            {
                continue;
            }
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
            {
                getModel().move_up();
            }
            else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
            {
                getModel().move_down();
            }
            else if (key == KeyEvent.VK_ENTER)
            {
                pressedKeys.remove(key);
                if (getModel().select())
                {
                    game.setState(new GameState(new World()));
                }
            }
            else if (key == KeyEvent.VK_BACK_SPACE)
            {
                getModel().go_back();
                pressedKeys.remove(key);
            }
        }
    }
}
