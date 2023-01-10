package com.l14gr07.jsb.viewer.game;

import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.net.URISyntaxException;

class ItemViewerTest {
    ItemViewer itemViewer;
    Gui gui;
    Item item;

    @BeforeEach
    void startup()  throws IOException, URISyntaxException
    {
        itemViewer = new ItemViewer();
        gui = Mockito.mock(Gui.class);
        item = Mockito.mock(Item.class);
    }

    @Test
    void drawPlayer()  throws IOException, URISyntaxException
    {
        itemViewer.draw(gui, item);

        Mockito.verify(gui, Mockito.times(1)).drawItem(item);
    }
}