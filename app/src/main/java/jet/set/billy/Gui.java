package jet.set.billy;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.Map;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;

public class Gui
{
    Terminal terminal;
    Screen screen;
    TextGraphics tg;

    Set<Character> pressedKeys = new HashSet<>();
    Map<String, BasicTextImage> blocks = new HashMap<>();

    Boolean run = true;
    int sprite_time = 0;

    public Boolean get_run()
    {
        return run;
    }
    public void set_run(Boolean nrun)
    {
        run = nrun;
    }

    public Set<Character> get_pressedKeys()
    {
        return pressedKeys;
    }

    // Criar pixeis pequenos
    private AWTTerminalFontConfiguration loadFont() throws Exception
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
    
    public Gui() throws Exception
    {
        AWTTerminalFontConfiguration fontConfig = loadFont();
        TerminalSize terminalSize = new TerminalSize(250, 150);
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
                pressedKeys.add(e.getKeyChar());
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    run = false;
                }
            }
        
            @Override
            public void keyReleased(KeyEvent e)
            {
                pressedKeys.remove(e.getKeyChar());
            }
        });

        initialize_blocks();
    }

    BasicTextImage text_to_sprite(String t, TextColor color_x, TextColor color_y, TextColor color_z)
    {
        BasicTextImage b = new BasicTextImage(10, 10);
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (t.charAt(i * 11 + j) == 'x')
                {
                    b.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', color_x, color_x)[0]);
                }
                else if (t.charAt(i * 11 + j) == 'y')
                {
                    b.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', color_y, color_y)[0]);
                }
                else if (t.charAt(i * 11 + j) == 'z')
                {
                    b.setCharacterAt(j, i, TextCharacter.fromCharacter(' ', color_z, color_z)[0]);
                }
            }
        }
        return b;
    }

    void initialize_blocks() throws Exception
    {
        BasicTextImage wall = new BasicTextImage(10, 10);
        wall.setAll(TextCharacter.fromCharacter(' ', TextColor.ANSI.RED, TextColor.ANSI.BLUE)[0]);
        blocks.put("wall", wall);

        String platform_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/platform.txt").toURI()));
        platform_string = platform_string.replaceAll("\r", "");
        blocks.put("platform", text_to_sprite(platform_string, TextColor.ANSI.GREEN, null, null));

        String ladder_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("blocks/ladder.txt").toURI()));
        ladder_string = ladder_string.replaceAll("\r", "");
        blocks.put("ladder", text_to_sprite(ladder_string, TextColor.Factory.fromString("#004040"), TextColor.Factory.fromString("#005050"), null));

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
    }

    public void close() throws Exception
    {
        screen.close();
    }

    public void clear()
    {
        screen.clear();
    }
 
    public void refresh() throws Exception
    {
        screen.refresh();
    }

    public void drawEnemies(List<Enemy> enemies)
    {
        for (Enemy enemy : enemies)
        {
            for (int i = 0; i < enemy.get_size_y(); i++)
            {
                for (int j = 0; j < enemy.get_size_x(); j++)
                {
                    if (enemy.get_sprite_string().charAt(i * (enemy.get_size_x() + 1) + j) == 'x')
                    {
                        tg.setCharacter(enemy.get_position_x() + j, enemy.get_position_y() + i,  TextCharacter.fromCharacter(' ', TextColor.ANSI.RED, TextColor.ANSI.RED)[0]);
                    }
                }
            }
        }
    }

    public void drawPlayer(Player player)
    {
        for (int i = 0; i < player.get_size_y(); i++)
        {
            for (int j = 0; j < player.get_size_x(); j++)
            {
                if (player.get_current_sprite().charAt(i * (player.get_size_x() + 1) + j) == 'x')
                {
                    tg.setCharacter(player.get_position_x() + j, player.get_position_y() + i,  TextCharacter.fromCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.WHITE)[0]);
                }
                else if (player.get_current_sprite().charAt(i * (player.get_size_x() + 1) + j) == '-')
                {
                    tg.setCharacter(player.get_position_x() + j, player.get_position_y() + i,  TextCharacter.fromCharacter(' ', TextColor.ANSI.BLACK, TextColor.ANSI.BLACK)[0]);
                }
            }
        }
    }

    public void drawRoom(Room room)
    {
        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 13; j++)
            {
                TerminalPosition imagePosition = new TerminalPosition(i*10, j*10);
                if (room.get_room_string().charAt(i + j*26) == 'x')
                {
                    tg.drawImage(imagePosition, blocks.get("wall"), imagePosition.TOP_LEFT_CORNER, blocks.get("wall").getSize());
                }
                else if (room.get_room_string().charAt(i + j*26) == '-')
                {
                    tg.drawImage(imagePosition, blocks.get("platform"), imagePosition.TOP_LEFT_CORNER, blocks.get("platform").getSize());
                }
                else if (room.get_room_string().charAt(i + j*26) == 'H')
                {
                    tg.drawImage(imagePosition, blocks.get("ladder"), imagePosition.TOP_LEFT_CORNER, blocks.get("ladder").getSize());
                }
                else if (room.get_room_string().charAt(i + j*26) == 's')
                {
                    tg.drawImage(imagePosition, blocks.get("stairs_left"), imagePosition.TOP_LEFT_CORNER, blocks.get("stairs_left").getSize());
                }
                else if (room.get_room_string().charAt(i + j*26) == 'z')
                {
                    tg.drawImage(imagePosition, blocks.get("stairs_right"), imagePosition.TOP_LEFT_CORNER, blocks.get("stairs_right").getSize());
                }
                else if (room.get_room_string().charAt(i + j*26) == 't')
                {
                    if (sprite_time < 2)
                    {
                        tg.drawImage(imagePosition, blocks.get("toilet_1"), imagePosition.TOP_LEFT_CORNER, blocks.get("toilet_1").getSize());
                    }
                    else if (sprite_time < 4)
                    {
                        tg.drawImage(imagePosition, blocks.get("toilet_2"), imagePosition.TOP_LEFT_CORNER, blocks.get("toilet_2").getSize());
                    }
                    else
                    {
                        tg.drawImage(imagePosition, blocks.get("toilet_3"), imagePosition.TOP_LEFT_CORNER, blocks.get("toilet_3").getSize());
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
}
