package org.thomasweston.core;

/**
 * Draws a rectangle to the screen.
 * @author Thomas Weston
 */
public class GameRect extends GameObject
{
    public int colour;
    public boolean border;
    
    public GameRect(int x, int y, int width, int height, int colour, boolean border)
    {
        super(x, y, width, height);
        this.colour = colour;
        this.border = border;
    }
    
    public void update(long elapsed)
    {
    }

    public void draw(Canvas canvas, int cameraX, int cameraY) 
    {
        canvas.drawRect(x - cameraX, y - cameraY, width, height, colour, border);
    }
}
