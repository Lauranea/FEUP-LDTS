package com.l14gr07.jsb.controller.game;


import com.l14gr07.jsb.model.Player;
import com.l14gr07.jsb.model.Room;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest 
{
    
    private PlayerController playerController = new PlayerController();
    private Player player;
    public PlayerControllerTest()  throws IOException, URISyntaxException
    {
        player = new Player(0, 0, 10);
    }
    private int x;
    private int y;
    private int move;
    private Random random = new Random();
    
    @BeforeEach
    void startup()
    {
        x = random.nextInt(0, 10);
        y = random.nextInt(0, 10);
        move = random.nextInt(0,10);
        player.set_position_y(y);
        player.set_position_x(x);
        Player player2 = Mockito.mock(Player.class);
        Room room = Mockito.mock(Room.class);
        Mockito.when(room.get_room_string()).thenReturn("x **               z----x\n" +
                "x         9       z     x\n" +
                "x        9       z      x\n" +
                "x               z       f\n" +
                "x      9       z        f\n" +
                "x     9    zxxxxxxxxxxxxx\n" +
                "x         z      x      x\n" +
                "x        z       x      x\n" +
                "x       z 0      x *x   x\n" +
                "x      z 0       x *x   x\n" +
                "x     z          x *x  -x\n" +
                "x    z 0         x *x   x\n" +
                "xs  - 0            *x-  x\n" +
                "#########################\n" +
                "#########################");
        Set<Integer> pressedKeys = new HashSet<>();
        PlayerController playerController2 = new PlayerController();
        playerController2.set_grounded(true);
    }

    @RepeatedTest(5)
    void moveRightTest()
    {
        for(int i = 0; i < move; i++)
        {
            playerController.move_right(player);
        }
        assertEquals(x + move, player.get_position_x());
        assertEquals(y, player.get_position_y());
    }

    @RepeatedTest(5)
    void moveLeftTest()
    {
        for(int i = 0; i < move; i++)
        {
            playerController.move_left(player);
        }
        assertEquals(x - move, player.get_position_x());
        assertEquals(y, player.get_position_y());
    }
    /*
    @Test
    void stepGroundedWTest()
    {
        Player player2 = Mockito.mock(Player.class);
        Room room = Mockito.mock(Room.class);
        Set<Integer> pressedKeys = new HashSet<>();
        pressedKeys.add(KeyEvent.VK_W);
        PlayerController playerController2 = Mockito.mock(PlayerController.class);
        Mockito.when(playerController2.get_grounded()).thenReturn(true);
        playerController2.step(pressedKeys, player2, room);
        Mockito.verify(playerController2, Mockito.times(0)).get_grounded();
    }

    @Test
    void stepGroundedWTest1()
    {
        Player player2 = Mockito.mock(Player.class);
        Room room = Mockito.mock(Room.class);
        Set<Integer> pressedKeys = new HashSet<>();
        pressedKeys.add(KeyEvent.VK_W);
        assertEquals(true, pressedKeys.contains(KeyEvent.VK_W));
    }
    */
    
    @Test
    void stepGroundedASpriteTest()
    {
        Player player2 = Mockito.mock(Player.class);
        Room room = Mockito.mock(Room.class);
        Mockito.when(room.get_room_string()).thenReturn("x **               z----x\n" +
                "x         9       z     x\n" +
                "x        9       z      x\n" +
                "x               z       f\n" +
                "x      9       z        f\n" +
                "x     9    zxxxxxxxxxxxxx\n" +
                "x         z      x      x\n" +
                "x        z       x      x\n" +
                "x       z 0      x *x   x\n" +
                "x      z 0       x *x   x\n" +
                "x     z          x *x  -x\n" +
                "x    z 0         x *x   x\n" +
                "xs  - 0            *x-  x\n" +
                "#########################\n" +
                "#########################");
        Set<Integer> pressedKeys = new HashSet<>();
        pressedKeys.add(KeyEvent.VK_A);
        PlayerController playerController2 = new PlayerController();
        playerController2.set_grounded(true);
        playerController2.step(pressedKeys, player2, room);
        Mockito.verify(player2, Mockito.times(1)).change_sprite_left(playerController2.direction_level);
    }

    @Test
    void stepGroundedDSpriteTest()
    {
        Player player2 = Mockito.mock(Player.class);
        Room room = Mockito.mock(Room.class);
        Mockito.when(room.get_room_string()).thenReturn("x **               z----x\n" +
                "x         9       z     x\n" +
                "x        9       z      x\n" +
                "x               z       f\n" +
                "x      9       z        f\n" +
                "x     9    zxxxxxxxxxxxxx\n" +
                "x         z      x      x\n" +
                "x        z       x      x\n" +
                "x       z 0      x *x   x\n" +
                "x      z 0       x *x   x\n" +
                "x     z          x *x  -x\n" +
                "x    z 0         x *x   x\n" +
                "xs  - 0            *x-  x\n" +
                "#########################\n" +
                "#########################");
        Set<Integer> pressedKeys = new HashSet<>();
        pressedKeys.add(KeyEvent.VK_D);
        PlayerController playerController2 = new PlayerController();
        playerController2.set_grounded(true);
        playerController2.step(pressedKeys, player2, room);
        Mockito.verify(player2, Mockito.times(1)).change_sprite_right(playerController2.direction_level);
    }
    
    @Property
    void collision_Left_Test(@ForAll int x, @ForAll int y, @ForAll int i, @ForAll int j) throws IOException, URISyntaxException 
    {
        player = Mockito.mock(Player.class);
        Mockito.when(player.get_position_x()).thenReturn(x);
        Mockito.when(player.get_position_y()).thenReturn(y);
        Mockito.when(player.get_size_x()).thenReturn(10);
        Mockito.when(player.get_size_y()).thenReturn(10);
        playerController.collision_left(player, i, j);
        if ((player.get_position_x() == i * 10 + 10 || player.get_position_x() == i * 10 + 9) && ((player.get_position_y() + 6 > j * 10 && player.get_position_y() + 6 <= j * 10 + 10) || (player.get_position_y() + player.get_size_y() > j * 10 && player.get_position_y() + player.get_size_y() <= j * 10 + 10) || (player.get_position_y() > j * 10 && player.get_position_y() <= j * 10 + 10)))
        {
            Mockito.verify(player, Mockito.atMost(9)).get_position_x();
            //Mockito.verify(player2, Mockito.times(1)).set_position_x(player2.get_position_x()-1);
            assertEquals(true, playerController.collision_left(player, i, j));
        }
        else
        {
            Mockito.verify(player, Mockito.atMost(7)).get_position_x();
            //Mockito.verify(player2, Mockito.times(0)).set_position_x(player2.get_position_x()-1);
            assertEquals(false, playerController.collision_left(player, i, j));
        }
    }

    @Property
    void collision_Right_Test(@ForAll int x, @ForAll int y, @ForAll int i, @ForAll int j) throws IOException, URISyntaxException
    {
        player = Mockito.mock(Player.class);
        Mockito.when(player.get_position_x()).thenReturn(x);
        Mockito.when(player.get_position_y()).thenReturn(y);
        Mockito.when(player.get_size_x()).thenReturn(10);
        Mockito.when(player.get_size_y()).thenReturn(10);
        playerController.collision_right(player, i, j);
        if ((player.get_position_x() + player.get_size_x() == i * 10 || player.get_position_x() + player.get_size_x() == i * 10 + 1) && ((player.get_position_y() + 6 > j * 10 && player.get_position_y() + 6 <= j * 10 + 10) || (player.get_position_y() + player.get_size_y() > j * 10 && player.get_position_y() + player.get_size_y() <= j * 10 + 10) || (player.get_position_y() > j * 10 && player.get_position_y() <= j * 10 + 10)))
        {
            Mockito.verify(player, Mockito.atMost(9)).get_position_x();
            //Mockito.verify(player, Mockito.times(1)).set_position_x(player.get_position_x()-1);
            assertEquals(true, playerController.collision_right(player, i, j));
        }
        else
        {
            Mockito.verify(player, Mockito.atMost(7)).get_position_x();
            //Mockito.verify(player, Mockito.times(0)).set_position_x(player.get_position_x()-1);
            assertEquals(false, playerController.collision_right(player, i, j));
        }
    }
}