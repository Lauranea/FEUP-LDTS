package com.l14gr07.jsb.controller.game;

import com.l14gr07.jsb.model.Player;
import com.l14gr07.jsb.model.Room;
import com.l14gr07.jsb.model.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.io.IOException;
import java.net.URISyntaxException;

class WorldControllerTest 
{
    
    WorldController worldController = new WorldController();
    World world;
    Room room;
    Player player;
    @BeforeEach
    void startup()
    {
        world = Mockito.mock(World.class);
        room = Mockito.mock(Room.class);
        player = Mockito.mock(Player.class);
    }
    
    @Test
    void changeRoom_Left_setpos_Test()  throws IOException, URISyntaxException
    {
        Mockito.when(world.get_player()).thenReturn(player);
        Mockito.when(player.get_position_x()).thenReturn(-1);
        Mockito.when(player.get_position_y()).thenReturn(10);
        Mockito.when(world.get_current_room()).thenReturn(room);
        worldController.change_room(world);
        Mockito.verify(world.get_player(), Mockito.times(1)).set_position_x(237);
    }
    @Test
    void changeRoom_Right_setpos_Test()  throws IOException, URISyntaxException
    {
        Mockito.when(world.get_player()).thenReturn(player);
        Mockito.when(player.get_position_x()).thenReturn(241);
        Mockito.when(player.get_position_y()).thenReturn(10);
        Mockito.when(world.get_current_room()).thenReturn(room);
        worldController.change_room(world);
        Mockito.verify(world.get_player(), Mockito.times(1)).set_position_x(3);
    }

    @Test
    void changeRoom_Up_setpos_Test()  throws IOException, URISyntaxException
    {
        Mockito.when(world.get_player()).thenReturn(player);
        Mockito.when(player.get_position_x()).thenReturn(10);
        Mockito.when(player.get_position_y()).thenReturn(116);
        Mockito.when(world.get_current_room()).thenReturn(room);
        worldController.change_room(world);
        Mockito.verify(world.get_player(), Mockito.times(1)).set_position_y(-5);
    }

    @Test
    void changeRoom_Down_setpos_Test()  throws IOException, URISyntaxException
    {
        Mockito.when(world.get_player()).thenReturn(player);
        Mockito.when(player.get_position_x()).thenReturn(10);
        Mockito.when(player.get_position_y()).thenReturn(-11);
        Mockito.when(world.get_current_room()).thenReturn(room);
        worldController.change_room(world);
        Mockito.verify(world.get_player(), Mockito.times(1)).set_position_y(107);
    }



    @Test
    void setRoomTest()
    {
        worldController.set_room(world, room);
        Mockito.verify(world, Mockito.times(1)).set_current_room(room);
    }
}