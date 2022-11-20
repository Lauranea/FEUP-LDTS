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
    int collumn, row;

    BasicTextImage wall;
    BasicTextImage platform;
    BasicTextImage ladder;
    
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

    public Room(String nroom_layout, int ncollumn, int nrow, String nname) throws Exception
    {
        room_layout = nroom_layout;
        collumn = ncollumn;
        row = nrow;
        name = nname;

        wall = new BasicTextImage(10, 10);
        wall.setAll(TextCharacter.fromCharacter(' ', TextColor.ANSI.RED, TextColor.ANSI.BLUE)[0]);

        String platform_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("sprites/platform.txt").toURI()));
        platform = text_to_sprite(platform_string, TextColor.ANSI.GREEN, null, null);

        String ladder_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("sprites/ladder.txt").toURI()));
        ladder = text_to_sprite(ladder_string, TextColor.Factory.fromString("#222222"), TextColor.Factory.fromString("#402222"), null);
    }

    public String get_room_string()
    {
        return room_layout;
    }

    public void draw(TextGraphics tg)
    {
        for (int i = 0; i < 30; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                TerminalPosition imagePosition = new TerminalPosition(i*10, j*10);
                if (room_layout.charAt(i + j*31) == 'x')
                {
                    tg.drawImage(imagePosition, wall, imagePosition.TOP_LEFT_CORNER, wall.getSize());
                }
                else if (room_layout.charAt(i + j*31) == '-')
                {
                    tg.drawImage(imagePosition, platform, imagePosition.TOP_LEFT_CORNER, platform.getSize());
                }
                else if (room_layout.charAt(i + j*31) == 'H')
                {
                    tg.drawImage(imagePosition, ladder, imagePosition.TOP_LEFT_CORNER, ladder.getSize());
                }
            }
        }
    }
}
