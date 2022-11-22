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

    int sprite_time = 0;

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
                    for (int ii = i; ii < 25; ii++)
                    {
                        Boolean found = false;
                        for (int jj = j; jj < 13; jj++)
                        {
                            if (room_layout.charAt(ii + jj * 26) == '2')
                            {
                                String enemyString = Files.readString(Paths.get(getClass().getClassLoader().getResource("enemies/murderous_block.txt").toURI()));
                                enemyString = enemyString.replaceAll("\r", "");
                                enemies.add(new Enemy(i*10, j*10, ii*10, jj*10, true, 1, enemyString, 10, 10));
                                found = true;
                                break;
                            }
                        }
                        if (found)
                        {
                            break;
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
                else if (room_layout.charAt(i + j*26) == 't')
                {
                    if (sprite_time < 2)
                    {
                        tg.drawImage(imagePosition, blocks.get("toilet_1"), imagePosition.TOP_LEFT_CORNER, blocks.get("toilet_1").getSize());
                    }
                    else if (sprite_time < 4)
                    {
                        tg.drawImage(imagePosition, blocks.get("toilet_2"), imagePosition.TOP_LEFT_CORNER, blocks.get("toilet_2").getSize());
                    }
                    else
                    {
                        tg.drawImage(imagePosition, blocks.get("toilet_3"), imagePosition.TOP_LEFT_CORNER, blocks.get("toilet_3").getSize());
                    }
                }
            }
        }
        sprite_time++;
        if (sprite_time > 5)
        {
            sprite_time = 0;
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
