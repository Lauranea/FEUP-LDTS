package com.l14gr07.jsb;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.l14gr07.jsb.model.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.FontFormatException;

class GuiTest 
{
    private Gui gui;
    private Terminal terminal;
    private Screen screen;
    private TextGraphics tg;
    private List<Enemy> enemies;
    
    public GuiTest()  throws IOException, URISyntaxException, FontFormatException
    {
        gui = new Gui();
    }
    
    @BeforeEach
    void startup()
    {
        screen = Mockito.mock(Screen.class);
        tg = Mockito.mock(TextGraphics.class);
        Mockito.when(screen.newTextGraphics()).thenReturn(tg);
        enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy(0,0,10,10,true,3,"xxx\nxxx\nxxx",3,3));
        enemies.add(new Enemy(2,0,5,3,true,1,"xxx\nxxx\nxxx",3,3));
        enemies.add(new Enemy(3,7,2,7,false,2,"xxx\nxxx\nxxx",3,3));
    }
    
}