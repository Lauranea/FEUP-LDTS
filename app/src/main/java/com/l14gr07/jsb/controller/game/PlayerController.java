package com.l14gr07.jsb.controller.game;

import java.util.Arrays;
import java.util.Set;
import java.util.Vector;

import java.awt.event.KeyEvent;

import com.l14gr07.jsb.model.Player;
import com.l14gr07.jsb.model.Room;

public class PlayerController
{
    Boolean grounded = false;
    int direction_level = 0;
    int direction = 0;

    Boolean sprite_backwards = false;

    int sprite_delay = 0;

    Boolean jumping = false;
    int jump_height = 0;
    Boolean fall_after_jump = false;
    int jump_index = 5;
    int close_to_fall = 5;

    // Jump speed pattern
    Vector<Integer> jump = new Vector<Integer>(Arrays.asList
    (
        3, 3, 3, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1
    ));

    void jump()
    {
        if (get_grounded())
        {
            jump_index = 0;
            set_grounded(false);
            jumping = true;
        }
    }

    void move_left(Player player)
    {
        player.set_position_x(player.get_position_x()-1);
    }
    void move_right(Player player)
    {
        player.set_position_x(player.get_position_x()+1);
    }

    void move_y(Boolean j, Player player)
    {
        if (jump_index > 14)
        {
            jump_index = 14;
        }
        else if (jump_index < 0)
        {
            jump_index = 0;
            close_to_fall--;
        }

        if (close_to_fall < 0)
        {
            fall_after_jump = false;
        }
        
        if (j)
        {
            player.set_position_y(player.get_position_y()-jump.get(jump_index));
            jump_index++;
        }
        else
        {
            player.set_position_y(player.get_position_y()+jump.get(jump_index));
            jump_index--;
        }
    }

    public void advance_sprite()
    {
        sprite_delay++;
        if (sprite_delay > 3)
        {
            if (!sprite_backwards)
            {
                direction_level++;
                if (direction_level > 2)
                {
                    direction_level=1;
                    sprite_backwards = true;
                }
            }
            else
            {
                direction_level--;
                if (direction_level < 0)
                {
                    direction_level=1;
                    sprite_backwards = false;
                }
            }
            sprite_delay = 0;
        }
    }

    Boolean collision_ground(Player player, int i, int j)
    {
        if (!jumping && ((player.get_position_x() > i * 10 - 1 && player.get_position_x() < i * 10 + 11) || (player.get_position_x() + player.get_size_x() > i * 10 - 1 && player.get_position_x() + player.get_size_x() < i * 10 + 11)) && (player.get_position_y() + player.get_size_y() >= j * 10 && player.get_position_y() + player.get_size_y() < j * 10 + 3))
        {
            if (player.get_position_y() + player.get_size_y() > j * 10)
            {
                player.set_position_y(j * 10 - player.get_size_y());
            }
            set_grounded(true);
            return true;
        }
        return false;
    }

    Boolean collision_ceiling(Player player, int i, int j)
    {
        if (((player.get_position_x() >= i * 10 && player.get_position_x() < i * 10 + 10) || (player.get_position_x() + player.get_size_x() > i * 10 && player.get_position_x() + player.get_size_x() <= i * 10 + 10)) && (player.get_position_y() <= j * 10 + 10 && player.get_position_y() > j * 10 + 5))
        {
            if (jump_index < 10)
            {
                jump_index = 10;
            }
            if (player.get_position_y() < j * 10 + 10)
            {
                player.set_position_y(j * 10 + 10);
            }
            jumping = false;
            return true;
        }
        return false;
    }

    Boolean collision_left(Player player, int i, int j)
    {
        if ((player.get_position_x() == i * 10 + 10 || player.get_position_x() == i * 10 + 9) && ((player.get_position_y() + 6 > j * 10 && player.get_position_y() + 6 <= j * 10 + 10) || (player.get_position_y() + player.get_size_y() > j * 10 && player.get_position_y() + player.get_size_y() <= j * 10 + 10) || (player.get_position_y() > j * 10 && player.get_position_y() <= j * 10 + 10)))
        {
            player.set_position_x(player.get_position_x()+1);
            return true;
        }
        return false;
    }

    Boolean collision_right(Player player, int i, int j)
    {
        if ((player.get_position_x() + player.get_size_x() == i * 10 || player.get_position_x() + player.get_size_x() == i * 10 + 1) && ((player.get_position_y() + 6 > j * 10 && player.get_position_y() + 6 <= j * 10 + 10) || (player.get_position_y() + player.get_size_y() > j * 10 && player.get_position_y() + player.get_size_y() <= j * 10 + 10) || (player.get_position_y() > j * 10 && player.get_position_y() <= j * 10 + 10)))
        {
            player.set_position_x(player.get_position_x()-1);
            return true;
        }
        return false;
    }

