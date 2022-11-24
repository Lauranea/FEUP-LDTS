package jet.set.billy;

import java.util.Arrays;
import java.util.Vector;

public class World
{
    Vector<Vector<String>> rooms = new Vector<Vector<String>>(Arrays.asList
    (
        new Vector<String>(Arrays.asList("Corridor", "Bathroom")),
        new Vector<String>(Arrays.asList("Kitchen", "Winery"))
    ));

    Room current_room = new Room( "Corridor");

    public Room get_current_room()
    {
        return current_room;
    }
}
