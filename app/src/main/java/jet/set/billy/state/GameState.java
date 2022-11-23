package jet.set.billy.state;

import jet.set.billy.Gui;
import jet.set.billy.Player;
import jet.set.billy.Room;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import jet.set.billy.Enemy;
import jet.set.billy.Game;
import jet.set.billy.viewer.Viewer;
import jet.set.billy.controller.Controller;

public class GameState
{
    Viewer viewer = new Viewer();
    Controller controller = new Controller();

    Player player;

    Room room;

    Vector<Vector<String>> rooms = new Vector<Vector<String>>(Arrays.asList
    (
        new Vector<String>(Arrays.asList("Corridor", "Bathroom")),
        new Vector<String>(Arrays.asList("Kitchen"))
    ));

    Map<Integer, Enemy> enemies = new HashMap<>();

    public GameState() throws Exception
    {
        String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/Bathroom.txt").toURI()));
        room_string = room_string.replaceAll("\r", "");
        room = new Room(room_string, 0, 1, "Bathroom");

        player = new Player(150, 100, room);
    }

    public void step(Gui gui) throws Exception
    {
        check_if_dead();
        change_room();
        
        controller.step(gui.get_pressedKeys(), player, room, room.get_enemies());
        viewer.step(gui, player, room, room.get_enemies());
    }

    void change_room() throws Exception
    {
        if (player.get_position_x() < 0)
        {
            String room_name = rooms.get(room.get_coord1()).get(room.get_coord2() - 1);
            String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+room_name+".txt").toURI()));
            room_string = room_string.replaceAll("\r", "");
            room = new Room(room_string, room.get_coord1(), room.get_coord2() - 1, room_name);

            player.set_position_x(237);
            player.change_room(room);
        }
        else if (player.get_position_x() > 240)
        {
            String room_name = rooms.get(room.get_coord1()).get(room.get_coord2() + 1);
            String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+room_name+".txt").toURI()));
            room_string = room_string.replaceAll("\r", "");
            room = new Room(room_string, room.get_coord1(), room.get_coord2() + 1, room_name);
            
            player.set_position_x(3);
            player.change_room(room);
        }
        else if (player.get_position_y() > 115)
        {
            String room_name = rooms.get(room.get_coord1() + 1).get(room.get_coord2());
            String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+room_name+".txt").toURI()));
            room_string = room_string.replaceAll("\r", "");
            room = new Room(room_string, room.get_coord1() + 1, room.get_coord2(), room_name);
            
            player.set_position_y(-5);
            player.change_room(room);
        }
        else if (player.get_position_y() < -10)
        {
            String room_name = rooms.get(room.get_coord1() - 1).get(room.get_coord2());
            String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+room_name+".txt").toURI()));
            room_string = room_string.replaceAll("\r", "");
            room = new Room(room_string, room.get_coord1() - 1, room.get_coord2(), room_name);
            
            player.set_position_y(107);
            player.change_room(room);
        }
    }

    void die() throws Exception
    {
        room = new Room(room.get_room_string(), room.get_coord1(), room.get_coord2(), room.get_room_name());
        // player.die();
    }

    void check_if_dead() throws Exception
    {
        for(Enemy e : room.get_enemies())
        {
            if ((player.get_position_x() > e.get_position_x() && player.get_position_x() < e.get_position_x() + e.get_size_x()) || (player.get_position_x() + player.get_size_x() > e.get_position_x() && player.get_size_x() + player.get_position_x() < e.get_position_x() + e.get_size_x()))
            {
                if ((player.get_position_y() >= e.get_position_y() && player.get_position_y() <= e.get_position_y() + e.get_size_y()) || (player.get_position_y() + player.get_size_y() >= e.get_position_y() && player.get_size_y() + player.get_position_y() <= e.get_position_y() + e.get_size_y()))
                {
                    die();
                }
            }
        }
    }
}
