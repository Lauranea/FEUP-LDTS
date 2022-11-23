package jet.set.billy;

import java.util.Set;
import java.util.Vector;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

import javax.lang.model.util.ElementScanner14;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class Player
{
    int sx = 10; // size x
    int sy = 16; // size y

    int px; // position x
    int py; // position y

    int safe_px;
    int safe_py;

    Room room;

    LinkedList<String> sprites_left = new LinkedList<String>();
    LinkedList<String> sprites_right = new LinkedList<String>();

    String current_sprite;

    void get_sprites() throws Exception
    {
        String player_image_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("player/left_1.txt").toURI()));
        player_image_string = player_image_string.replaceAll("\r", "");
        sprites_left.add(player_image_string);
        player_image_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("player/left_2.txt").toURI()));
        player_image_string = player_image_string.replaceAll("\r", "");
        sprites_left.add(player_image_string);
        player_image_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("player/left_3.txt").toURI()));
        player_image_string = player_image_string.replaceAll("\r", "");
        sprites_left.add(player_image_string);
        player_image_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("player/right_1.txt").toURI()));
        player_image_string = player_image_string.replaceAll("\r", "");
        sprites_right.add(player_image_string);
        player_image_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("player/right_2.txt").toURI()));
        player_image_string = player_image_string.replaceAll("\r", "");
        sprites_right.add(player_image_string);
        player_image_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("player/right_3.txt").toURI()));
        player_image_string = player_image_string.replaceAll("\r", "");
        sprites_right.add(player_image_string);
    }

    public Player(int new_px, int new_py, Room nroom) throws Exception
    {
        px = new_px;
        py = new_py;
        safe_px = px;
        safe_py = py;

        get_sprites();

        current_sprite = sprites_left.get(0);
        
        room = nroom;
    }

    public int get_size_x()
    {
        return sx;
    }
    public int get_size_y()
    {
        return sy;
    }

    public int get_position_x()
    {
        return px;
    }
    public int get_position_y()
    {
        return py;
    }

    public void set_position_x(int new_px)
    {
        px = new_px;
    }
    public void set_position_y(int new_py)
    {
        py = new_py;
    }

    public int get_safe_position_x()
    {
        return safe_px;
    }
    public int get_safe_position_y()
    {
        return safe_py;
    }

    public String get_current_sprite()
    {
        return current_sprite;
    }

    public void change_room(Room nroom)
    {
        room = nroom;
        safe_px = px;
        safe_py = py;
    }

    public void change_sprite_left(int direction_level)
    {
        current_sprite = sprites_left.get(direction_level);
    }
    public void change_sprite_right(int direction_level)
    {
        current_sprite = sprites_right.get(direction_level);
    }
}