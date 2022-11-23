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
                    e.set_position_y(e.get_position_y()+e.get_speed());
                }
                else
                {
                    e.set_position_y(e.get_position_y()-e.get_speed());
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
                    e.set_position_y(e.get_position_y()+e.get_speed());
                }
            } 
        }
        else if (e.get_position_y1() == e.get_position_y2())
        {
            if (e.is_boomerang())
            {
                if (e.get_position_x() > e.get_position_x2())
                {
                    e.set_f(false);
                }
                else if (e.get_position_x() < e.get_position_x1())
                {
                    e.set_f(true);
                }
                if (e.get_f())
                {
                    e.set_position_x(e.get_position_x()+e.get_speed());
                }
                else
                {
                    e.set_position_x(e.get_position_x()-e.get_speed());
                }
            }   
            else
            {
                if (e.get_position_x() > e.get_position_x2())
                {
                    e.set_position_x(e.get_position_x1());
                }
                else
                {
                    e.set_position_x(e.get_position_x()+e.get_speed());
                }
            } 
        }
    }
}
