package org.thomasweston;

import org.thomasweston.core.GameSprite;

/**
 * Player controlled runner
 * @author Thomas Weston
 */
public class Runner extends GameSprite
{
    public float velocityX;
    public float accelerationX;
    public float maxVelocityX;

    public Runner(String image, int x, int y)
    {
        super(image, 32, 49, x, y);
        velocityX = 0;
        accelerationX = 0;
        maxVelocityX = 0;
    }

    public void update(long elapsed)
    {
        velocityX += accelerationX;
        
        if(velocityX > maxVelocityX)
            velocityX = maxVelocityX;
        
        // drag
        if(accelerationX == 0)
            velocityX *= 0.9f;
        
        // d = s * t;
        x += (int)(velocityX * (elapsed * 0.1f));

        if(velocityX > 0.1f)
            nextFrame();
        else
            setFrame(0);
    }
}
