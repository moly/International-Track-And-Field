package org.thomasweston;


/**
 * Helper functions for working with text.
 * @author Thomas Weston
 */
public class TextUtil
{    
    /**
     * Formats a time (in seconds) to a string "seconds:milliseconds".
     */
    public static String formatTime(int time)
    {  
        int seconds = time / 1000;
        return (seconds < 10 ? "0" : "") + seconds + ":" + (time % 1000) / 10;   
    }
}
