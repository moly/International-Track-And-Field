package org.thomasweston;

import org.thomasweston.core.GameText;
import org.thomasweston.core.GameState;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.rms.RecordStoreException;
import org.thomasweston.core.GameRect;

/**
 * Display high scores
 * @author Thomas Weston
 */
public class HighScoresState extends GameState implements CommandListener
{
    private Main _main;
    
    private Command _restartCommand;
        
    public void create(Main main)
    {
        _main = main;
        
        _restartCommand = new Command("Menu", Command.SCREEN,1);        
        _main.addCommand(_restartCommand);
        
        _main.setCommandListener(this);
        
        _displayList.addElement(new TiledBackground());
        _displayList.addElement(new GameRect(100, 40, 130, 200, 0xFFFFFFFF, true));
                
        int[] times = null;
        
        try 
        {
            SaveFile save = SaveFile.open("ITF");
            times = save.getTimes();
            save.close();
        }
        catch(RecordStoreException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        if(times == null)
            return;
        
        for(int i = 0; i < times.length; ++i)
            _displayList.addElement(new GameText(TextUtil.formatTime(times[i]), 130, 50 + (i * 36)));
    }
    
    public void destroy()
    {
        super.destroy();
        
        _main.removeCommand(_restartCommand);
        
        _main.setCommandListener(null);
    }

    public void commandAction(Command cmnd, Displayable dsplbl) 
    {
        if(cmnd.equals(_restartCommand))
            _main.switchState(new MenuState());
    }
}
