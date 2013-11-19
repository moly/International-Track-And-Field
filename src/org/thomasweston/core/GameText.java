package org.thomasweston.core;

/**
 * A basic textfield.
 * @author Thomas Weston
 */
public class GameText extends GameObject
{
    public String text;
    public int colour;
    
    public GameText(String text, int x, int y, int colour)
    {
        super(x, y);
        this.text = text;
        this.colour = colour;
    }
    
    public GameText(String text, int x, int y)
    {
        this(text, x, y, 0xFF000000);
    }
    
    public void update(long elapsed)
    {
    }

    public void draw(Canvas canvas, int cameraX, int cameraY)
    {
        canvas.draw(text, x, y, colour);
    }
}
