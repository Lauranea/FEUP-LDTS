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
    Player player;

    Room room;

    Viewer viewer;
    Controller controller;

    Map<Integer, Enemy> enemies = new HashMap<>();

    public GameState() throws Exception
    {
        String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/Bathroom.txt").toURI()));
        room_string = room_string.replaceAll("\r", "");
        room = new Room(room_string, 0, 1, "Bathroom");

        player = new Player(150, 100);

        viewer = new Viewer();
        controller = new Controller(room);
    }

    public void step(Gui gui) throws Exception
    {
        room = controller.get_room();

        controller.step(gui.get_pressedKeys(), player, room);
        viewer.step(gui, player, room);
    }
}
