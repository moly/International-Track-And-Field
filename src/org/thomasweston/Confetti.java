package org.thomasweston;

import org.thomasweston.core.GameObject;
import org.thomasweston.core.Canvas;
import java.util.Random;

/**
 * A very simple particle system.
 * Makes a constant stream of confetti flow down the screen.
 * @author Thomas Weston
 */
public class Confetti extends GameObject
{
    private final Particle[] _particles;
    private final float _fallSpeed;
    private final int _screenHeight;
    
    public Confetti(int numParticles, float fallSpeed, int screenWidth, int screenHeight)
    {
        _particles = new Particle[numParticles];
        _fallSpeed = fallSpeed;
        _screenHeight = screenHeight;
        
        Random r = new Random();

        for(int i = 0; i < numParticles; i++)
            _particles[i] = new Particle(r.nextInt(screenWidth), -10 * i, r.nextInt(16777215));
    }
    
    public void update(long elapsed)
    {
        int length = _particles.length;
        for(int i = 0; i < length; i++)
        {
            Particle particle = _particles[i];
            particle.y += (_fallSpeed * elapsed * 0.1f);
            if((particle.y) > _screenHeight)
                particle.y = 0;
        }
    }
    
    public void draw(Canvas canvas, int cameraX, int cameraY)
    {
        int length = _particles.length;
        for(int i = 0; i < length; i++)
            canvas.drawRect(_particles[i].x - cameraX, _particles[i].y - cameraY, 3, 3, _particles[i].colour, false);
    }
}

// a struct for the confetti
class Particle
{
    public int x, y, colour;
    public Particle(int x, int y, int colour)
    {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }
}