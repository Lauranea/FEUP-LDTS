package jet.set.billy.state;

import jet.set.billy.Gui;

public class State
{
    MenuState menuState;
    GameState gameState;

    public State() throws Exception
    {
        menuState = new MenuState();
        gameState = new GameState();
    }

    public void step(Gui gui) throws Exception
    {
        gameState.step(gui);
    }
}
