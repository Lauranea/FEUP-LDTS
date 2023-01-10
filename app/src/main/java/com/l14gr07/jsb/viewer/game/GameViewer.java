package com.l14gr07.jsb.viewer.game;

import java.io.IOException;
import java.net.URISyntaxException;

import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.model.Enemy;
import com.l14gr07.jsb.model.Player;
import com.l14gr07.jsb.model.World;
import com.l14gr07.jsb.viewer.Viewer;
import com.l14gr07.jsb.model.Item;

public class GameViewer extends Viewer<World>
{
    EnemyViewer enemyViewer = new EnemyViewer();
    RoomViewer roomViewer = new RoomViewer();
    PlayerViewer playerViewer = new PlayerViewer();
    ItemViewer itemViewer = new ItemViewer();

    public GameViewer(World model)
    {
        super(model);
    }
    

    public void step(Gui gui) throws IOException, URISyntaxException
    {
        roomViewer.draw(gui, getModel().get_current_room());
        for (Enemy enemy : getModel().get_current_room().get_enemies())
        {
            enemyViewer.draw(gui, enemy);
        }
        for (Item item : getModel().get_current_room().get_items())
        {
            itemViewer.draw(gui, item);
        }
        
        playerViewer.draw(gui, getModel().get_player());

        gui.drawHUD(getModel().get_player(), getModel());
    }   
}
