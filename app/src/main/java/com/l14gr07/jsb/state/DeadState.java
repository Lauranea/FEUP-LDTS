package com.l14gr07.jsb.state;

import com.l14gr07.jsb.Gui;
import com.l14gr07.jsb.controller.Controller;
import com.l14gr07.jsb.controller.dead.DeadAnimController;
import com.l14gr07.jsb.model.DeadAnim;
import com.l14gr07.jsb.viewer.Viewer;
import com.l14gr07.jsb.viewer.dead.DeadAnimViewer;

import java.io.IOException;
import java.net.URISyntaxException;

public class DeadState extends State<DeadAnim>
{
    public DeadState(DeadAnim model)  throws IOException, URISyntaxException
    {
        super(model);
    }

    @Override
    protected Viewer<DeadAnim> getViewer()
    {
        return new DeadAnimViewer(getModel());
    }

    @Override
    protected Controller<DeadAnim> getController() throws IOException, URISyntaxException
    {
        return new DeadAnimController(getModel());
    }
}
