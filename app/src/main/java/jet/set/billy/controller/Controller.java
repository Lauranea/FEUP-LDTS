package jet.set.billy.controller;

import java.util.Set;
import java.util.List;

import jet.set.billy.Player;
import jet.set.billy.Room;
import jet.set.billy.controller.game.EnemyController;
import jet.set.billy.controller.game.PlayerController;
import jet.set.billy.controller.game.WorldController;
import jet.set.billy.Enemy;

public class Controller
{
    PlayerController playerController = new PlayerController();
    EnemyController enemyController = new EnemyController();
    WorldController worldController;

    public void step(Set<Character> pressedKeys, Player player, Room room) throws Exception
    {
        check_if_dead(player, room);
        worldController.change_room(player, room);

        playerController.step(pressedKeys, player, room);
        for (Enemy enemy : room.get_enemies())
        {
            enemyController.step(enemy);
        }
    }

    public Room get_room()
    {
        return worldController.get_room();
    }

    public Controller(Room room)
    {
        worldController = new WorldController(room);
    }
 

    void die(Player player, Room room) throws Exception
    {
        worldController.set_room(new Room(room.get_room_string(), room.get_coord1(), room.get_coord2(), room.get_room_name()));
        playerController.die(player);
    }

    void check_if_dead(Player player, Room room) throws Exception
    {
        for(Enemy e : room.get_enemies())
        {
            if ((player.get_position_x() > e.get_position_x() && player.get_position_x() < e.get_position_x() + e.get_size_x()) || (player.get_position_x() + player.get_size_x() > e.get_position_x() && player.get_size_x() + player.get_position_x() < e.get_position_x() + e.get_size_x()))
            {
                if ((player.get_position_y() >= e.get_position_y() && player.get_position_y() <= e.get_position_y() + e.get_size_y()) || (player.get_position_y() + player.get_size_y() >= e.get_position_y() && player.get_size_y() + player.get_position_y() <= e.get_position_y() + e.get_size_y()))
                {
                    die(player, room);
                }
            }
        }
    }
}
