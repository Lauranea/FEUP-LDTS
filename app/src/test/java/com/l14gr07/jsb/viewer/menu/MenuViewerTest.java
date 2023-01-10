package com.l14gr07.jsb.viewer.menu;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.model.Menu;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.io.IOException;
import java.net.URISyntaxException;


class MenuViewerTest 
{
    Gui gui;
    MenuViewer menuViewer;
    Menu menu;
    TextCharacter red;
    
    @Property
    void stepTestMenuStart(@ForAll Boolean menuquit, @ForAll @IntRange(min = 0, max = 1) Integer select) throws IOException, URISyntaxException {
        gui = Mockito.mock(Gui.class);
        menu = new Menu();
        menuViewer = new MenuViewer(menu);
        menu.set_quit(menuquit);
        menu.set_start(true);
        menu.set_selected(select);
        red = TextCharacter.fromCharacter(' ', TextColor.ANSI.RED, TextColor.ANSI.RED)[0];
        menuViewer.step(gui);
        if(menuquit)
        {
            Mockito.verify(gui,Mockito.times(1)).set_run(false);
        }
        Mockito.verify(gui, Mockito.times(1)).drawImage((250 - menu.titulo.getSize().getColumns()) / 2, 20, menu.titulo);
        
        Mockito.verify(gui,Mockito.times(1)).drawImage((250 - menu.ten_lives.getSize().getColumns()) / 2, 110, menu.ten_lives);
        Mockito.verify(gui, Mockito.times(1)).drawImage((250 - menu.endless.getSize().getColumns()) / 2, 135, menu.endless);
        if(select == 1)
        {
            Mockito.verify(gui, Mockito.times(1)).drawSelectionL(112 - (menu.ten_lives.getSize().getColumns() / 2), 88 + menu.get_selected() * 25, red);
            Mockito.verify(gui, Mockito.times(1)).drawSelectionR(133 + (menu.ten_lives.getSize().getColumns() / 2), 88 + menu.get_selected() * 25, red);
        }
        else
        {
            Mockito.verify(gui, Mockito.times(1)).drawSelectionL(112 - (menu.endless.getSize().getColumns() / 2), 88 + menu.get_selected() * 25, red);
            Mockito.verify(gui, Mockito.times(1)).drawSelectionR(133 + (menu.endless.getSize().getColumns() / 2), 88 + menu.get_selected() * 25, red);
        }
        
    }
    @Property
    void stepTestNotMenuStart(@ForAll Boolean menuquit, @ForAll @IntRange(min = 0, max = 1) Integer select) throws IOException, URISyntaxException {
    gui = Mockito.mock(Gui.class);
    menu = new Menu();
    menuViewer = new MenuViewer(menu);
    menu.set_quit(menuquit);
    menu.set_start(false);
    menu.set_selected(select);
    red = TextCharacter.fromCharacter(' ', TextColor.ANSI.RED, TextColor.ANSI.RED)[0];
    menuViewer.step(gui);
    
    if(menuquit)
    {
        Mockito.verify(gui,Mockito.times(1)).set_run(false);
    }
    Mockito.verify(gui, Mockito.times(1)).drawImage((250 - menu.titulo.getSize().getColumns()) / 2, 20, menu.titulo);
    
    Mockito.verify(gui, Mockito.times(1)).drawImage((250 - menu.start.getSize().getColumns()) / 2, 110, menu.start);
    Mockito.verify(gui, Mockito.times(1)).drawImage((250 - menu.quit.getSize().getColumns()) / 2, 135, menu.quit);
    
    if(select == 1)
    {
        Mockito.verify(gui, Mockito.times(1)).drawSelectionL(112 - (menu.start.getSize().getColumns() / 2), 88 + menu.get_selected() * 25, red);
        Mockito.verify(gui, Mockito.times(1)).drawSelectionR(133 + (menu.start.getSize().getColumns() / 2), 88 + menu.get_selected() * 25, red);
    }
    else
    {
        Mockito.verify(gui, Mockito.times(1)).drawSelectionL(112 - (menu.quit.getSize().getColumns() / 2), 88 + menu.get_selected() * 25, red);
        Mockito.verify(gui, Mockito.times(1)).drawSelectionR(133 + (menu.quit.getSize().getColumns() / 2), 88 + menu.get_selected() * 25, red);
    }
    }
}