    Boolean collision_stairs_left(Player player, int i, int j)
    {
        if (((!jumping && !fall_after_jump) || direction == 0) && ((player.get_position_x() > i * 10 - 1 && player.get_position_x() < i * 10 + 11) || (player.get_position_x() + player.get_size_x() > i * 10 - 1 && player.get_position_x() + player.get_size_x() < i * 10 + 11)))
        {
            for (int k = 0; k < 10; k ++)
            {
                if (player.get_position_x() == i * 10 + 10 - k)
                {
                    if (player.get_position_y() + player.get_size_y() >= j * 10 + 10 - k && player.get_position_y() + player.get_size_y() <= j * 10 + 12 - k)
                    {
                        player.set_position_y(player.get_position_y()-1);
                        set_grounded(true);
                        return true;
                    }
                    else if (player.get_position_y() + player.get_size_y() == j * 10 + 10 - k - 2)
                    {
                        player.set_position_y(player.get_position_y()+1);
                        set_grounded(true);
                        jumping = false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    Boolean collision_stairs_right(Player player, int i, int j)
    {
        if (((!jumping && !fall_after_jump) || direction == 0) && ((player.get_position_x() > i * 10 - 1 && player.get_position_x() < i * 10 + 11) || (player.get_position_x() + player.get_size_x() > i * 10 - 1 && player.get_position_x() + player.get_size_x() < i * 10 + 11)))
        {
            for (int k = 0; k < 10; k ++)
            {
                if (player.get_position_x() + player.get_size_x() == i * 10 + k)
                {
                    if (player.get_position_y() + player.get_size_y() >= j * 10 + 10 - k && player.get_position_y() + player.get_size_y() <= j * 10 + 12 - k)
                    {
                        player.set_position_y(player.get_position_y()-1);
                        set_grounded(true);
                        jumping = false;
                    }
                    else if (player.get_position_y() + player.get_size_y() == j * 10 + 10 - k - 2 )
                    {
                        player.set_position_y(player.get_position_y()+1);
                        set_grounded(true);
                        jumping = false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    void collision(Player player, Room room)
    {

        for (int i = 0 ; i * 10 <= player.get_position_x() + player.get_size_x() + 10; i++)
        {
            if (i * 10 + 10 < player.get_position_x())
            {
                continue;
            }
            for (int j = 0; j * 10 <= player.get_position_y() + player.get_size_y() + 5; j++)
            {
                if (j * 10 + 10 < player.get_position_y())
                {
                    continue;
                }
                char c = room.get_room_string().charAt(i + j * 26);
                if (c == 'x' || c == 'i')
                {
                    if (!collision_ground(player, i, j))
                    {
                        collision_ceiling(player, i, j);
                    }
                    if (!collision_left(player, i, j))
                    {
                        collision_right(player, i, j);
                    }
                }
                else if (c == '-' || c == 'H')
                {
                    collision_ground(player, i, j);
                }
                else if (c == 's')
                {
                    collision_stairs_left(player, i, j);
                }
                else if (c == 'z')
                {
                    collision_stairs_right(player, i, j);
                }
                else if (c == 'b')
                {
                    room.set_won(true);
                }
            }
        }
    }

    public void die(Player player)
    {
        player.set_position_x(player.get_safe_position_x());
        player.set_position_y(player.get_safe_position_y());
        jumping = false;
        fall_after_jump = false;
        set_grounded(true);
    }


    public void step(Set<Integer> pressedKeys, Player player, Room room)
    {
        if (get_grounded())
        {
            step_grounded(pressedKeys, player, room);
        }
        else
        {
            step_airborne(pressedKeys, player, room);
        }
        set_grounded(false);

        collision(player, room);
    }

    void step_grounded(Set<Integer> pressedKeys, Player player, Room room)
    {
        close_to_fall = 5;
        jump_index = 15;
        fall_after_jump = false;
        jump_height = 0;
        direction = 0;
        for (Integer e : pressedKeys)
        {
            if (e == KeyEvent.VK_A || e ==  KeyEvent.VK_LEFT)
            {
                move_left(player);
                advance_sprite();
                player.change_sprite_left(direction_level);
                direction = -1;
            }
            if (e == KeyEvent.VK_D || e ==  KeyEvent.VK_RIGHT)
            {
                move_right(player);
                advance_sprite();
                player.change_sprite_right(direction_level);
                direction = 1;
            }
            if (e == KeyEvent.VK_W || e ==  KeyEvent.VK_UP)
            {
                jump();
                break;
            }
        }
    }

    void step_airborne(Set<Integer> pressedKeys, Player player, Room room)
    {
        if (jumping)
        {
            fall_after_jump = true;
            if (jump_height < 15)
            {
                move_y(true, player);
                jump_height++;
            }
            else
            {
                jumping = false;
            }
        }
        else
        {
            move_y(false, player);
        }
        if (fall_after_jump)
        {
            if (direction == -1)
            {
                move_left(player);
                advance_sprite();
                player.change_sprite_left(direction_level);
            }
            else if (direction == 1)
            {
                move_right(player);
                advance_sprite();
                player.change_sprite_right(direction_level);
            }
        }
    }

    public void set_grounded(Boolean x)
    {
        grounded = x;
    }
    
    public boolean get_grounded()
    {
        return grounded;
    }
}
