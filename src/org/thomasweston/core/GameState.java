package org.thomasweston.core;

import org.thomasweston.core.GameObject;
import java.util.Enumeration;
import java.util.Vector;
import org.thomasweston.Main;

/**
 * Base class for all game states.
 * @author Thomas Weston
 */
public abstract class GameState
{
    // Java ME doesn't support generics or ArrayLists.
    // Vector is best alternative.
    protected Vector _displayList;

    public GameState()
    {
        _displayList = new Vector();
    }

    public abstract void create(Main main);
    
    public void destroy()
    {
        _displayList.removeAllElements();
        _displayList = null;
    }
    
    public void update(long elapsed)
    {
        Enumeration vEnum = _displayList.elements();
        while (vEnum.hasMoreElements())
            ((GameObject)vEnum.nextElement()).update(elapsed);
    }

    public void draw(Canvas canvas, int cameraX, int cameraY)
    {
        Enumeration vEnum = _displayList.elements();
        while (vEnum.hasMoreElements())
            ((GameObject)vEnum.nextElement()).draw(canvas, cameraX, cameraY);
    }
}
