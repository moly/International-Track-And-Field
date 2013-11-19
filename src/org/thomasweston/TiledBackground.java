package org.thomasweston;

import org.thomasweston.core.GameObject;
import org.thomasweston.core.Canvas;
import java.io.IOException;
import javax.microedition.lcdui.Image;

/**
 * 
 * @author Thomas Weston
 */
public class TiledBackground extends GameObject
{
    private final Image[] _tiles;

    private final int _widthInTiles;
    private final int _heightInTiles;
    private final int _tileWidth;
    private final int _tileHeight;
    
    public TiledBackground()
    {
        _widthInTiles = 4;
        _heightInTiles = 10;
        
        _tiles = new Image[_heightInTiles];

        Image grandstandImage;
        Image grassImage;
        Image trackImage;
        Image fenceImage;
        
        try
        {
            grandstandImage = Image.createImage("/assets/grandstand.png");
            grassImage = Image.createImage("/assets/grass.png");
            trackImage = Image.createImage("/assets/track.png");
            fenceImage = Image.createImage("/assets/fence.png");
        }
        catch(IOException ex)
        {
            throw new RuntimeException(ex.getMessage());
        }
        
        _tileWidth = grandstandImage.getWidth();
        _tileHeight = grandstandImage.getHeight();
        
        _tiles[0] = grandstandImage;
        _tiles[1] = grandstandImage;
        _tiles[2] = grandstandImage;
        _tiles[3] = fenceImage;
        _tiles[4] = grassImage;
        _tiles[5] = trackImage;
        _tiles[6] = trackImage;
        _tiles[7] = trackImage;
        _tiles[8] = trackImage;
        _tiles[9] = grassImage;
    }
    
    public void draw(Canvas canvas, int cameraX, int cameraY)
    {
        for(int x = 0; x < _widthInTiles; x++)
        {
            for(int y = 0; y < _heightInTiles; y++)
                canvas.draw(_tiles[y], (x * _tileWidth) - (cameraX  % _tileWidth), (y * _tileHeight) - (cameraY % _tileHeight));
        }
    }

    public void update(long elapsed)
    {
        
    }
}
