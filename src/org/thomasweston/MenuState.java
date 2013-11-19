package org.thomasweston;

import org.thomasweston.core.GameState;
import org.thomasweston.core.GameImage;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;

/**
 * Game menu
 * @author Thomas Weston
 */
public class MenuState extends GameState implements CommandListener
{
    Main _main;
   
    // Menu options
    private Command _startCommand;
    private Command _scoresCommand;
 
    public void create(Main main)
    {   
        _main = main;
        
        _startCommand = new Command("Start", Command.SCREEN, 1);
        _scoresCommand = new Command("Records", Command.SCREEN, 2);
        
        _main.addCommand(_startCommand);
        _main.addCommand(_scoresCommand);
        
        _main.setCommandListener(this);
        
        _displayList.addElement(new TiledBackground());
        _displayList.addElement(new GameImage("/assets/title.png", 10, 120));
    }
    
    public void destroy()
    {
        super.destroy();
        
        _main.removeCommand(_startCommand);
        _main.removeCommand(_scoresCommand);
        
        _main.setCommandListener(null);
    }

    public void commandAction(Command cmnd, Displayable dsplbl) 
    {
        if(cmnd.equals(_startCommand))
            _main.switchState(new RaceState());

        else if(cmnd.equals(_scoresCommand))
            _main.switchState(new HighScoresState());
    }
}
