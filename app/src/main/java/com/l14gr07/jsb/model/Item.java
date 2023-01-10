package com.l14gr07.jsb.model;

import java.io.IOException;
import java.net.URISyntaxException;

public class Item
{
    int px;
    int py;

    String sprite = "   xx   \n  xxxx  \n xxxxxx \nxxxxxxxx\nnxxxxxxxx\n xxxxxx \n  xxxx  \n   xx   ";

    public Item(int npx, int npy) throws IOException, URISyntaxException
    {
        px = npx;
        py = npy;
    }

    public int get_position_x()
    {
        return px;
    }
    public int get_position_y()
    {
        return py;
    }

    public String get_sprite_string()
    {
        return sprite;
    }
    public void set_sprite_string(String nsprite)
    {
        sprite = nsprite;
    }

    public void set_position_x(int npx)
    {
        px = npx;
    }
    public void set_position_y(int npy)
    {
        py = npy;
    }
}
