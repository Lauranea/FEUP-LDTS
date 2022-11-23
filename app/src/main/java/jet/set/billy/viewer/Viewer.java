package jet.set.billy.viewer;

import java.util.List;

import com.googlecode.lanterna.graphics.TextGraphics;

import jet.set.billy.Enemy;
import jet.set.billy.Player;
import jet.set.billy.Room;
import jet.set.billy.Gui;
import jet.set.billy.viewer.game.EnemyViewer;
import jet.set.billy.viewer.game.PlayerViewer;
import jet.set.billy.viewer.game.RoomViewer;

public class Viewer
{
    EnemyViewer enemyViewer = new EnemyViewer();
    RoomViewer roomViewer = new RoomViewer();
    PlayerViewer playerViewer = new PlayerViewer();

    public void step(Gui gui, Player player, Room room, List<Enemy> enemies) throws Exception
    {
        gui.clear();
        roomViewer.draw(gui, room);
        enemyViewer.draw(gui, enemies);
        playerViewer.draw(gui, player);
        gui.refresh();
    }    
}
