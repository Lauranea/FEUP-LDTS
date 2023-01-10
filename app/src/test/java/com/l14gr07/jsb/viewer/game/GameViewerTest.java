package com.l14gr07.jsb.viewer.game;

import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.model.*;
import com.l14gr07.jsb.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

class GameViewerTest {
    GameViewer gameViewer;
    EnemyViewer enemyViewer;
    RoomViewer roomViewer;
    PlayerViewer playerViewer;
    ItemViewer itemViewer;
    Gui gui;
    World world;
    Enemy enemy;
    Item item;
    Room room;
    @BeforeEach
    void startup()  throws IOException, URISyntaxException
    {
        gui = Mockito.mock(Gui.class);
        enemyViewer = Mockito.mock(EnemyViewer.class);
        roomViewer = Mockito.mock(RoomViewer.class);
        playerViewer = Mockito.mock(PlayerViewer.class);
        itemViewer = Mockito.mock(ItemViewer.class);
        world = Mockito.mock(World.class);
        enemy = Mockito.mock(Enemy.class);
        item = Mockito.mock(Item.class);
        room = Mockito.mock(Room.class);
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);
        Vector<Item> items = new Vector<>();
        items.add(item);
        Mockito.when(world.get_current_room()).thenReturn(room);
        Mockito.when(room.get_enemies()).thenReturn(enemies);
        Mockito.when(room.get_items()).thenReturn(items);
        gameViewer = new GameViewer(world);
        gameViewer.roomViewer = roomViewer;
        gameViewer.enemyViewer = enemyViewer;
        gameViewer.playerViewer = playerViewer;
        gameViewer.itemViewer = itemViewer;
    }
    @Test
    void gameViewerStepRoomTest()  throws IOException, URISyntaxException
    {
        gameViewer.step(gui);
        Mockito.verify(roomViewer, Mockito.times(1)).draw(gui, world.get_current_room());
        Mockito.verify(gui, Mockito.times(1)).drawHUD(world.get_player(), world);
    }
    @Test
    void gameViewerStepEnemyTest()  throws IOException, URISyntaxException
    {
        gameViewer.step(gui);
        Mockito.verify(enemyViewer, Mockito.times(1)).draw(gui, enemy);
        Mockito.verify(gui, Mockito.times(1)).drawHUD(world.get_player(), world);
    }
    @Test
    void gameViewerStepItemTest()  throws IOException, URISyntaxException
    {
        gameViewer.step(gui);
        Mockito.verify(itemViewer, Mockito.times(1)).draw(gui, item);
        Mockito.verify(gui, Mockito.times(1)).drawHUD(world.get_player(), world);
    }
    @Test
    void gameViewerStepPlayerTest()  throws IOException, URISyntaxException
    {
        gameViewer.step(gui);
        Mockito.verify(playerViewer, Mockito.times(1)).draw(gui, world.get_player());
        Mockito.verify(gui, Mockito.times(1)).drawHUD(world.get_player(), world);
    }
}