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

public class Game
{
    Player player;

    Room room;

    TextGraphics tg;
    Screen screen;
    Set<Character> pressedKeys = new HashSet<>();

    Vector<Vector<String>> rooms = new Vector<Vector<String>>(Arrays.asList
    (
        new Vector<String>(Arrays.asList("Corridor", "Bathroom"))
    ));

    Map<String, BasicTextImage> blocks = new HashMap<>();
    Map<Integer, Enemy> enemies = new HashMap<>();

    Boolean run = true;

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

    public Game() throws Exception
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

    void draw()
    {
        room.draw(tg);
        player.draw(tg);
    }

    void change_room() throws Exception
    {
        if (player.get_position_x() < 0)
        {
            String room_name = rooms.get(room.get_coord1()).get(room.get_coord2() - 1);
            String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+room_name+".txt").toURI()));
            room_string = room_string.replaceAll("\r", "");
            room = new Room(room_string, room.get_coord1(), room.get_coord2() - 1, room_name, blocks);

            player.set_position_x(237);
            player.change_room(room_string);
        }
        else if (player.get_position_x() > 240)
        {
            String room_name = rooms.get(room.get_coord1()).get(room.get_coord2() + 1);
            String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/"+room_name+".txt").toURI()));
            room_string = room_string.replaceAll("\r", "");
            room = new Room(room_string, room.get_coord1(), room.get_coord2() + 1, room_name, blocks);
            
            player.set_position_x(3);
            player.change_room(room_string);
        }
    }

    void die() throws Exception
    {
        room = new Room(room.get_room_string(), room.get_coord1(), room.get_coord2(), room.get_room_name(), blocks);
        player.die();
    }

    void check_if_dead() throws Exception
    {
        for(Enemy e : room.get_enemies())
        {
            if ((player.get_position_x() > e.get_position_x() && player.get_position_x() < e.get_position_x() + e.get_size_x()) || (player.get_position_x() + player.get_size_x() > e.get_position_x() && player.get_size_x() + player.get_position_x() < e.get_position_x() + e.get_size_x()))
            {
                if ((player.get_position_y() >= e.get_position_y() && player.get_position_y() <= e.get_position_y() + e.get_size_y()) || (player.get_position_y() + player.get_size_y() >= e.get_position_y() && player.get_size_y() + player.get_position_y() <= e.get_position_y() + e.get_size_y()))
                {
                    die();
                }
            }
        }
    }

    public void run() throws Exception
    {
        AWTTerminalFontConfiguration fontConfig = loadFont();
        TerminalSize terminalSize = new TerminalSize(250, 150);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
        terminalFactory.setForceAWTOverSwing(true);
        terminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);
        Terminal terminal = terminalFactory.createTerminal();
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
        
        String room_string = Files.readString(Paths.get(getClass().getClassLoader().getResource("rooms/Bathroom.txt").toURI()));
        room_string = room_string.replaceAll("\r", "");
        room = new Room(room_string, 0, 1, "Bathroom", blocks);

        player = new Player(200, 100, room_string);

        screen = new TerminalScreen(terminal);
        tg = screen.newTextGraphics();
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        
        while (run)
        {
            player.handle_input(pressedKeys);
            check_if_dead();

            screen.clear();

            change_room();

            room.update(tg);
            draw();

            screen.refresh();
            Thread.sleep(20);
        }
        screen.close();
    }
}