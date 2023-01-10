package com.l14gr07.jsb.model;

import com.l14gr07.jsb.controller.game.PlayerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import java.io.IOException;
import java.net.URISyntaxException;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest 
{
    private PlayerController playerController = new PlayerController();
    private Player player;
    public PlayerTest()  throws IOException, URISyntaxException
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
    }

    @RepeatedTest(5)
    void get_x_Test()
    {
        assertEquals(x, player.get_position_x());
    }

    @RepeatedTest(5)
    void get_y_Test()
    {
        assertEquals(y, player.get_position_y());
    }

    @RepeatedTest(5)
    void set_get_y_Test()
    {
        y = random.nextInt(0, 10);
        player.set_position_y(y);
        assertEquals(y, player.get_position_y());
    }

    @RepeatedTest(5)
    void set_get_x_Test()
    {
        x = random.nextInt(0, 10);
        player.set_position_x(x);
        assertEquals(x, player.get_position_x());
    }
}