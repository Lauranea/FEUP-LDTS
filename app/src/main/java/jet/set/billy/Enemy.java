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

    public Boolean get_f()
    {
        return f;
    }
    public void set_f(Boolean nf)
    {
        f = nf;
    }

    public void set_position_x(int npx)
    {
        px = npx;
    }
    public void set_position_y(int npy)
    {
        py = npy;
    }

    public int get_position_x()
    {
        return px;
    }
    public int get_position_y()
    {
        return py;
    }

    public int get_position_x1()
    {
        return px1;
    }
    public int get_position_y1()
    {
        return py1;
    }

    public int get_position_x2()
    {
        return px2;
    }
    public int get_position_y2()
    {
        return py2;
    }

    public int get_size_x()
    {
        return sx;
    }
    public int get_size_y()
    {
        return sy;
    }

    public String get_sprite_string()
    {
        return sprite_string;
    }

    public Boolean is_boomerang()
    {
        return boomerang;
    }
    public int get_speed()
    {
        return speed;
    }
}
