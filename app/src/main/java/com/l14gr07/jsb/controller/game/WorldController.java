package com.l14gr07.jsb.controller.game;

import com.l14gr07.jsb.model.Player;
import com.l14gr07.jsb.model.Room;
import com.l14gr07.jsb.model.World;

import java.io.IOException;
import java.net.URISyntaxException;

public class WorldController
{
    public void set_room(World world, Room nroom)
    {
        world.set_current_room(nroom);
    }

    void set_safe_position(Player player)
    {
        player.set_safe_position_x(player.get_position_x());
        player.set_safe_position_y(player.get_position_y());
    }

    public void change_room(World world)  throws IOException, URISyntaxException
    {
        if (world.get_player().get_position_x() < 0)
        {
            world.set_current_room(world.get_room(world.get_current_room().get_coord1(), world.get_current_room().get_coord2() - 1));

            world.get_player().set_position_x(237);
            set_safe_position(world.get_player());
        }
        else if (world.get_player().get_position_x() > 240)
        {
            world.set_current_room(world.get_room(world.get_current_room().get_coord1(), world.get_current_room().get_coord2() + 1));
            
            world.get_player().set_position_x(3);
            set_safe_position(world.get_player());
        }
        else if (world.get_player().get_position_y() > 115)
        {
            world.set_current_room(world.get_room(world.get_current_room().get_coord1() + 1, world.get_current_room().get_coord2()));
            
            world.get_player().set_position_y(-5);
            set_safe_position(world.get_player());
        }
        else if (world.get_player().get_position_y() < -10)
        {
            world.set_current_room(world.get_room(world.get_current_room().get_coord1() - 1, world.get_current_room().get_coord2()));
            
            world.get_player().set_position_y(107);
            set_safe_position(world.get_player());
        }
    }   
}
