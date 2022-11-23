package jet.set.billy.controller.game;

import java.util.Set;
import java.util.List;
import java.util.Vector;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import jet.set.billy.Player;
import jet.set.billy.Room;
import jet.set.billy.controller.game.EnemyController;
import jet.set.billy.controller.game.PlayerController;
import jet.set.billy.Enemy;

public class GameController 
{
    PlayerController playerController = new PlayerController();
    EnemyController enemyController = new EnemyController();
    
    Vector<Vector<String>> rooms = new Vector<Vector<String>>(Arrays.asList
    (
        new Vector<String>(Arrays.asList("Corridor", "Bathroom")),
        new Vector<String>(Arrays.asList("Kitchen"))
    ));

    public void step(Set<Character> pressedKeys, Player player, Room room) throws Exception
    {
        check_if_dead(player, room);
        change_room(player, room);

        playerController.step(pressedKeys, player, room);
        for (Enemy enemy : room.get_enemies())
        {
            enemyController.step(enemy);
        }
    }  

    void die(Player player, Room room) throws Exception
    {
        room = new Room(room.get_room_string(), room.get_coord1(), room.get_coord2(), room.get_room_name());
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

    void change_room(Player player, Room room) throws Exception
    {
        if (player.get_position_x() < 0)
        {
            String room_name = rooms.get(room.get_coord1()).get(room.get_coord2() - 1);
            String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+room_name+".txt").toURI()));
            room_string = room_string.replaceAll("\r", "");
            room = new Room(room_string, room.get_coord1(), room.get_coord2() - 1, room_name);

            player.set_position_x(237);
            player.set_safe_position_x(player.get_position_x());
            player.set_safe_position_y(player.get_position_y());
        }
        else if (player.get_position_x() > 240)
        {
            String room_name = rooms.get(room.get_coord1()).get(room.get_coord2() + 1);
            String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+room_name+".txt").toURI()));
            room_string = room_string.replaceAll("\r", "");
            room = new Room(room_string, room.get_coord1(), room.get_coord2() + 1, room_name);
            
            player.set_position_x(3);
            player.set_safe_position_x(player.get_position_x());
            player.set_safe_position_y(player.get_position_y());
        }
        else if (player.get_position_y() > 115)
        {
            String room_name = rooms.get(room.get_coord1() + 1).get(room.get_coord2());
            String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+room_name+".txt").toURI()));
            room_string = room_string.replaceAll("\r", "");
            room = new Room(room_string, room.get_coord1() + 1, room.get_coord2(), room_name);
            
            player.set_position_y(-5);
            player.set_safe_position_x(player.get_position_x());
            player.set_safe_position_y(player.get_position_y());
        }
        else if (player.get_position_y() < -10)
        {
            String room_name = rooms.get(room.get_coord1() - 1).get(room.get_coord2());
            String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+room_name+".txt").toURI()));
            room_string = room_string.replaceAll("\r", "");
            room = new Room(room_string, room.get_coord1() - 1, room.get_coord2(), room_name);
            
            player.set_position_y(107);
            player.set_safe_position_x(player.get_position_x());
            player.set_safe_position_y(player.get_position_y());
        }
    }   
}
