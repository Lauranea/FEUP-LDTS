package com.l14gr07.jsb.model.enemies;

import java.io.IOException;
import java.net.URISyntaxException;

import com.l14gr07.jsb.Loader;
import com.l14gr07.jsb.model.Enemy;

public class Shell extends Enemy
{
    public Shell(int npx1, int npx2, int npy1) throws IOException, URISyntaxException
    {
        super(npx1, npy1, npx2, npy1, true, 3, Loader.get_string_from_file("enemies/shell.txt"), 6, 6);
    }
}
