package org.thomasweston.core;

import java.io.IOException;
import javax.microedition.lcdui.Image;

/**
 * Loads and displays a simple image.
 * @author Thomas Weston
 */
public class GameImage extends GameObject
{
    protected Image _image;

    public GameImage(String image)
    {
        this(image, 0, 0);
    }

    public GameImage(String image, int x, int y)
    {
        super(x, y);
        
        try 
        {
            _image = Image.createImage(image);
        } 
        catch (IOException ex) 
        {
            throw new RuntimeException(ex.getMessage());
        }
        
        width = _image.getWidth();
        height = _image.getHeight();
    }
    
    public void update(long elapsed)
    {
        
    }

    public void draw(Canvas canvas, int cameraX, int cameraY)
    {
        canvas.draw(_image, x - cameraX, y - cameraY);
    }
}