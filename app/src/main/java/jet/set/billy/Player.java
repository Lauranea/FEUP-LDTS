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

    String room_string;
    Room room;

    Boolean grounded = false;
    int direction = 0; // 1 = right, -1 = left
    int direction_level = 0;

    Boolean sprite_backwards = false;

    int sprite_delay = 0;
    String current_sprite;

    Boolean jumping = false;
    int jump_height = 0;
    Boolean fall_after_jump = false;
    int jump_index = 5;
    int close_to_fall = 5;

    Vector<Integer> jump = new Vector<Integer>(Arrays.asList
    (
        3, 3, 3, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1
    ));

    LinkedList<String> sprites_left = new LinkedList<String>();
    LinkedList<String> sprites_right = new LinkedList<String>();

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

    public Player(int new_px, int new_py, String cur_room) throws Exception
    {
        px = new_px;
        py = new_py;
        safe_px = px;
        safe_py = py;

        get_sprites();

        current_sprite = sprites_left.get(0);
        
        room_string = cur_room;
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

    public void change_room(String new_room_string)
    {
        room_string = new_room_string;
        safe_px = px;
        safe_py = py;
    }

    public void draw(TextGraphics tg)
    {
        for (int i = 0; i < sy; i++)
        {
            for (int j = 0; j < sx; j++)
            {
                if (current_sprite.charAt(i * (sx + 1) + j) == 'x')
                {
                    tg.setCharacter(px + j, py + i,  TextCharacter.fromCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.WHITE)[0]);
                }
                else if (current_sprite.charAt(i * (sx + 1) + j) == '-')
                {
                    tg.setCharacter(px + j, py + i,  TextCharacter.fromCharacter(' ', TextColor.ANSI.BLACK, TextColor.ANSI.BLACK)[0]);
                }
            }
        }
    }

    void move_left()
    {
        px -= 1;
    }
    void move_right()
    {
        px += 1;
    }

    void jump()
    {
        if (grounded)
        {
            jump_index = 0;
            grounded = false;
            jumping = true;
        }
    }

    public void die()
    {
        px = safe_px;
        py = safe_py;
        jumping = false;
        fall_after_jump = false;
        grounded = true;
    }

    void advance_sprite()
    {
        sprite_delay++;
        if (sprite_delay > 3)
        {
            if (!sprite_backwards)
            {
                direction_level++;
                if (direction_level > 2)
                {
                    direction_level=1;
                    sprite_backwards = true;
                }
            }
            else
            {
                direction_level--;
                if (direction_level < 0)
                {
                    direction_level=1;
                    sprite_backwards = false;
                }
            }
            sprite_delay = 0;
        }
    }

    void move_y(Boolean j)
    {
        if (jump_index > 14)
        {
            jump_index = 14;
        }
        else if (jump_index < 0)
        {
            jump_index = 0;
            close_to_fall--;
        }

        if (close_to_fall < 0)
        {
            fall_after_jump = false;
        }
        
        if (j)
        {
            py -= jump.get(jump_index);
            jump_index++;
        }
        else
        {
            py+= jump.get(jump_index);
            jump_index--;
        }
    }

    Boolean collision_ground(int i, int j)
    {
        if (!jumping && ((px > i * 10 - 1 && px < i * 10 + 11) || (px + sx > i * 10 - 1 && px + sx < i * 10 + 11)) && (py + sy >= j * 10 && py + sy < j * 10 + 3))
        {
            if (py + sy > j * 10)
            {
                py = j * 10 - sy;
            }
            grounded = true;
            return true;
        }
        return false;
    }

    Boolean collision_ceiling(int i, int j)
    {
        if (((px >= i * 10 && px < i * 10 + 10) || (px + sx > i * 10 && px + sx <= i * 10 + 10)) && (py <= j * 10 + 10 && py > j * 10 + 5))
        {
            if (jump_index < 10)
            {
                jump_index = 10;
            }
            if (py < j * 10 + 10)
            {
                py = j * 10 + 10;
            }
            jumping = false;
            return true;
        }
        return false;
    }

    Boolean collision_left(int i, int j)
    {
        if ((px == i * 10 + 10 || px == i * 10 + 9) && ((py + 6 > j * 10 && py + 6 <= j * 10 + 10) || (py + sy > j * 10 && py + sy <= j * 10 + 10) || (py > j * 10 && py <= j * 10 + 10)))
        {
            px++;
            return true;
        }
        return false;
    }

    Boolean collision_right(int i, int j)
    {
        if ((px + sx == i * 10 || px + sx == i * 10 + 1) && ((py + 6 > j * 10 && py + 6 <= j * 10 + 10) || (py + sy > j * 10 && py + sy <= j * 10 + 10) || (py > j * 10 && py <= j * 10 + 10)))
        {
            px--;
            return true;
        }
        return false;
    }

    Boolean collision_stairs_left(int i, int j)
    {
        if ((!jumping && !fall_after_jump || direction == 0) && ((px > i * 10 - 1 && px < i * 10 + 11) || (px + sx > i * 10 - 1 && px + sx < i * 10 + 11)))
        {
            for (int k = 0; k < 10; k ++)
            {
                if (px == i * 10 + 10 - k)
                {
                    if (py + sy >= j * 10 + 10 - k && py + sy <= j * 10 + 12 - k)
                    {
                        py--;
                        grounded = true;
                        return true;
                    }
                    else if (py + sy == j * 10 + 10 - k - 2)
                    {
                        py++;
                        grounded = true;
                        jumping = false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    Boolean collision_stairs_right(int i, int j)
    {
        if ((!jumping && !fall_after_jump || direction == 0) && ((px > i * 10 - 1 && px < i * 10 + 11) || (px + sx > i * 10 - 1 && px + sx < i * 10 + 11)))
        {
            for (int k = 0; k < 10; k ++)
            {
                if (px + sx == i * 10 + k)
                {
                    if (py + sy >= j * 10 + 10 - k && py + sy <= j * 10 + 12 - k)
                    {
                        py--;
                        grounded = true;
                        jumping = false;
                    }
                    else if (py + sy == j * 10 + 10 - k - 2 )
                    {
                        py++;
                        grounded = true;
                        jumping = false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    void collision()
    {

        for (int i = 0 ; i * 10 <= px + sx + 10; i++)
        {
            if (i * 10 + 10 < px)
            {
                continue;
            }
            for (int j = 0; j * 10 <= py + sy + 10; j++)
            {
                if (j * 10 + 10 < py)
                {
                    continue;
                }
                if (room_string.charAt(i + j * 26) == 'x')
                {
                    if (!collision_ground(i, j))
                    {
                        collision_ceiling(i, j);
                    }
                    if (!collision_left(i, j))
                    {
                        collision_right(i, j);
                    }
                }
                else if (room_string.charAt(i + j * 26) == '-' || room_string.charAt(i + j * 26) == 'H')
                {
                    collision_ground(i, j);
                }
                else if (room_string.charAt(i + j * 26) == 's')
                {
                    collision_stairs_left(i, j);
                }
                else if (room_string.charAt(i + j * 26) == 'z')
                {
                    collision_stairs_right(i, j);
                }
            }
        }
    }

    public void handle_input(Set<Character> pressedKeys)
    {
        if (grounded)
        {
            close_to_fall = 5;
            jump_index = 15;
            fall_after_jump = false;
            jump_height = 0;
            direction = 0;
            if (pressedKeys.contains('a'))
            {
                move_left();
                advance_sprite();
                current_sprite = sprites_left.get(direction_level);
                direction = -1;
            }
            if (pressedKeys.contains('d'))
            {
                move_right();
                advance_sprite();
                current_sprite = sprites_right.get(direction_level);
                direction = 1;
            }
            if ((pressedKeys.contains('w')))
            {
                jump();
                return;
            }
        }
        else
        {
            if (jumping)
            {
                fall_after_jump = true;
                if (jump_height < 15)
                {
                    move_y(true);
                    jump_height++;
                }
                else
                {
                    jumping = false;
                }
            }
            else
            {
                move_y(false);
            }
            if (fall_after_jump)
            {
                if (direction == -1)
                {
                    move_left();
                    advance_sprite();
                    current_sprite = sprites_left.get(direction_level);
                }
                else if (direction == 1)
                {
                    move_right();
                    advance_sprite();
                    current_sprite = sprites_right.get(direction_level);
                }
            }
        }
        grounded = false;
        collision();
    }
}