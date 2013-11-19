package org.thomasweston;

import org.thomasweston.core.GameState;
import org.thomasweston.core.Canvas;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * Entry class.
 * @author Thomas Weston
 */
public class Main extends MIDlet implements Runnable
{
    private Thread _gameThread;
    private Canvas _canvas;
    private long _lastTime;
    private volatile boolean _running;
    private boolean _created;

    private GameState _currentState;
    private GameState _newState;
    
    // camera position
    public int cameraX;
    public int cameraY;

    public Main()
    {
        _created = false;
        cameraX = 0;
        cameraY = 0;
    }
    
    private void create()
    {
        _created = true;
        
        _canvas = new Canvas();
        Display display = Display.getDisplay(this);
        display.setCurrent(_canvas);
    
        switchState(new MenuState());

        _gameThread = new Thread(this);
    }

    public void startApp()
    {
        if(!_created)
            create();
        
        _running = true;
        _gameThread.start();
    }

    public void pauseApp()
    {
        _running = false;
    }

    public void destroyApp(boolean unconditional) 
    {
        _running = false;
        _currentState.destroy();
       _currentState = null;
       _canvas = null;
    }

    public void addCommand(Command command)
    {
        _canvas.addCommand(command);
    }
    
    public void removeCommand(Command command)
    {
        _canvas.removeCommand(command);
    }
    
    public void setCommandListener(CommandListener cmndListener)
    {
        _canvas.setCommandListener(cmndListener);
    }

    public void switchState(GameState state)
    {
        _newState = state;
    }
    
    public void run()
    {
        _lastTime = System.currentTimeMillis();

        while(_running)
        {
            // work out how long has passed since the last frame
            long elapsed = System.currentTimeMillis() - _lastTime;
            _lastTime = System.currentTimeMillis();
            
            // check if the state has been switched
            if(!_newState.equals(_currentState))
            {
                if(_currentState != null)
                    _currentState.destroy();
                _currentState = _newState;
                _currentState.create(this);
                cameraX = cameraY = 0;
            }
            
            _currentState.update(elapsed);

            // draw the current state
            _canvas.clear();
            _currentState.draw(_canvas, cameraX, cameraY);
            _canvas.flushGraphics();            
        }

    }
}