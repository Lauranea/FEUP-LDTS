package com.l14gr07.jsb.model;

import java.io.IOException;
import java.net.URISyntaxException;

import com.googlecode.lanterna.graphics.BasicTextImage;

import com.l14gr07.jsb.Loader;

public class Menu
{
    int selected = 1;

    Boolean s = false;
    int type_of_lives = 0;

    public Boolean please_quit = false;

    public BasicTextImage ten_lives;
    public BasicTextImage endless;
    public BasicTextImage titulo;
    public BasicTextImage start;
    public BasicTextImage credits;
    public BasicTextImage quit;

    public Menu() throws IOException, URISyntaxException
    {
        ten_lives = Loader.loadImage(2, "menu/10_lives.txt");
        endless = Loader.loadImage(2, "menu/endless.txt");
        titulo = Loader.loadImage(6, "menu/titulo.txt");
        start = Loader.loadImage(2, "menu/Start.txt");
        credits = Loader.loadImage(2, "menu/Credits.txt");
        quit = Loader.loadImage(2, "menu/Quit.txt");
    }

    public void move_up()
    {
        selected=1;
    }
    public void move_down()
    {
        selected=2;
    }
    public void go_back()
    {
        s = false;
        type_of_lives = 0;
        selected = 1;
    }

    public int get_lives()
    {
        if (type_of_lives == 1)
        {
            return 10;
        }
        return -1;
    }

    public Boolean select()
    {
        if (!s)
        {
            if (selected == 2)
            {
                please_quit = true;
                return false;
            }
            s = true;
        }
        else if (s)
        {
            type_of_lives = selected;
            return true;
        }

        return false;
    }

    public Boolean is_starting()
    {
        return s;
    }
    public void set_s(Boolean b){s = b;}
    public void set_selected(int b){selected = b;}

    public int get_selected()
    {
        return selected;
    }
    public Boolean get_s(){return s;}
    public Boolean get_please_quit(){return please_quit;}
    public int get_type_of_lives(){return type_of_lives;}
    public void set_type_of_lives(int b){type_of_lives = b;}
    public void set_quit(Boolean b){please_quit = b;}
    public void set_start(Boolean b){s = b;}
    public void set_selected(Integer b){selected = b;};
}
