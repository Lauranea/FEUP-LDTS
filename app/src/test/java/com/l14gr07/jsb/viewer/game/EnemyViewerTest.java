package com.l14gr07.jsb.viewer.game;

import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.model.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.net.URISyntaxException;

class EnemyViewerTest 
{
    EnemyViewer enemyViewer;
    Gui gui;
    Enemy enemy;

    @BeforeEach
    void startup()  throws IOException, URISyntaxException
    {
        enemyViewer = new EnemyViewer();
        gui = Mockito.mock(Gui.class);
        enemy = Mockito.mock(Enemy.class);
    }

    @Test
    void drawPlayer()  throws IOException, URISyntaxException
    {
        enemyViewer.draw(gui, enemy);

        Mockito.verify(gui, Mockito.times(1)).drawEnemy(enemy);
    }
}