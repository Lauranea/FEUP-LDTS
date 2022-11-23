package jet.set.billy.controller;

import java.util.Set;
import java.util.List;

import jet.set.billy.Player;
import jet.set.billy.Room;
import jet.set.billy.controller.game.EnemyController;
import jet.set.billy.controller.game.PlayerController;
import jet.set.billy.Enemy;

public class Controller
{
    PlayerController playerController = new PlayerController();
    EnemyController enemyController = new EnemyController();

    public void step(Set<Character> pressedKeys, Player player, Room room) throws Exception
    {
        playerController.step(pressedKeys, player, room);
        for (Enemy enemy : room.get_enemies())
        {
            enemyController.step(enemy);
        }
    }  
}
