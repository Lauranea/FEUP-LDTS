package com.l14gr07.jsb.model.enemies;

import java.io.IOException;
import java.net.URISyntaxException;

import com.l14gr07.jsb.Loader;
import com.l14gr07.jsb.model.Enemy;

public class Ghast extends Enemy
{
    public Ghast(int npx1, int npy1, int npy2) throws IOException, URISyntaxException
    {
        super(npx1, npy1, npx1, npy2, true, 1, Loader.get_string_from_file("enemies/ghast.txt"), 7, 9);
    }
}
