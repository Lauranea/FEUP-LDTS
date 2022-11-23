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

    public Room(String nroom_layout, int nc1, int nc2, String nname) throws Exception
    {
        room_layout = nroom_layout;
        coord1 = nc1;
        coord2 = nc2;
        name = nname;

        initialize_enemies();
    }

    void initialize_enemies() throws Exception
    {
        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 13; j++)
            {
                if (room_layout.charAt(i + j * 26) == '1') // murderous block
                {
                    for (int ii = i; ii < 25; ii++)
                    {
                        Boolean found = false;
                        for (int jj = j; jj < 13; jj++)
                        {
                            if (room_layout.charAt(ii + jj * 26) == '2')
                            {
                                System.out.print(i+","+j+" "+ii+","+jj);
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
                else if (room_layout.charAt(i + j * 26) == '3') // arrow
                {
                    String enemyString = Files.readString(Paths.get(getClass().getClassLoader().getResource("enemies/arrow.txt").toURI()));
                    enemyString = enemyString.replaceAll("\r", "");
                    enemies.add(new Enemy(-20, j*10, 270, j*10, false, 2, enemyString, 10, 3));
                }
                else if (room_layout.charAt(i + j * 26) == '4') // arrow
                {
                    String enemyString = Files.readString(Paths.get(getClass().getClassLoader().getResource("enemies/arrow.txt").toURI()));
                    enemyString = enemyString.replaceAll("\r", "");
                    enemies.add(new Enemy(270, j*10, -20, j*10, false, 2, enemyString, 10, 3));
                }
            }
        }
    }
}
