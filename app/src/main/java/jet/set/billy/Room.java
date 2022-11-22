package jet.set.billy;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    Map<String, BasicTextImage> blocks;

    List<Enemy> enemies = new ArrayList<Enemy>();

    public int get_coord1()
    {
        return coord1;
    }
    public int get_coord2()
    {
        return coord2;
    }

    public String get_room_string()
    {
        return room_layout;
    }
    public String get_room_name()
    {
        return name;
    }

    public List<Enemy> get_enemies()
    {
        return enemies;
    }

    public Room(String nroom_layout, int nc1, int nc2, String nname, Map<String, BasicTextImage> nblocks) throws Exception
    {
        room_layout = nroom_layout;
        coord1 = nc1;
        coord2 = nc2;
        name = nname;

        blocks = nblocks;

        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 13; j++)
            {
                if (room_layout.charAt(i + j * 26) == '1')
                {
                    for (int ii = 0; ii < 25; ii++)
                    {
                        for (int jj = 0; jj < 13; jj++)
                        {
                            if (room_layout.charAt(ii + jj * 26) == '2')
                            {
                                System.out.println(i+","+j+","+ii+","+jj);
                                String enemyString = Files.readString(Paths.get(getClass().getClassLoader().getResource("enemies/murderous_block.txt").toURI()));
                                enemyString = enemyString.replaceAll("\r", "");
                                enemies.add(new Enemy(i*10, j*10, ii*10, jj*10, true, 1, enemyString, 10, 10));
                            }
                        }
                    }
                }
            }
        }
    }

    public void draw(TextGraphics tg)
    {
        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 13; j++)
            {
                TerminalPosition imagePosition = new TerminalPosition(i*10, j*10);
                if (room_layout.charAt(i + j*26) == 'x')
                {
                    tg.drawImage(imagePosition, blocks.get("wall"), imagePosition.TOP_LEFT_CORNER, blocks.get("wall").getSize());
                }
                else if (room_layout.charAt(i + j*26) == '-')
                {
                    tg.drawImage(imagePosition, blocks.get("platform"), imagePosition.TOP_LEFT_CORNER, blocks.get("platform").getSize());
                }
                else if (room_layout.charAt(i + j*26) == 'H')
                {
                    tg.drawImage(imagePosition, blocks.get("ladder"), imagePosition.TOP_LEFT_CORNER, blocks.get("ladder").getSize());
                }
                else if (room_layout.charAt(i + j*26) == 's')
                {
                    tg.drawImage(imagePosition, blocks.get("stairs_left"), imagePosition.TOP_LEFT_CORNER, blocks.get("stairs_left").getSize());
                }
                else if (room_layout.charAt(i + j*26) == 'z')
                {
                    tg.drawImage(imagePosition, blocks.get("stairs_right"), imagePosition.TOP_LEFT_CORNER, blocks.get("stairs_right").getSize());
                }
            }
        }
    }

    public void update(TextGraphics tg)
    {
        for (Enemy e : enemies)
        {
            e.move();
            e.draw(tg);
        }
    }
}
