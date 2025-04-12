import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Level_3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level_3 extends World
{

    /**
     * Constructor for objects of class Level_3.
     * 
     */
    public Level_3(String message)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
        
        // Stop all music from previous levels
        if (WinScreen.winMusic != null) {
            WinScreen.winMusic.stop();
        }
        if (Level_1.ambientMusic != null) {
            Level_1.ambientMusic.stop();
        }
        if (Level_2.ambientMusic != null) {
            Level_2.ambientMusic.stop();
        }
        
        // Display the congratulations message
        showText(message, getWidth()/2, getHeight()/2);
    }
}
