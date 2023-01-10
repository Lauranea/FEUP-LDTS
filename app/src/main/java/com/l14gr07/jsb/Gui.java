package com.l14gr07.jsb;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


import java.util.Map;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;

import com.l14gr07.jsb.model.Enemy;
import com.l14gr07.jsb.model.Player;
import com.l14gr07.jsb.model.Room;
import com.l14gr07.jsb.model.Item;
import com.l14gr07.jsb.model.World;

public class Gui
{
    Terminal terminal;
    Screen screen;
    TextGraphics tg;

    Set<Integer> pressedKeys = new HashSet<>();
    Map<String, BasicTextImage> blocks = new HashMap<>();
    Map<Integer, BasicTextImage> numerals = new HashMap<>();

    BasicTextImage items_collected_sprite;

    Boolean run = true;
    Boolean won = false;
    int sprite_time = 0;

    public Boolean get_run()
    {
        return run;
    }
    public void set_run(Boolean nrun)
    {
        run = nrun;
    }

    public Set<Integer> get_pressedKeys()
    {
        return pressedKeys;
    }

    void initialize_blocks() throws IOException, URISyntaxException, FontFormatException
    {
        String wall_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/wall.txt").toURI()));
        wall_string = wall_string.replaceAll("\r", "");
        blocks.put("wall", text_to_sprite(wall_string,new TextColor.RGB(0, 0, 255), new TextColor.RGB(51, 51, 255),null));

        String fake_wall_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/fake_wall.txt").toURI()));
        fake_wall_string = fake_wall_string.replaceAll("\r", "");
        blocks.put("fake_wall", text_to_sprite(fake_wall_string,new TextColor.RGB(0, 0, 255), new TextColor.RGB(153, 102, 255),null));

        String platform_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/platform.txt").toURI()));
        platform_string = platform_string.replaceAll("\r", "");
        blocks.put("platform", text_to_sprite(platform_string, new TextColor.RGB(0, 153, 122), new TextColor.RGB(0, 204, 163), null));

        String ladder_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/ladder.txt").toURI()));
        ladder_string = ladder_string.replaceAll("\r", "");
        blocks.put("ladder", text_to_sprite(ladder_string, new TextColor.RGB(102, 51, 0), new TextColor.RGB(102, 51, 0), new TextColor.RGB(179, 89, 0)));

        String stairs_left_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/stairs_left.txt").toURI()));
        stairs_left_string = stairs_left_string.replaceAll("\r", "");
        blocks.put("stairs_left", text_to_sprite(stairs_left_string, TextColor.ANSI.WHITE, null, null));

        String stairs_right_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/stairs_right.txt").toURI()));
        stairs_right_string = stairs_right_string.replaceAll("\r", "");
        blocks.put("stairs_right", text_to_sprite(stairs_right_string, TextColor.ANSI.WHITE, null, null));

        String toilet_string_1 = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/toilet_1.txt").toURI()));
        toilet_string_1 = toilet_string_1.replaceAll("\r", "");
        blocks.put("toilet_1", text_to_sprite(toilet_string_1, TextColor.ANSI.WHITE, null, null));

        String toilet_string_2 = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/toilet_2.txt").toURI()));
        toilet_string_2 = toilet_string_2.replaceAll("\r", "");
        blocks.put("toilet_2", text_to_sprite(toilet_string_2, TextColor.ANSI.WHITE, null, null));

        String toilet_string_3 = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/toilet_3.txt").toURI()));
        toilet_string_3 = toilet_string_3.replaceAll("\r", "");
        blocks.put("toilet_3", text_to_sprite(toilet_string_3, TextColor.ANSI.WHITE, null, null));

        String bed1_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/bed/bed1.txt").toURI()));
        bed1_string = bed1_string.replaceAll("\r", "");
        blocks.put("bed1", text_to_sprite(bed1_string, TextColor.ANSI.WHITE, TextColor.ANSI.RED, new TextColor.RGB(102, 51, 0)));

        String bed2_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/bed/bed2.txt").toURI()));
        bed2_string = bed2_string.replaceAll("\r", "");
        blocks.put("bed2", text_to_sprite(bed2_string, TextColor.ANSI.WHITE, TextColor.ANSI.RED, new TextColor.RGB(102, 51, 0)));
    }


    // Criar pixeis pequenos
    private AWTTerminalFontConfiguration loadFont() throws IOException, URISyntaxException, FontFormatException
    {
        URL resource = getClass().getClassLoader().getResource("fonts/square.ttf");
        File fontFile = new File(resource.toURI());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        Font loadedFont = font.deriveFont(Font.PLAIN, 3);
        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(loadedFont);
        return fontConfig;
    }
    
