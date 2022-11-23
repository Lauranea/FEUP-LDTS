package jet.set.billy.controller;

import java.util.Set;
import java.util.List;

import jet.set.billy.Player;
import jet.set.billy.Room;
import jet.set.billy.controller.game.EnemyController;
import jet.set.billy.controller.game.GameController;
import jet.set.billy.controller.game.PlayerController;
import jet.set.billy.Enemy;

public class Controller
{
    GameController gameController;

    public Controller(Room room)
    {
        gameController = new GameController(room);
    }

    public void step(Set<Character> pressedKeys, Player player, Room room) throws Exception
    {
        gameController.step(pressedKeys, player, room);
    }

    public Room get_room()
    {
        return gameController.get_room();
    }
}
