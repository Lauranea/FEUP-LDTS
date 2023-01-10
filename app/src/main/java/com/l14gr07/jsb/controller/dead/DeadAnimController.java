package com.l14gr07.jsb.controller.dead;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import com.l14gr07.jsb.Game;
import com.l14gr07.jsb.controller.Controller;
import com.l14gr07.jsb.model.DeadAnim;
import com.l14gr07.jsb.model.Menu;
import com.l14gr07.jsb.state.MenuState;

public class DeadAnimController extends Controller<DeadAnim>
{
    public DeadAnimController(DeadAnim model)
    {
        super(model);
    }

    public void step(Game game, Set<Integer> pressedKeys) throws IOException, URISyntaxException
    {
        getModel().set_f(getModel().get_f()+1);
        if (getModel().get_f() > getModel().get_frames())
        {
            game.setState(new MenuState(new Menu()));
        }
        if (getModel().get_fo_pos() < -57)
        {
            getModel().set_fo_pos(getModel().get_fo_pos()+3);
        }
    }
}