    public Gui() throws IOException, URISyntaxException, FontFormatException
    {
        AWTTerminalFontConfiguration fontConfig = loadFont();
        TerminalSize terminalSize = new TerminalSize(250, 185);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
        terminalFactory.setForceAWTOverSwing(true);
        terminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);
        terminal = terminalFactory.createTerminal();

        screen = new TerminalScreen(terminal);
        tg = screen.newTextGraphics();
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();

        ((AWTTerminalFrame)terminal).getComponent(0).addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                pressedKeys.add(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    run = false;
                }
            }
        
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (pressedKeys.contains(e.getKeyCode()))
                {
                    pressedKeys.remove(e.getKeyCode());
                }
            }
        });

        initialize_numerals();
        initialize_hud();
        initialize_blocks();
    }

    BasicTextImage text_to_sprite(String t, TextColor color_x, TextColor color_y, TextColor color_z)
    {
        String[] tt = t.split("\n");
        int l = tt[0].length();

        BasicTextImage b = new BasicTextImage(tt[0].length(), tt.length);

        for (int i = 0; i < tt.length; i++)
        {
            for (int j = 0; j < l; j++)
            {
                if (t.charAt(i * (l+1) + j) == 'x')
                {
                    b.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', color_x, color_x)[0]);
                }
                else if (t.charAt(i * (l+1) + j) == 'y')
                {
                    b.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', color_y, color_y)[0]);
                }
                else if (t.charAt(i * (l+1) + j) == 'z')
                {
                    b.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', color_z, color_z)[0]);
                }
            }
        }
        return b;
    }

    public void close()  throws IOException, URISyntaxException
    {
        screen.close();
    }

    public void clear()
    {
        screen.clear();
    }
 
    public void refresh()  throws IOException, URISyntaxException
    {
        screen.refresh();
    }

    public void drawEnemy(Enemy enemy)
    {
        for (int i = 0; i < enemy.get_size_y(); i++)
        {
            for (int j = 0; j < enemy.get_size_x(); j++)
            {
                TextCharacter c = Loader.get_colors().get(enemy.get_sprite_string().charAt(i * (enemy.get_size_x() + 1) + j));
                if (c != null)
                {
                    tg.setCharacter(enemy.get_position_x() + j, enemy.get_position_y() + i,  Loader.get_colors().get(enemy.get_sprite_string().charAt(i * (enemy.get_size_x() + 1) + j)));
                }
            }
        }
    }

    public void drawItem(Item item)
    {
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                TextCharacter c = Loader.get_colors().get(item.get_sprite_string().charAt(i * 7 + j));
                if (c != null)
                {
                    tg.setCharacter(item.get_position_x() + j, item.get_position_y() + i, Loader.get_colors().get(item.get_sprite_string().charAt(i * 7 + j)));
                }
            }
        }
    }

    public void drawPlayer(Player player) throws IOException, URISyntaxException
    {
        if (won) return;
        
        for (int i = 0; i < player.get_size_y(); i++)
        {
            for (int j = 0; j < player.get_size_x(); j++)
            {
                TextCharacter c = Loader.get_colors().get(player.get_current_sprite().charAt(i * (player.get_size_x() + 1) + j));
                if (c != null)
                {
                    tg.setCharacter(player.get_position_x() + j, player.get_position_y() + i, c);
                }
            }
        }
    }

    public void drawRoom(Room room)
    {
        won = room.get_won();

        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 13; j++)
            {
                TerminalPosition imagePosition = new TerminalPosition(i*10, j*10);
                TerminalPosition TOP_LEFT = new TerminalPosition(0, 0);
                if (room.get_room_string().charAt(i + j*26) == 'x')
                {
                    tg.drawImage(imagePosition, blocks.get("wall"), TOP_LEFT, blocks.get("wall").getSize());
                }
                else if (room.get_room_string().charAt(i + j*26) == 'f')
                {
                    tg.drawImage(imagePosition, blocks.get("fake_wall"), TOP_LEFT, blocks.get("fake_wall").getSize());
                }
                else if (room.get_room_string().charAt(i + j*26) == '-')
                {
                    tg.drawImage(imagePosition, blocks.get("platform"), TOP_LEFT, blocks.get("platform").getSize());
                }
                else if (room.get_room_string().charAt(i + j*26) == 'H')
                {
                    tg.drawImage(imagePosition, blocks.get("ladder"), TOP_LEFT, blocks.get("ladder").getSize());
                }
                else if (room.get_room_string().charAt(i + j*26) == 's')
                {
                    tg.drawImage(imagePosition, blocks.get("stairs_left"), TOP_LEFT, blocks.get("stairs_left").getSize());
                }
                else if (room.get_room_string().charAt(i + j*26) == 'z')
                {
                    tg.drawImage(imagePosition, blocks.get("stairs_right"), TOP_LEFT, blocks.get("stairs_right").getSize());
                }
                else if (room.get_room_string().charAt(i + j*26) == 't')
                {
                    if (sprite_time < 2)
                    {
                        tg.drawImage(imagePosition, blocks.get("toilet_1"), TOP_LEFT, blocks.get("toilet_1").getSize());
                    }
                    else if (sprite_time < 4)
                    {
                        tg.drawImage(imagePosition, blocks.get("toilet_2"), TOP_LEFT, blocks.get("toilet_2").getSize());
                    }
                    else
                    {
                        tg.drawImage(imagePosition, blocks.get("toilet_3"), TOP_LEFT, blocks.get("toilet_3").getSize());
                    }
                }
                else if (room.get_room_string().charAt(i + j*26) == 'b')
                {
                    if (!won)
                    {
                        tg.drawImage(new TerminalPosition(i*10-10, j*10-4), blocks.get("bed1"), TOP_LEFT, blocks.get("bed1").getSize());
                    }
                    else
                    {
                        tg.drawImage(new TerminalPosition(i*10-10, j*10-4), blocks.get("bed2"), TOP_LEFT, blocks.get("bed2").getSize());
                    }
                }
            }
        }
        sprite_time++;
        if (sprite_time > 5)
        {
            sprite_time = 0;
        }
    }

    void initialize_numerals() throws IOException, URISyntaxException, FontFormatException
    {
        for (int i = 0; i < 10; i++)
        {
            String numeral_sprite_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("fonts/items/" + i + ".txt").toURI()));
            numeral_sprite_string = numeral_sprite_string.replaceAll("\r", "");
            String[] numeral_sprite_array = numeral_sprite_string.split("\n");

            BasicTextImage numeral_sprite = new BasicTextImage(numeral_sprite_array[0].length(), numeral_sprite_array.length);

            TextCharacter c = TextCharacter.fromCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.WHITE)[0];

            for (int j = 0; j < numeral_sprite_array.length; j++)
            {
                for (int k = 0; k < numeral_sprite_array[j].length(); k++)
                {
                    if (numeral_sprite_array[j].charAt(k) == 'x')
                    {
                        numeral_sprite.setCharacterAt(k, j, c);
                    }
                }
            }
            numerals.put(i, numeral_sprite);
        }
    }

    void initialize_hud() throws IOException, URISyntaxException, FontFormatException
    {
        String items_collected_sprite_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("fonts/items/items_collected.txt").toURI()));
        items_collected_sprite_string = items_collected_sprite_string.replaceAll("\r", "");
        String[] temp_room_array = items_collected_sprite_string.split("\n");

        items_collected_sprite = new BasicTextImage(temp_room_array[0].length(), temp_room_array.length);

        TextCharacter c = TextCharacter.fromCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.WHITE)[0];

        for (int i = 0; i < temp_room_array.length; i++)
        {
            for (int j = 0; j < temp_room_array[i].length(); j++)
            {
                if (temp_room_array[i].charAt(j) == 'x') 
                {
                    items_collected_sprite.setCharacterAt(j, i, c);
                }
            }
        }
    }

    public void drawHUD(Player player, World world) throws IOException, URISyntaxException
    {
        tg.drawImage(new TerminalPosition(world.get_current_room().get_room_name_sprite_position(), 135), world.get_current_room().get_room_name_sprite());
        tg.drawImage(new TerminalPosition(5, 150), items_collected_sprite);
        tg.drawImage(new TerminalPosition(85, 150), numerals.get(world.get_coins_collected()/100));
        tg.drawImage(new TerminalPosition(91, 150), numerals.get(world.get_coins_collected()%100/10));
        tg.drawImage(new TerminalPosition(97, 150), numerals.get(world.get_coins_collected()%10));

        for (int i = 0; i < player.get_lives(); i++)
        {
            tg.drawImage(new TerminalPosition(5 + i * 15, 165), player.get_sprite_life());
        }
    }

    public void drawMenu()
    {
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(10, 10, "Press ENTER");
    }

    public void drawRectangle(int x, int y, int width, int height, TextCharacter c)
    {
        tg.fillRectangle(new TerminalPosition(x, y), new TerminalSize(width, height), c);
    }

    String L = "xx   xx   xx xx xx  ";
    public void drawSelectionL(int x, int y, TextCharacter c)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (L.charAt(i * 4 + j) == 'x')
                {
                    tg.setCharacter(new TerminalPosition(x + j, y + i), c);
                }
            }
        }
    }

    String R = "  xx xx xx   xx   xx";
    public void drawSelectionR(int x, int y, TextCharacter c)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (R.charAt(i * 4 + j) == 'x')
                {
                    tg.setCharacter(new TerminalPosition(x + j, y + i), c);
                }
            }
        }
    }

    public void drawImage(int x, int y, BasicTextImage p)
    {
        tg.drawImage(new TerminalPosition(x, y), p);
    }
}
