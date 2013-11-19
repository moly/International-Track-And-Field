package org.thomasweston.core;

import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;
import org.thomasweston.Key;

/**
 * A drawable game screen.
 * @author Thomas Weston
 */
public class Canvas extends GameCanvas
{
    private final Graphics _graphics;
            
    // screen width and height
    private final int _height;
    private final int _width;

    // initialize
    public Canvas()
    {
        super(false);
        
        _graphics = getGraphics();
        _height = getHeight();
        _width = getWidth();
    }

    // clear entire screen
    public void clear()
    {
        _graphics.setColor(0xffffff); // white
        _graphics.fillRect(0, 0, _width, _height);
    }

    // key press handler
    protected void keyPressed(int keyCode)
    {
        Key.keyPressed(keyCode);
    }

    // drawing functions
    public void draw(Sprite s)
    {
        s.paint(_graphics);
    }

    public void draw(Image image, int x, int y)
    {
        _graphics.drawImage(image, x, y, Graphics.TOP|Graphics.LEFT);
    }

    public void draw(Image image, int srcX, int srcY, int width, int height, int destX, int destY)
    {
        _graphics.drawRegion(image, srcX, srcY, width, height, Sprite.TRANS_NONE, destX, destY, Graphics.TOP|Graphics.LEFT);
    }

    public void draw(String string, int x, int y, int colour)
    {
        _graphics.setColor(colour);
        _graphics.drawString(string, x, y, Graphics.TOP|Graphics.LEFT);
    }

    public void drawRect(int x, int y, int width, int height, int colour, boolean border)
    {
        _graphics.setColor(colour);
        _graphics.fillRect(x, y, width, height);
        if(border)
        {
            _graphics.setColor(0xFF000000);
            _graphics.drawRect(x, y, width, height);
            _graphics.drawRect(x + 1, y + 1, width - 2, height - 2);
        }
    }
}
