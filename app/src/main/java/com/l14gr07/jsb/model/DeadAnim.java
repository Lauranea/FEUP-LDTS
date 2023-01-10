package com.l14gr07.jsb.model;

import java.io.IOException;
import java.net.URISyntaxException;

import com.googlecode.lanterna.graphics.BasicTextImage;

import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.Loader;

public class DeadAnim
{
    int frames = 100;
    int f = 0;

    BasicTextImage po;
    BasicTextImage fo;
    BasicTextImage co;

    int fo_pos = -199;

    public DeadAnim()  throws IOException, URISyntaxException
    {
        po = Loader.loadImage(1, "player/right_1.txt");

        fo = Loader.loadImage(1, "anim/foot.txt");

        co = Loader.loadImage(1, "blocks/wall.txt");
    }

    public int get_f(){return f;}
    public int get_fo_pos(){return fo_pos;}
    public void set_f(int z){f = z;}
    public void set_fo_pos(int z){fo_pos = z;}
    public void set_fo(int z){fo_pos = z;}
    public void set_frames(int z){frames = z;}
    public int get_frames(){return frames;}
    public BasicTextImage getPo(){return po;}
    public BasicTextImage getFo(){return fo;}
    public BasicTextImage getCo(){return co;}
}
