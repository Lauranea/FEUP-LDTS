package jet.set.billy.viewer.game;

import java.util.List;

import jet.set.billy.Enemy;
import jet.set.billy.Gui;

public class EnemyViewer
{
    public void draw(Gui gui, List<Enemy> enemies)
    {
        gui.drawEnemies(enemies);
    }    
}
