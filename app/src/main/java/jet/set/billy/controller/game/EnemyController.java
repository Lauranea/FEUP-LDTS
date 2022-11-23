package jet.set.billy.controller.game;

import jet.set.billy.Enemy;

public class EnemyController
{
    public void step(Enemy e)
    {
        if (e.get_position_x1() == e.get_position_x2())
        {
            if (e.is_boomerang())
            {
                if (e.get_position_y() > e.get_position_y2())
                {
                    e.set_f(false);
                }
                else if (e.get_position_y() < e.get_position_y1())
                {
                    e.set_f(true);
                }
                if (e.get_f())
                {
                    e.set_position_y(e.get_position_y()+1);
                }
                else
                {
                    e.set_position_y(e.get_position_y()-1);
                }
            }   
            else
            {
                if (e.get_position_y() > e.get_position_y2())
                {
                    e.set_position_y(e.get_position_y1());
                }
                else
                {
                    py+=speed;
                }
            } 
        }
        else if (py1 == py2)
        {
            if (boomerang)
            {
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
            else
            {
                if (px > px2)
                {
                    px = px1;
                }
                else
                {
                    px+=speed;
                }
            }  
        }
    }
}
