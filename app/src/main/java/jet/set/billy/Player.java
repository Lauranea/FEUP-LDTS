package jet.set.billy;

import java.util.Set;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    Room room;
    String room_string;

    Boolean grounded = false;
    int direction = 0; // 1 = right, -1 = left
    int direction_level = 0;

    Boolean sprite_backwards = false;

    int sprite_delay = 0;

    BasicTextImage player_image;

    Boolean jumping = false;
    int jump_height = 0;
    Boolean fall_after_jump = false;

    LinkedList<BasicTextImage> sprites_left = new LinkedList<BasicTextImage>();
    LinkedList<BasicTextImage> sprites_right = new LinkedList<BasicTextImage>();

    void get_sprites()
    {
        String player_image_string =  "   xxxx   "+
                                      "   xxxx   "+
                                      "  xxxxxx  "+
                                      "   x xx   "+
                                      "  xxxxx   "+
                                      "   xxxx   "+
                                      "    xx    "+
                                      "   xxxx   "+
                                      "  xxx xx  "+
                                      "  xxx xx  "+
                                      "  xxx xx  "+
                                      "  xx xxx  "+
                                      "   xxxx   "+
                                      "    xx    "+
                                      "    xx    "+
                                      "   xxx    ";
        sprites_left.add(text_to_sprite(player_image_string));
        player_image_string = "   xxxx   "+
                              "   xxxx   "+
                              "  xxxxxx  "+
                              "   x xx   "+
                              "  xxxxx   "+
                              "   xxxx   "+
                              "    xx    "+
                              "   xxxx   "+
                              "  xxxxxx  "+
                              "  xxxxxx  "+
                              " xxx xxxx "+
                              " xx xxxxx "+
                              "   xxxx   "+
                              "  xx xxx  "+
                              "  xxx xx  "+
                              " xxx xxx  ";
        sprites_left.add(text_to_sprite(player_image_string));
        player_image_string = "   xxxx   "+
                              "   xxxx   "+
                              "  xxxxxx  "+
                              "   x xx   "+
                              "  xxxxx   "+
                              "   xxxx   "+
                              "    xx    "+
                              "   xxxx   "+
                              "  xxxxxx  "+
                              " xxxxxxxx "+
                              "xxxxxxxxxx"+
                              "xx xxxx xx"+
                              "   xxxx   "+
                              "x  xx xxx "+
                              "xxxx    xx"+
                              " xxx   xxx";
        sprites_left.add(text_to_sprite(player_image_string));
        player_image_string = "   xxxx   "+
                              "   xxxx   "+
                              "  xxxxxx  "+
                              "   xx x   "+
                              "   xxxxx  "+
                              "   xxxx   "+
                              "    xx    "+
                              "   xxxx   "+
                              "  xx xxx  "+
                              "  xx xxx  "+
                              "  xx xxx  "+
                              "  xxx xx  "+
                              "   xxxx   "+
                              "    xx    "+
                              "    xx    "+
                              "    xxx   ";
        sprites_right.add(text_to_sprite(player_image_string));
        player_image_string = "   xxxx   "+
                              "   xxxx   "+
                              "  xxxxxx  "+
                              "   xx x   "+
                              "   xxxxx  "+
                              "   xxxx   "+
                              "    xx    "+
                              "   xxxx   "+
                              "  xxxxxx  "+
                              "  xxxxxx  "+
                              " xxxx xxx "+
                              " xxxxx xx "+
                              "   xxxx   "+
                              "  xxx xx  "+
                              "  xx xxx  "+
                              "  xxx xxx ";
        sprites_right.add(text_to_sprite(player_image_string));
        player_image_string = "   xxxx   "+
                              "   xxxx   "+
                              "  xxxxxx  "+
                              "   xx x   "+
                              "   xxxxx  "+
                              "   xxxx   "+
                              "    xx    "+
                              "   xxxx   "+
                              "  xxxxxx  "+
                              " xxxxxxxx "+
                              "xxxxxxxxxx"+
                              "xx xxxx xx"+
                              "   xxxx   "+
                              " xxx xx  x"+
                              "xx    xxxx"+
                              "xxx   xxx ";
        sprites_right.add(text_to_sprite(player_image_string));
    }

    BasicTextImage text_to_sprite(String t)
    {
        BasicTextImage b = new BasicTextImage(sx, sy);
        for (int i = 0; i < sy; i++)
        {
            for (int j = 0; j < sx; j++)
            {
                if (t.charAt(i * sx + j) == 'x')
                {
                    b.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.WHITE)[0]);
                }
                else
                {
                    b.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', TextColor.ANSI.BLACK, TextColor.ANSI.BLACK)[0]);
                }
            }
        }
        return b;
    }

    public Player(int new_px, int new_py) throws Exception
    {
        px = new_px;
        py = new_py;

        get_sprites();

        player_image = sprites_left.get(0);
        
        room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/bathroom.txt").toURI()));
        room = new Room(room_string, 0, 0, "Bathroom");
    }

    public int get_size_x()
    {
        return sx;
    }
    public int get_size_y()
    {
        return sy;
    }

    public void draw(TextGraphics tg)
    {
        room.draw(tg);
        TerminalPosition playerPosition = new TerminalPosition(px, py);
        tg.drawImage(playerPosition, player_image, playerPosition.TOP_LEFT_CORNER, player_image.getSize());
    }

    void move_left()
    {
        px -= 1;
    }
    void move_right()
    {
        px += 1;
    }

    void move_left_air()
    {
        px -= 1;
    }
    void move_right_air()
    {
        px += 1;
    }

    void jump()
    {
        if (grounded)
        {
            grounded = false;
            jumping = true;
        }
    }

    void advance_sprite()
    {
        sprite_delay++;
        if (sprite_delay > 5)
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

    void collision()
    {
        for (int i = 0; i < 20; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                if (room_string.charAt(i + j * 21) == 'x')
                {
                    if (((px > i * 10 - 1 && px < i * 10 + 11) || (px + sx > i * 10 - 1 && px + sx < i * 10 + 11)) && py + sy == j * 10)
                    {
                        grounded = true;
                        jumping = false;
                    }
                    else if (((px > i * 10 - 1 && px < i * 10 + 11) || (px + sx > i * 10 - 1 && px + sx < i * 10 + 11)) && py == j * 10 + 10)
                    {
                        jumping = false;
                    }
                    if ((px == i * 10 + 10 || px == i * 10 + 9) && ((py + 5 > j * 10 && py + 5 <= j * 10 + 10) || (py + sy > j * 10 && py + sy <= j * 10 + 10) || (py > j * 10 && py <= j * 10 + 10)))
                    {
                        px++;
                    }
                    else if ((px + sx == i * 10 || px + sx == i * 10 + 1) && ((py + 5 > j * 10 && py+ 5 <= j * 10 + 10) || (py + sy > j * 10 && py + sy <= j * 10 + 10) || (py > j * 10 && py <= j * 10 + 10)))
                    {
                        px--;
                    }
                }
            }
        }
    }

    public void handle_input(Set<Character> pressedKeys)
    {
        if (grounded)
        {
            fall_after_jump = false;
            jump_height = 0;
            direction = 0;
            if (pressedKeys.contains('a'))
            {
                move_left();
                advance_sprite();
                player_image = sprites_left.get(direction_level);
                direction = -1;
            }
            if (pressedKeys.contains('d'))
            {
                move_right();
                advance_sprite();
                player_image = sprites_right.get(direction_level);
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
                if (jump_height < 25)
                {
                    py--;
                    jump_height++;
                }
                else
                {
                    jumping = false;
                }
            }
            else
            {
                py++;
            }
            if (fall_after_jump)
            {
                if (direction == -1)
                {
                    move_left_air();
                    advance_sprite();
                    player_image = sprites_left.get(direction_level);
                }
                else if (direction == 1)
                {
                    move_right_air();
                    advance_sprite();
                    player_image = sprites_right.get(direction_level);
                }
            }
        }
        grounded = false;
        collision();
    }
}