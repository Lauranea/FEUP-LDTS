package com.l14gr07.jsb.viewer.game;

import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;
class RoomViewerTest 
{
    RoomViewer roomViewer;
    
    Gui gui;
    Room room;

    @BeforeEach
    void startup()  throws IOException, URISyntaxException
    {
        roomViewer = new RoomViewer();
        gui = Mockito.mock(Gui.class);
        room = Mockito.mock(Room.class);
    }

    @Test
    void drawPlayer()  throws IOException, URISyntaxException
    {
        roomViewer.draw(gui, room);

        Mockito.verify(gui, Mockito.times(1)).drawRoom(room);
    }
}