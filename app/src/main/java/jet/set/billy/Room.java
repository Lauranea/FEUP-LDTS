package jet.set.billy;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Room
{
    String name;
    String room_layout;
    int coord1, coord2;

    BasicTextImage wall;
    BasicTextImage platform;
    BasicTextImage ladder;
    BasicTextImage stairs_left;
    BasicTextImage stairs_right;

    public int get_coord1()
    {
        return coord1;
    }
    public int get_coord2()
    {
        return coord2;
    }
    
    BasicTextImage text_to_sprite(String t, TextColor color_x, TextColor color_y, TextColor color_z)
    {
        BasicTextImage b = new BasicTextImage(10, 10);
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (t.charAt(i * 11 + j) == 'x')
                {
                    b.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', color_x, color_x)[0]);
                }
                else if (t.charAt(i * 11 + j) == 'y')
                {
                    b.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', color_y, color_y)[0]);
                }
                else if (t.charAt(i * 11 + j) == 'z')
                {
                    b.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', color_z, color_z)[0]);
                }
            }
        }
        return b;
    }

    public Room(String nroom_layout, int nc1, int nc2, String nname) throws Exception
    {
        room_layout = nroom_layout;
        coord1 = nc1;
        coord2 = nc2;
        name = nname;

        wall = new BasicTextImage(10, 10);
        wall.setAll(TextCharacter.fromCharacter(' ', TextColor.ANSI.RED, TextColor.ANSI.BLUE)[0]);

        String platform_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/platform.txt").toURI()));
        platform = text_to_sprite(platform_string, TextColor.ANSI.GREEN, null, null);

        String ladder_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/ladder.txt").toURI()));
        ladder = text_to_sprite(ladder_string, TextColor.Factory.fromString("#004040"), TextColor.Factory.fromString("#005050"), null);

        String stairs_left_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/stairs_left.txt").toURI()));
        stairs_left = text_to_sprite(stairs_left_string, TextColor.ANSI.WHITE, null, null);

        String stairs_right_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/stairs_right.txt").toURI()));
        stairs_right = text_to_sprite(stairs_right_string, TextColor.ANSI.WHITE, null, null);
    }

    public void draw(TextGraphics tg)
    {
        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                TerminalPosition imagePosition = new TerminalPosition(i*10, j*10);
                if (room_layout.charAt(i + j*26) == 'x')
                {
                    tg.drawImage(imagePosition, wall, imagePosition.TOP_LEFT_CORNER, wall.getSize());
                }
                else if (room_layout.charAt(i + j*26) == '-')
                {
                    tg.drawImage(imagePosition, platform, imagePosition.TOP_LEFT_CORNER, platform.getSize());
                }
                else if (room_layout.charAt(i + j*26) == 'H')
                {
                    tg.drawImage(imagePosition, ladder, imagePosition.TOP_LEFT_CORNER, ladder.getSize());
                }
                else if (room_layout.charAt(i + j*26) == 's')
                {
                    tg.drawImage(imagePosition, stairs_left, imagePosition.TOP_LEFT_CORNER, stairs_left.getSize());
                }
                else if (room_layout.charAt(i + j*26) == 'z')
                {
                    tg.drawImage(imagePosition, stairs_right, imagePosition.TOP_LEFT_CORNER, stairs_right.getSize());
                }
            }
        }
    }
}
