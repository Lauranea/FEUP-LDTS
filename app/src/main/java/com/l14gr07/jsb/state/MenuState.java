package com.l14gr07.jsb.state;


import java.io.IOException;
import java.net.URISyntaxException;

import com.l14gr07.jsb.Game;
import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.controller.Controller;
import com.l14gr07.jsb.controller.menu.MenuController;
import com.l14gr07.jsb.model.Menu;
import com.l14gr07.jsb.model.World;
import com.l14gr07.jsb.viewer.Viewer;
import com.l14gr07.jsb.viewer.menu.MenuViewer;

public class MenuState extends State<Menu>
{
    Menu menu = new Menu();

    public MenuState(Menu model) throws IOException, URISyntaxException
    {
        super(model);
    }

    @Override
    protected Viewer<Menu> getViewer()
    {
        return new MenuViewer(getModel());
    }

    @Override
    protected Controller<Menu> getController() throws IOException, URISyntaxException
    {
        return new MenuController(getModel());
    }

    public Menu get_info()
    {
        return menu;
    }
}
