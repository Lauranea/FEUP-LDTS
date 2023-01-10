package com.l14gr07.jsb.model.enemies;

import java.io.IOException;
import java.net.URISyntaxException;

import com.l14gr07.jsb.Loader;
import com.l14gr07.jsb.model.Enemy;

public class Arrow_right extends Enemy
{
    public Arrow_right(int npx1, int npx2, int npy1) throws IOException, URISyntaxException
    {
        super(npx1, npy1, npx2, npy1, false, 2, Loader.get_string_from_file("enemies/arrow_right.txt"), 10, 3);
    }
}
