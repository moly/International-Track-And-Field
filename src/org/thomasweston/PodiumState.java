package org.thomasweston;

import org.thomasweston.core.GameState;
import org.thomasweston.core.GameImage;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.rms.RecordStoreException;
import org.thomasweston.core.GameRect;
import org.thomasweston.core.GameText;

/**
 * Screen showing player's position on podium
 * @author Thomas Weston
 */
public class PodiumState extends GameState implements CommandListener
{    
    // Where the player finished in the race
    private final int _playePosition;
    
    // The player's race time
    private final int _playerTime;

    private GameText _newRecordText;

    private Command _restartCommand;
    private Command _scoresCommand;
    
    private Main _main;
    
    public PodiumState(int position, int time)
    {
        _playePosition = position;
        _playerTime = time;
    }
    
    public void create(Main main)
    {
        _main = main;
        
        _restartCommand = new Command("Menu", Command.SCREEN,1);
        _scoresCommand = new Command("Records", Command.SCREEN, 2);
        
        _main.addCommand(_restartCommand);
        _main.addCommand(_scoresCommand);
        
        _main.setCommandListener(this);
                
        boolean isHighScore = false;
        
        try
        {
            SaveFile save = SaveFile.open("ITF");
            int[] times = save.getTimes();
        
            // insert new time into records
            times = insertSorted(times, _playerTime);
            
            save.setTimes(times);
            
            save.close();
            
            if(_playerTime <= times[times.length - 1])
                isHighScore = true;
        }
        catch(RecordStoreException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        _displayList.addElement(new TiledBackground());
        
        _displayList.addElement(new GameRect(40, 300, 80, 60, 0xFFFFCCCC, true));
        _displayList.addElement(new GameRect(120, 270, 80, 90, 0xFFFFCCCC, true));
        _displayList.addElement(new GameRect(200, 320, 80, 40, 0xFFFFCCCC, true));
        _displayList.addElement(new GameText("2", 75, 305, 0xFFFFFFFF));
        _displayList.addElement(new GameText("1", 155, 275, 0xFFFFFFFF));
        _displayList.addElement(new GameText("3", 235, 325, 0xFFFFFFFF));
        
        _displayList.addElement(new GameImage(_playePosition == 2 ? "/assets/playend.png" : "/assets/oppend.png", 66, 254));
        _displayList.addElement(new GameImage(_playePosition == 1 ? "/assets/playend.png" : "/assets/oppend.png", 146, 224));
        _displayList.addElement(new GameImage(_playePosition == 3 ? "/assets/playend.png" : "/assets/oppend.png", 226, 274));
        
        // put on a special display if player sets a new record
        if(isHighScore)
        {     
            Confetti confetti = new Confetti(30, 4, 320, 350);
            _displayList.addElement(confetti);
            
            _newRecordText = new GameText("NEW RECORD!", 60, 100, 0x00FF0000);
            _displayList.addElement(_newRecordText);
        }
        
        _displayList.addElement(new GameText("Your Time - " + TextUtil.formatTime(_playerTime), 46, 45, 0xFFFFFF00));
    }
    
    public void destroy()
    {
        super.destroy();
        
        _main.removeCommand(_restartCommand);
        _main.removeCommand(_scoresCommand);
        
        _main.setCommandListener(null);
    }
    
    public void update(long elapsed)
    {
        super.update(elapsed);
        
        if(_newRecordText != null)
            _newRecordText.colour = ~_newRecordText.colour;
    }
    
    private int[] insertSorted(int[] array, int newTime)
    {
        for(int i = 0; i < array.length; ++i)
        {
            if(newTime < array[i])
            {
                for(int j = array.length - 2; j > i; --j)
                    array[j+1] = array[j];
                array[i] = newTime;
                break;
            }
        }
        
        return array;
    }

    public void commandAction(Command cmnd, Displayable dsplbl) 
    {
        if(cmnd.equals(_restartCommand))
            _main.switchState(new MenuState());
        else if(cmnd.equals(_scoresCommand))
            _main.switchState(new HighScoresState());
    }
}
