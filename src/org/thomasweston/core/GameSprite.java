package org.thomasweston.core;

/**
 * An animated game image.
 * @author Thomas
 */
public class GameSprite extends GameImage
{
    private int _frameX;
    private int _frameY;
    private int _frameWidth;
    private int _frameHeight;
    private int _sheetWidth;

    public GameSprite(String image, int frameWidth, int frameHeight)
    {    
        this(image, frameWidth, frameHeight, 0, 0);
    }
    
    public GameSprite(String image, int frameWidth, int frameHeight, int x, int y)
    {    
        super(image, x, y);
        _frameWidth = frameWidth;
        _frameHeight = frameHeight;
        _frameX = 0;
        _frameY = 0;
        _sheetWidth = _image.getWidth();
    }
    
    public void draw(Canvas canvas, int cameraX, int cameraY)
    {
        canvas.draw(_image, _frameX, _frameY, _frameWidth, _frameHeight, x - cameraX, y - cameraY);
    }

    public void nextFrame()
    {
        _frameX += _frameWidth;
        if (_frameX >= _sheetWidth)
            _frameX = 0;
    }

    public void setFrame(int frame)
    {
        _frameX = frame * _frameWidth;
    }
}
