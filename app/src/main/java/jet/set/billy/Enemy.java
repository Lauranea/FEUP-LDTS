package jet.set.billy;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Enemy
{
    int sx; // size x
    int sy; // size y

    int px; // position x
    int py; // position y

    int px1;
    int py1;

    int px2;
    int py2;

    int speed;
    String sprite_string;
    Boolean boomerang;
    Boolean f = true;

    public Enemy(int npx1, int npy1, int npx2, int npy2, Boolean nboomerang, int nspeed, String nsprite, int nsx, int nsy)
    {
        px1 = npx1;
        py1 = npy1;
        px2 = npx2;
        py2 = npy2;
        sx = nsx;
        sy = nsy;
        px = px1;
        py = py1;
        speed = nspeed;
        sprite_string = nsprite;
        boomerang = nboomerang;
    }

    public int get_position_x()
    {
        return px;
    }
    public int get_position_y()
    {
        return py;
    }

    public int get_size_x()
    {
        return sx;
    }
    public int get_size_y()
    {
        return sy;
    }

    public void draw(TextGraphics tg)
    {
        for (int i = 0; i < sy; i++)
        {
            for (int j = 0; j < sx; j++)
            {
                if (sprite_string.charAt(i * (sx + 1) + j) == 'x')
                {
                    tg.setCharacter(px + j, py + i,  TextCharacter.fromCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.WHITE)[0]);
                }
            }
        }
    }

    public void move()
    {
        if (px1 == px2)
        {
            System.out.println("px1 == px2");
            if (boomerang)
            {
                System.out.println("boom");
                if (py > py2)
                {
                    f = false;
                }
                else if (py < py1)
                {
                    f = true;
                }
                if (f)
                {
                    py+=speed;
                }
                else
                {
                    py-=speed;
                }
            }    
        }
        else if (py1 == py2)
        {
            System.out.println("py1 == py2");
            if (boomerang)
            {
                System.out.println("boom");
                if (px > px2)
                {
                    f = false;
                }
                else if (px < px1)
                {
                    f = true;
                }
                if (f)
                {
                    px+=speed;
                }
                else
                {
                    px-=speed;
                }
            }    
        }
        
    }
}
