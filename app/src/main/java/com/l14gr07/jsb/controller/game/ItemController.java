package com.l14gr07.jsb.controller.game;

import java.util.Vector;

import java.io.IOException;
import java.net.URISyntaxException;

import com.l14gr07.jsb.Loader;
import com.l14gr07.jsb.model.Item;

public class ItemController
{
    Vector<String> item_sprites = new Vector<String>();
    Boolean inverted = false;
    int cur = 0;

    public void update()
    {
        if (!inverted)
        {
            cur++;
            if (cur>7)
            {
                cur = 8;
                inverted = true;
            }
        }
        else
        {
            cur--;
            if (cur<0)
            {
                cur = 0;
                inverted = false;
            }
        }
    }
    
    // Pode não estar muito correto guardar as sprites dentro do controller, mas como todos os items têm a mesma sprite
    // a cada momento, esta solução é mais eficiente do que guardar as sprites em cada item.
    public ItemController()  throws IOException, URISyntaxException
    {
        item_sprites.add(Loader.get_string_from_file("items/amulet1.txt"));
        item_sprites.add(Loader.get_string_from_file("items/amulet2.txt"));
        item_sprites.add(Loader.get_string_from_file("items/amulet3.txt"));
    }

    public void step(Item e)
    {
        e.set_sprite_string(item_sprites.get(cur/3));
    }
}
