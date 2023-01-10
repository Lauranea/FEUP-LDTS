package com.l14gr07.jsb.viewer.dead;

import java.io.IOException;
import java.net.URISyntaxException;

import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.model.DeadAnim;
import com.l14gr07.jsb.viewer.Viewer;

public class DeadAnimViewer extends Viewer<DeadAnim>
{
    public DeadAnimViewer(DeadAnim model)
    {
        super(model);
    }

    public void step(Gui gui) throws IOException, URISyntaxException
    {
        for (int i = 0; i < 25; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                gui.drawImage(i*10, j*10 + 136, getModel().getCo());
            }
        }
        gui.drawImage(121, 120, getModel().getPo());
        gui.drawImage(119, getModel().get_fo_pos(), getModel().getFo());
    }
}
