package com.l14gr07.jsb.model;

import java.util.LinkedList;

import java.io.IOException;
import java.net.URISyntaxException;

import com.googlecode.lanterna.graphics.BasicTextImage;
import com.l14gr07.jsb.Loader;

public class Player
{
    int sx = 10; // size x
    int sy = 16; // size y

    int px; // position x
    int py; // position y

    int safe_px;
    int safe_py;

    LinkedList<String> sprites_left = new LinkedList<String>();
    LinkedList<String> sprites_right = new LinkedList<String>();

    BasicTextImage sprite_life;
    int lives = 1;

    String current_sprite;

    void get_sprites()  throws IOException, URISyntaxException
    {
        sprites_left.add(Loader.get_string_from_file("player/left_1.txt"));
        sprites_left.add(Loader.get_string_from_file("player/left_2.txt"));
        sprites_left.add(Loader.get_string_from_file("player/left_3.txt"));
        sprites_right.add(Loader.get_string_from_file("player/right_1.txt"));
        sprites_right.add(Loader.get_string_from_file("player/right_2.txt"));
        sprites_right.add(Loader.get_string_from_file("player/right_3.txt"));
    }

    public BasicTextImage get_sprite_life()
    {
        return sprite_life;
    }
    public int get_lives()
    {
        return lives;
    }
    public void take_live()
    {
        lives--;
    }

    public Player(int new_px, int new_py, int nlives) throws IOException, URISyntaxException
    {
        px = new_px;
        py = new_py;
        safe_px = px;
        safe_py = py;

        lives = nlives;

        get_sprites();

        current_sprite = sprites_left.get(0);

        sprite_life = Loader.loadImage(1, "player/right_1.txt");
    }

    public int get_size_x()
    {
        return sx;
    }
    public int get_size_y()
    {
        return sy;
    }

    public int get_position_x()
    {
        return px;
    }
    public int get_position_y()
    {
        return py;
    }

    public void set_position_x(int new_px)
    {
        px = new_px;
    }
    public void set_position_y(int new_py)
    {
        py = new_py;
    }

    public int get_safe_position_x()
    {
        return safe_px;
    }
    public int get_safe_position_y()
    {
        return safe_py;
    }

    public void set_safe_position_x(int nsafe_px)
    {
        safe_px = nsafe_px;
    }
    public void set_safe_position_y(int nsafe_py)
    {
        safe_py = nsafe_py;
    }

    public String get_current_sprite()
    {
        return current_sprite;
    }

    public void change_sprite_left(int direction_level)
    {
        current_sprite = sprites_left.get(direction_level);
    }
    public void change_sprite_right(int direction_level)
    {
        current_sprite = sprites_right.get(direction_level);
    }
}