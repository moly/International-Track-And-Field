package org.thomasweston.core;

/**
 * The base class for all game entities.
 * @author Thomas Weston
 */
public abstract class GameObject
{
    public int x;
    public int y;
    public int width;
    public int height;

    public GameObject(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public GameObject(int x, int y)
    {
        this(x, y, 0, 0);
    }
    
    public GameObject()
    {
        this(0, 0);
    }
    
    public abstract void update(long elapsed);

    public abstract void draw(Canvas canvas, int cameraX, int cameraY);
}
