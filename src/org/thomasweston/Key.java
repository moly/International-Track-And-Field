package org.thomasweston;

/**
 * Gathers information on key presses
 * @author Thomas Weston
 */
public class Key 
{
    private static int presses = 0;
    private static int lastKey = 0;

    public static void keyPressed(int keyCode)
    {
        if(keyCode != lastKey)
        {
            presses++;
            lastKey = keyCode;
        }
    }

    // return number of presses since last request
    public static int getPresses()
    {
        int p = presses;
        presses = 0;
        return p;
    }

    public static void clearPresses()
    {
        presses = 0;
    }

}
