package com.l14gr07.jsb.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    Menu menu;
    @BeforeEach
    void setup() throws IOException, URISyntaxException {
        menu = new Menu();
    }
    
    @Test
    void select_s_test()
    {
        menu.set_s(true);
        menu.select();
        assertEquals(menu.get_selected(), menu.get_type_of_lives());
        assertEquals(true, menu.select());
    }
    @Test
    void select_not_s_selected2()
    {
        menu.set_s(false);
        menu.set_selected(2);
        menu.select();
        assertEquals(menu.get_please_quit(), true);
        assertEquals(menu.select(), false);
    }
    @Test
    void select_not_s_selected1()
    {
        menu.set_s(false);
        menu.set_selected(1);
        menu.select();
        assertEquals(menu.get_s(), true);
        menu.set_s(false);
        assertEquals(menu.select(), false);
    }
    
    @Test
    void get_lives1()
    {
        menu.set_type_of_lives(1);
        assertEquals(menu.get_lives(), 10);
    }
    @Test
    void get_lives_not_1()
    {
        menu.set_type_of_lives(0);
        assertEquals(menu.get_lives(), -1);
    }
}