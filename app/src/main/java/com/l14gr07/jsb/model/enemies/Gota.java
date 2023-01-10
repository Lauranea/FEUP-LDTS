package com.l14gr07.jsb.model.enemies;

import java.io.IOException;
import java.net.URISyntaxException;

import com.l14gr07.jsb.Loader;
import com.l14gr07.jsb.model.Enemy;

public class Gota extends Enemy
{
    public Gota(int npx1, int npy1, int npy2) throws IOException, URISyntaxException
    {
        super(npx1, npy1, npx1, npy2, false, 3, Loader.get_string_from_file("enemies/gota.txt"), 5, 7);
    }
}
