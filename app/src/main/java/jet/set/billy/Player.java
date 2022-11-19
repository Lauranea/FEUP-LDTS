package jet.set.billy;

import java.util.Set;

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
    int sx = 6; // size x
    int sy = 16; // size y

    int px; // position x
    int py; // position y

    Boolean grounded = true;
    int direction = 0; // 1 = right, -1 = left

    BasicTextImage player_image;

    public BasicTextImage get_player_image()
    {
        String player_image_string_idle = " xxxx "+
                                          " xxxx "+
                                          "xxxxxx"+
                                          " x xx "+
                                          "xxxxx "+
                                          " xxxx "+
                                          "  xx  "+
                                          " xxxx "+
                                          "xxx xx"+
                                          "xxx xx"+
                                          "xxx xx"+
                                          "xx xxx"+
                                          " xxxx "+
                                          "  xx  "+
                                          "  xx  "+
                                          " xxx  ";
        
        player_image = new BasicTextImage(sx, sy);
        for (int i = 0; i < sy; i++)
        {
            for (int j = 0; j < sx; j++)
            {
                if (player_image_string_idle.charAt(i * sx + j) == 'x')
                {
                    player_image.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.WHITE)[0]);
                }
                else
                {
                    player_image.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)[0]);
                }
            }
        }

        return player_image;
    }

    public Player(int new_px, int new_py)
    {
        px = new_px;
        py = new_py;
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
        player_image = get_player_image();
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

    public void handle_input(Set<Character> pressedKeys)
    {
        
        direction = 0;
        if (grounded)
        {
            if (pressedKeys.contains('a'))
            {
                move_left();
                direction = -1;
            }
            if (pressedKeys.contains('d'))
            {
                move_right();
                direction = 1;
            }
        }
    }
}