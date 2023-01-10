package com.l14gr07.jsb.model;

import com.l14gr07.jsb.controller.game.EnemyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest 
{

    private EnemyController enemyController;
    private Enemy enemyBoomerang;
    private Enemy enemyNoBoomerang;
    private int p1x;
    private int p1y;
    private int p2x;
    private int p2y;
    private int speed;
    private Random random = new Random();

    @BeforeEach
    void startup()
    {
        p1x = random.nextInt(0, 10);
        p1y = random.nextInt(0, 10);
        p2x = random.nextInt(0, 10);
        p2y = random.nextInt(0, 10);
        speed = random.nextInt(0, 10);
        enemyBoomerang = new Enemy(p1x, p1y, p2x, p2y, true, speed, "xxxxx\nxxxxx\nxxxxx\nxxxxx\nxxxxx", 5, 5);
        enemyNoBoomerang = new Enemy(p1x, p1y, p2x, p2y, false, speed, "xxxxx\nxxxxx\nxxxxx\nxxxxx\nxxxxx", 5, 5);
    }

    @RepeatedTest(5)
    void get_Pos1x_Test()
    {
        assertEquals(p1x, enemyBoomerang.get_position_x());
        assertEquals(p1x, enemyNoBoomerang.get_position_x());
    }

    @RepeatedTest(5)
    void get_Pos1y_Test()
    {
        assertEquals(p1y, enemyBoomerang.get_position_y());
        assertEquals(p1y, enemyNoBoomerang.get_position_y());
    }

    @RepeatedTest(5)
    void get_Pos2x_Test()
    {
        assertEquals(p2x, enemyBoomerang.get_position_x2());
        assertEquals(p2x, enemyNoBoomerang.get_position_x2());
    }

    @RepeatedTest(5)
    void get_Pos2y_Test()
    {
        assertEquals(p2y, enemyBoomerang.get_position_y2());
        assertEquals(p2y, enemyNoBoomerang.get_position_y2());
    }

    @RepeatedTest(5)
    void get_Speed_Test()
    {
        assertEquals(speed, enemyBoomerang.get_speed());
        assertEquals(speed, enemyNoBoomerang.get_speed());
    }

    @RepeatedTest(5)
    void set_Pos1x_Test()
    {
        p1x = random.nextInt(0, 10);
        enemyBoomerang.set_position_x(p1x);
        enemyNoBoomerang.set_position_x(p1x);
        assertEquals(p1x, enemyBoomerang.get_position_x());
        assertEquals(p1x, enemyNoBoomerang.get_position_x());
    }

    @RepeatedTest(5)
    void set_Pos1y_Test()
    {
        p1y = random.nextInt(0, 10);
        enemyBoomerang.set_position_y(p1y);
        enemyNoBoomerang.set_position_y(p1y);
        assertEquals(p1y, enemyBoomerang.get_position_y());
        assertEquals(p1y, enemyNoBoomerang.get_position_y());
    }

    @RepeatedTest(5)
    void set_Pos2x_Test()
    {
        p2x = random.nextInt(0, 10);
        enemyBoomerang.set_position_x2(p2x);
        enemyNoBoomerang.set_position_x2(p2x);
        assertEquals(p2x, enemyBoomerang.get_position_x2());
        assertEquals(p2x, enemyNoBoomerang.get_position_x2());
    }

    @RepeatedTest(5)
    void set_Pos2y_Test()
    {
        p2y = random.nextInt(0, 10);
        enemyBoomerang.set_position_y2(p2y);
        enemyNoBoomerang.set_position_y2(p2y);
        assertEquals(p2y, enemyBoomerang.get_position_y2());
        assertEquals(p2y, enemyNoBoomerang.get_position_y2());
    }

    @RepeatedTest(5)
    void set_Speed_Test() {
        speed = random.nextInt(0, 10);
        enemyBoomerang.set_speed(speed);
        enemyNoBoomerang.set_speed(speed);
        assertEquals(speed, enemyBoomerang.get_speed());
        assertEquals(speed, enemyNoBoomerang.get_speed());
    }
}