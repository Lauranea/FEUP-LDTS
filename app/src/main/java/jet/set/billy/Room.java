package jet.set.billy;

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

    BasicTextImage outer_wall;
    BasicTextImage wall;

    public Room(String nroom_layout, int ncollumn, int nrow, String nname)
    {
        room_layout = nroom_layout;
        collumn = ncollumn;
        row = nrow;
        name = nname;

        outer_wall = new BasicTextImage(10, 10);
        outer_wall.setAll(TextCharacter.fromCharacter(' ', TextColor.ANSI.RED, TextColor.ANSI.RED)[0]);

        wall = new BasicTextImage(10, 10);
        wall.setAll(TextCharacter.fromCharacter(' ', TextColor.ANSI.RED, TextColor.ANSI.BLUE)[0]);
    }

    int d = 0;
    public void draw(TextGraphics tg)
    {
        for (int i = 0; i < 30; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                TerminalPosition imagePosition = new TerminalPosition(i*10, j*10);
                if (room_layout.charAt(i + j*31) == '#')
                {
                    tg.drawImage(imagePosition, outer_wall, imagePosition.TOP_LEFT_CORNER, outer_wall.getSize());
                }
                else if (room_layout.charAt(i + j*31) == 'x')
                {
                    tg.drawImage(imagePosition, wall, imagePosition.TOP_LEFT_CORNER, wall.getSize());
                }
            }
        }
    }
}
