package com.l14gr07.jsb.viewer.menu;

import java.io.IOException;
import java.net.URISyntaxException;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.model.Menu;
import com.l14gr07.jsb.viewer.Viewer;

public class MenuViewer extends Viewer<Menu>
{
    TextCharacter red = TextCharacter.fromCharacter(' ', TextColor.ANSI.RED, TextColor.ANSI.RED)[0];

    public MenuViewer(Menu model)
    {
        super(model);
    }

    public void step(Gui gui) throws IOException, URISyntaxException
    {
        if (getModel().please_quit)
        {
            gui.set_run(false);
        }

        gui.drawImage((250 - getModel().titulo.getSize().getColumns()) / 2, 20, getModel().titulo);
        if (getModel().is_starting())
        {
            if (getModel().get_selected()==1)
            {
                gui.drawSelectionL(112 - (getModel().ten_lives.getSize().getColumns() / 2), 88 + getModel().get_selected() * 25, red);
                gui.drawSelectionR(133 + (getModel().ten_lives.getSize().getColumns() / 2), 88 + getModel().get_selected() * 25, red);
            }
            else
            {
                gui.drawSelectionL(112 - (getModel().endless.getSize().getColumns() / 2), 88 + getModel().get_selected() * 25, red);
                gui.drawSelectionR(133 + (getModel().endless.getSize().getColumns() / 2), 88 + getModel().get_selected() * 25, red);
            }
            gui.drawImage((250 - getModel().ten_lives.getSize().getColumns()) / 2, 110, getModel().ten_lives);
            gui.drawImage((250 - getModel().endless.getSize().getColumns()) / 2, 135, getModel().endless);
        }
        else
        {
            if (getModel().get_selected()==1)
            {
                gui.drawSelectionL(112 - (getModel().start.getSize().getColumns() / 2), 88 + getModel().get_selected() * 25, red);
                gui.drawSelectionR(133 + (getModel().start.getSize().getColumns() / 2), 88 + getModel().get_selected() * 25, red);
            }
            else
            {
                gui.drawSelectionL(112 - (getModel().quit.getSize().getColumns() / 2), 88 + getModel().get_selected() * 25, red);
                gui.drawSelectionR(133 + (getModel().quit.getSize().getColumns() / 2), 88 + getModel().get_selected() * 25, red);
            }
            gui.drawImage((250 - getModel().start.getSize().getColumns()) / 2, 110, getModel().start);
            gui.drawImage((250 - getModel().quit.getSize().getColumns()) / 2, 135, getModel().quit);
        }
    }    
}
