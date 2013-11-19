package org.thomasweston;

import java.util.Random;
import org.thomasweston.core.GameText;
import org.thomasweston.core.GameState;
import org.thomasweston.core.GameImage;

/**
 * Controls the main level
 * @author Thomas Weston
 */
public class RaceState extends GameState
{
    // State constants. Would normally use enums, but 
    // they aren't supported in Java ME.
    private static final int RACE_STATE_READY = 0;
    private static final int RACE_STATE_RUNNING = 1;
    private static final int RACE_STATE_FINISHED = 2;
    
    private static final int RACE_DISTANCE = 3000;
    private static final int START_PAUSE = 2500;
    private static final int FINISH_PAUSE = 2500;
    
    // current race state
    private int _raceState;
    
    // player and computer controlled athletes
    private Runner _player;
    private Runner[] _opponents;

    private GameImage _go;
    
    // counts the pause at the start and end of the race
    private long _counter;
 
    // the player's final position
    private int _position;

    // player's race time and display
    private int _time;
    private GameText _timeText;
    
    private Main _main;

    public RaceState()
    {            
        _counter = START_PAUSE;
        _raceState = RACE_STATE_READY;
        _time = 0;
    }

    public void create(Main main)
    {        
        _main = main;
        
        _displayList.addElement(new TiledBackground());
        
        _opponents = new Runner[3];
        for(int i = 0; i < _opponents.length; ++i)
        {
            _displayList.addElement(new GameImage("/assets/line.png", RACE_DISTANCE, 180 + (36 * i)));
            _opponents[i] = new Runner("/assets/opponent.png", 13 - (i * 2), (36*(4+i))+5);
            _displayList.addElement(_opponents[i]);
        }
        
        _displayList.addElement(new GameImage("/assets/line.png", RACE_DISTANCE, 180 + (36 * 3)));
        
        _player = new Runner("/assets/runner.png", 5, (36*7)+5);
        _player.maxVelocityX = 10;
        _displayList.addElement(_player);
        
        _go = new GameImage("/assets/go.png", 500, 50);
        _displayList.addElement(_go);
        
        _timeText = new GameText("00:00", 3, 3, 0x00FFFFFF);
        _displayList.addElement(_timeText);
    }
    
    public void update(long elapsed)
    {            
        super.update(elapsed);
        
        // have the camera track the player
        _main.cameraX = _player.x - 5;
        
        switch(_raceState)
        {
            case RACE_STATE_READY:
                
                _counter -= elapsed;

                // "GO!"
                if(_counter <= 0)
                    _go.x -= elapsed * 0.8f;
                
                // start race
                if(_go.x <= -_go.width)
                {
                    Key.clearPresses();
                    
                    // get the opponent racers moving
                    Random r = new Random();
                    int length = _opponents.length;
                    for(int i = 0; i < length; ++i)
                    {
                        _opponents[i].accelerationX = (r.nextFloat()) + 0.1f;
                        _opponents[i].maxVelocityX = (r.nextInt(2)) + 4;
                    }
                    
                    _displayList.removeElement(_go);
                    _raceState = RACE_STATE_RUNNING;
                }
                break;
        
            case RACE_STATE_RUNNING:
        
                _time += elapsed;
                _timeText.text = TextUtil.formatTime(_time);
                
                if(Key.getPresses() > 0)
                    _player.accelerationX += 4f;
                else 
                    _player.accelerationX = 0;

                // check for player crossing the finish line
                if(_player.x > RACE_DISTANCE)
                {
                    // work out player's finishing position
                    _position = 0;
                    int length = _opponents.length;
                    for(int i = 0; i < length; i++)
                    {
                        if(_opponents[i].x > RACE_DISTANCE)
                            _position++;
                    }
                    
                    _player.accelerationX = 0;
                    _counter = FINISH_PAUSE;
                    _raceState = RACE_STATE_FINISHED;
                }
                
                // check for opponents crossing the finish line
                int length = _opponents.length;
                for(int i = 0; i < length; ++i)
                {
                    if(_opponents[i].x > RACE_DISTANCE)
                        _opponents[i].accelerationX = 0;
                }
                break;
                
            case RACE_STATE_FINISHED:
                
                // let runners catch their breath before switching
                _counter -= elapsed;
                if(_counter < 0)
                    _main.switchState(new PodiumState(_position, _time));
                break;
                
            default:
                break;
        }
    }
}
