package com.l14gr07.jsb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.googlecode.lanterna.graphics.BasicTextImage;
import com.l14gr07.jsb.Loader;
import com.l14gr07.jsb.model.enemies.Arrow_left;
import com.l14gr07.jsb.model.enemies.Arrow_right;
import com.l14gr07.jsb.model.enemies.Beeg_cactus;
import com.l14gr07.jsb.model.enemies.Ghast;
import com.l14gr07.jsb.model.enemies.Gota;
import com.l14gr07.jsb.model.enemies.Gota_purple;
import com.l14gr07.jsb.model.enemies.Guba;
import com.l14gr07.jsb.model.enemies.Mother;
import com.l14gr07.jsb.model.enemies.Shell;
import com.l14gr07.jsb.model.enemies.Shmol_cactus;

import java.io.IOException;
import java.net.URISyntaxException;

public class Room
{
    String name;
    String room_layout;
    int coord1, coord2;

    int sprite_time = 0;

    List<Enemy> enemies = new ArrayList<Enemy>();
    Vector<Item> items = new Vector<Item>();
    Vector<Integer> all_available_items = new Vector<Integer>();

    BasicTextImage room_name_sprite;
    int room_name_sprite_position = 5;

    int coins_collected;
    Boolean won = false;

    public void set_won(Boolean nwon)
    {
        won = nwon;
    }
    public Boolean get_won()
    {
        return won;
    }

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
    public void set_room_string(String nroom_layout)
    {
        room_layout = nroom_layout;
    }

    public String get_room_name()
    {
        return name;
    }

    public List<Enemy> get_enemies()
    {
        return enemies;
    }
    public Vector<Item> get_items()
    {
        return items;
    }

    public int get_room_name_sprite_position()
    {
        return room_name_sprite_position;
    }

    public Room(String nroom_layout, int nc1, int nc2, String nname, Vector<Integer> nall_available_items, int ncoins_collected)  throws IOException, URISyntaxException
    {
        room_layout = nroom_layout;
        coord1 = nc1;
        coord2 = nc2;
        name = nname;
        all_available_items = nall_available_items;
        coins_collected = ncoins_collected;

        room_name_sprite = Loader.loadImage(1, "fonts/rooms/"+name+".txt");

        room_name_sprite_position = ((250 - room_name_sprite.getSize().getColumns()) / 2);
        
        initialize_enemies_items();
    }

    public BasicTextImage get_room_name_sprite()
    {
        return room_name_sprite;
    }

    public void remove_item(Item item)
    {
        items.remove(item);
    }

    void initialize_enemies_items() throws IOException, URISyntaxException
    {
        int current_item = 0;
        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 13; j++)
            {
                if (room_layout.charAt(i + j * 26) == '*') // item
                {
                    if (all_available_items.contains(current_item))
                    {
                        items.add(new Item(i*10+2, j*10+2));
                    }    
                    current_item++;
                }
                else if (room_layout.charAt(i + j * 26) == '1') // guba
                {
                    for (int ii = i; ii < 25; ii++)
                    {
                        if (room_layout.charAt(ii + j * 26) == '2')
                        {
                            enemies.add(new Guba(i*10, ii*10, j*10-1));
                            break;
                        }
                    }
                }
                else if (room_layout.charAt(i + j * 26) == '3') // arrow right
                {
                    enemies.add(new Arrow_right(-i*10 - 20, 270, j*10 + 2));
                }
                else if (room_layout.charAt(i + j * 26) == '4') // arrow left
                {
                    enemies.add(new Arrow_left(i*10 + 270, -20, j*10 + 2));
                }
                else if (room_layout.charAt(i + j * 26) == '5') // shmol cactus
                {
                    enemies.add(new Shmol_cactus(i*10, j*10+5));
                }
                else if (room_layout.charAt(i + j * 26) == '6') // beeg cactus
                {
                    enemies.add(new Beeg_cactus(i*10, j*10-3));
                }
                else if (room_layout.charAt(i + j * 26) == '7') // shell
                {
                    for (int ii = i; ii < 25; ii++)
                    {
                        if (room_layout.charAt(ii + j * 26) == '8')
                        {
                            enemies.add(new Shell(i*10, ii*10, j*10+4));
                            break;
                        }
                    }
                }
                else if (room_layout.charAt(i + j * 26) == '9') // ghast
                {
                    for (int jj = j; jj < 25; jj++)
                    {
                        if (room_layout.charAt(jj * 26 + i) == '0')
                        {
                            enemies.add(new Ghast(i*10, j*10+2, jj*10-1));
                            break;
                        }
                    }
                }
                else if (room_layout.charAt(i + j * 26) == 'j') // gota azul
                {
                    enemies.add(new Gota(i*10 + 2, -j*10, 120));
                }
                else if (room_layout.charAt(i + j * 26) == 'g') // gota purple
                {
                    enemies.add(new Gota_purple(i*10 + 2, -j*10, 120));
                }
                else if (room_layout.charAt(i + j * 26) == 'm' && coins_collected < 100)
                {
                    enemies.add(new Mother(i*10 + 4, j*10 - 6));
                }
            }
        }
    }
}
