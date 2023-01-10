package com.l14gr07.jsb.controller.game;

import java.util.Set;

import com.l14gr07.jsb.Game;
import com.l14gr07.jsb.controller.Controller;
import com.l14gr07.jsb.model.DeadAnim;
import com.l14gr07.jsb.model.Enemy;
import com.l14gr07.jsb.model.Player;
import com.l14gr07.jsb.model.Room;
import com.l14gr07.jsb.model.World;
import com.l14gr07.jsb.state.DeadState;
import com.l14gr07.jsb.state.MenuState;
import com.l14gr07.jsb.model.Item;
import com.l14gr07.jsb.model.Menu;

import java.io.IOException;
import java.net.URISyntaxException;

public class GameController extends Controller<World>
{
    PlayerController playerController = new PlayerController();
    EnemyController enemyController = new EnemyController();
    WorldController worldController = new WorldController();
    ItemController itemController = new ItemController();

    int frames = 100;
    int frame = 0;

    public GameController(World model)  throws IOException, URISyntaxException
    {
        super(model);
    }

    public void step(Game game, Set<Integer> pressedKeys) throws IOException, URISyntaxException
    {
        check_if_dead(getModel());
        collect_coin(getModel());
        worldController.change_room(getModel());

        playerController.step(pressedKeys, getModel().get_player(), getModel().get_current_room());
        for (Enemy enemy : getModel().get_current_room().get_enemies())
        {
            enemyController.step(enemy);
        }
        itemController.update();
        for (Item item : getModel().get_current_room().get_items())
        {
            itemController.step(item);
        }

        if (getModel().get_player().get_lives() == 0)
        {
            game.setState(new DeadState(new DeadAnim()));
        }
        else if (getModel().get_current_room().get_won())
        {
            if (frame > frames)
            {
                frame = 0;
                game.setState(new MenuState(new Menu()));
            }
            else
            {
                frame++;
            }
        }
    }
 

    void die(World world)  throws IOException, URISyntaxException
    {
        worldController.set_room(world, new Room(world.get_current_room().get_room_string(), world.get_current_room().get_coord1(), world.get_current_room().get_coord2(), world.get_current_room().get_room_name(), world.get_items(), 0));
        playerController.die(world.get_player());
        world.get_player().take_live();
    }

    boolean check_if_dead(World world)  throws IOException, URISyntaxException
    {
        for(Enemy e : world.get_current_room().get_enemies())
        {
            if ((world.get_player().get_position_x() > e.get_position_x() && world.get_player().get_position_x() < e.get_position_x() + e.get_size_x()) || (world.get_player().get_position_x() + world.get_player().get_size_x() > e.get_position_x() && world.get_player().get_size_x() + world.get_player().get_position_x() < e.get_position_x() + e.get_size_x()) || (world.get_player().get_position_x() + world.get_player().get_size_x() / 2 > e.get_position_x() && world.get_player().get_size_x() / 2 + world.get_player().get_position_x() < e.get_position_x() + e.get_size_x()))
            {
                if ((world.get_player().get_position_y() >= e.get_position_y() && world.get_player().get_position_y() <= e.get_position_y() + e.get_size_y()) || (world.get_player().get_position_y() + world.get_player().get_size_y() >= e.get_position_y() && world.get_player().get_size_y() + world.get_player().get_position_y() <= e.get_position_y() + e.get_size_y()) || (world.get_player().get_position_y() < e.get_position_y() && world.get_player().get_position_y() + world.get_player().get_size_y() > e.get_position_y() + e.get_size_y()))
                {
                    die(world);
                    return true;
                }
            }
        }
        return false;
    }

    void collect_coin(World world)
    {
        for (int i = 0; i < world.get_current_room().get_items().size(); i++)
        {
            Item item = world.get_current_room().get_items().get(i);
            if ((world.get_player().get_position_x() >= item.get_position_x() + 2 && world.get_player().get_position_x() <= item.get_position_x() + 8) || (world.get_player().get_position_x() + world.get_player().get_size_x() >= item.get_position_x() + 2 && world.get_player().get_size_x() + world.get_player().get_position_x() <= item.get_position_x() + 8) || ((world.get_player().get_position_x() + world.get_player().get_size_x() / 2 >= item.get_position_x() + 2 && world.get_player().get_position_x() + world.get_player().get_size_x() / 2 <= item.get_position_x() + 8)))
            {
                if ((world.get_player().get_position_y() >= item.get_position_y() + 2 && world.get_player().get_position_y() <= item.get_position_y() + 8) || (world.get_player().get_position_y() + world.get_player().get_size_y() >= item.get_position_y() + 2 && world.get_player().get_size_y() + world.get_player().get_position_y() <= item.get_position_y() + 8) || (world.get_player().get_position_y() < item.get_position_y() + 2 && world.get_player().get_position_y() + world.get_player().get_size_y() > item.get_position_y() + 8))
                {
                    world.get_current_room().remove_item(item);
                    world.remove_item(i);
                    world.collect_coin();
                }
            }
        }
    }
}
