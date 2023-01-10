package com.l14gr07.jsb;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;

public class Loader
{
    static Map<Character, TextCharacter> pixel_color = Map.ofEntries
    (
        Map.entry('r', TextCharacter.fromCharacter(' ', TextColor.ANSI.RED, TextColor.ANSI.RED)[0]),                                                 // RED
        Map.entry('w', TextCharacter.fromCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.WHITE)[0]),                                             // WHITE
        Map.entry('q', TextCharacter.fromCharacter(' ', TextColor.ANSI.BLACK, TextColor.ANSI.BLACK)[0]),                                             // BLACK
        Map.entry('g', TextCharacter.fromCharacter(' ', new TextColor.RGB(32, 96, 32), new TextColor.RGB(32, 96, 32))[0]),         // GREEN
        Map.entry('p', TextCharacter.fromCharacter(' ', new TextColor.RGB(255, 179, 255), new TextColor.RGB(255, 179, 255))[0]),   // PINK
        Map.entry('b', TextCharacter.fromCharacter(' ', new TextColor.RGB(102, 51, 0), new TextColor.RGB(102, 51, 0))[0]),         // BROWN
        Map.entry('n', TextCharacter.fromCharacter(' ', new TextColor.RGB(51, 26, 0), new TextColor.RGB(51, 26, 0))[0]),           // DARK BROWN
        Map.entry('t', TextCharacter.fromCharacter(' ', new TextColor.RGB(255, 255, 0), new TextColor.RGB(255, 255, 26))[0]),      // YELLOW
        Map.entry('u', TextCharacter.fromCharacter(' ', new TextColor.RGB(255, 255, 0), new TextColor.RGB(255, 255, 153))[0]),     // YELLOW PASTEL
        Map.entry('i', TextCharacter.fromCharacter(' ', new TextColor.RGB(255, 255, 0), new TextColor.RGB(179, 179, 0))[0]),       // YELLOW DARK
        Map.entry('o', TextCharacter.fromCharacter(' ', new TextColor.RGB(255, 128, 0), new TextColor.RGB(255, 128, 0))[0]),       // ORANGE
        Map.entry('c', TextCharacter.fromCharacter(' ', new TextColor.RGB(179, 179, 178),new TextColor.RGB(179, 179, 178))[0]),     // GRAY
        Map.entry('v', TextCharacter.fromCharacter(' ', new TextColor.RGB(163, 26, 255), new TextColor.RGB(163, 26, 255))[0]),     // Purple
        Map.entry('f', TextCharacter.fromCharacter(' ', new TextColor.RGB(204, 128, 255), new TextColor.RGB(204, 128, 255))[0]),     // Purple pastel
        Map.entry('a', TextCharacter.fromCharacter(' ', new TextColor.RGB(51, 204, 255), new TextColor.RGB(51, 204, 255))[0]),     // Blue
        Map.entry('s', TextCharacter.fromCharacter(' ', new TextColor.RGB(153, 230, 255), new TextColor.RGB(153, 230, 255))[0]),     // bright blue
        Map.entry('x', TextCharacter.fromCharacter(' ', new TextColor.RGB(0, 0, 255), new TextColor.RGB(0, 0, 255))[0]),     // x
        Map.entry('y', TextCharacter.fromCharacter(' ', new TextColor.RGB(51, 51, 255), new TextColor.RGB(51, 51, 255))[0])      // y
    );

    public static Map<Character, TextCharacter> get_colors()
    {
        return pixel_color;
    }

    public static BasicTextImage loadImage(int big, String p) throws URISyntaxException, IOException
    {
        String s = Files.readString(Paths.get(Loader.class.getClassLoader().getResource(p).toURI()));
        BasicTextImage r;
        s = s.replaceAll("\r", "");
        String[] sprite_array = s.split("\n");

        r = new BasicTextImage(sprite_array[0].length()*big, sprite_array.length*big);

        for (int j = 0; j < sprite_array.length; j++)
        {
            for (int k = 0; k < sprite_array[j].length(); k++)
            {
                TextCharacter c = pixel_color.get(sprite_array[j].charAt(k));
                if (c != null)
                {
                    for (int o = 0; o < big; o++)
                    {
                        for (int b = 0; b < big; b++)
                        {
                            r.setCharacterAt(k*big + b, j*big + o, c);
                        }
                    }
                }
            }
        }
        return r;
    }

    public static String get_string_from_file(String path) throws IOException, URISyntaxException
    {
        String enemyString = Files.readString(Paths.get(Loader.class.getClassLoader().getResource(path).toURI()));
        return enemyString.replaceAll("\r", "");
    }
}
