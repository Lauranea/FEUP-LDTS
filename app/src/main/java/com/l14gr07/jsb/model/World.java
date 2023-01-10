package com.l14gr07.jsb.model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Vector;

import java.io.IOException;
import java.net.URISyntaxException;

public class World
{
    // Dá bastante trabalho fazer isto de forma dinâmica, para o nosso caso, acho que não vale a pena
    Vector<Vector<String>> rooms = new Vector<Vector<String>>(Arrays.asList
    (
        new Vector<String>(Arrays.asList("", "Heaven")),
        new Vector<String>(Arrays.asList("", "Another Brick in the Wall", "You Only Live Once", "Hard to Explain")),
        new Vector<String>(Arrays.asList("", "Stairway to Heaven", "", "Dream On")),
        new Vector<String>(Arrays.asList("Bedroom", "Corridor", "Bathroom", "Shine on You Crazy Diamond", "Purple Rain")),
        new Vector<String>(Arrays.asList("", "Kitchen", "Winery", "Cuckoo", "Love Will Tear Us Apart")),
        new Vector<String>(Arrays.asList("", "Cold Store", "Basement", "Chapel")),
        new Vector<String>(Arrays.asList("", "Nomen Luni", "Secret Passage", "Secret Passage v2"))
    ));

    Vector<Vector<Vector<Integer>>> all_items = new Vector<Vector<Vector<Integer>>>();

    Room current_room;
    Player player;

    int coins_collected = 0;

    public World()  throws IOException, URISyntaxException
    {
        String r_name = "Bathroom";

        player = new Player(150, 100, 10);

        populate_items_vector();

        current_room = get_room(r_name);
    }

    public Player get_player()
    {
        return player;
    }

    public void collect_coin()
    {
        coins_collected++;
    }
    public int get_coins_collected()
    {
        return coins_collected;
    }
    public void set_coins_collected(int c)
    {
        coins_collected = c;
    }

    void populate_items_vector() throws IOException, URISyntaxException
    {
        for (int i = 0; i < rooms.size(); i++)
        {
            all_items.add(new Vector<Vector<Integer>>());
            for (int j = 0; j < rooms.get(i).size(); j++)
            {
                all_items.get(i).add(new Vector<Integer>());
                if (rooms.get(i).get(j) == "")
                {
                    continue;
                }
                String temp_room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+rooms.get(i).get(j)+".txt").toURI()));
                temp_room_string = temp_room_string.replaceAll("\r", "");
                int item_count = 0;
                while (temp_room_string.indexOf("*") != -1)
                {
                    temp_room_string = temp_room_string.substring(temp_room_string.indexOf("*") + 1);
                    all_items.get(i).get(j).add(item_count);
                    item_count++;
                }
            }
        }
    }

    public Vector<Integer> get_items()
    {
        return all_items.get(current_room.get_coord1()).get(current_room.get_coord2());
    }

    public void remove_item(int i)
    {
        all_items.get(current_room.get_coord1()).get(current_room.get_coord2()).remove(i);
    }

    public Room get_room(int c, int r) throws IOException, URISyntaxException
    {
        String room_name = rooms.get(c).get(r);
        String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+room_name+".txt").toURI()));
        room_string = room_string.replaceAll("\r", "");
        return new Room(room_string, c, r, room_name, all_items.get(c).get(r), coins_collected);
    }

    public Room get_room(String r_name) throws IOException, URISyntaxException
    {
        for (int i = 0; i < rooms.size(); i++)
        {
            for (int j = 0; j < rooms.get(i).size(); j++)
            {
                if (rooms.get(i).get(j) == r_name)
                {
                    String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+r_name+".txt").toURI()));
                    room_string = room_string.replaceAll("\r", "");
                    return new Room(room_string, i, j, r_name, all_items.get(i).get(j), coins_collected);
                }
            }
        }
        return get_room(0, 0);
    }

    public Room get_current_room()
    {
        return current_room;
    }
    public void set_current_room(Room room)
    {
        current_room = room;
    }
}
