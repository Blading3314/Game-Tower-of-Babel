import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WelcomeScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WelcomeScreen extends World
{
    double timeWelcomeScreenCreation = System.currentTimeMillis();
    public static GreenfootSound introMusic = new GreenfootSound("Waiting Time Music.mp3");

    public WelcomeScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(460, 460, 1); 

        showText("Press Space Bar", getWidth()/2, 20);
       
        // Stop any existing music first
        if (introMusic != null) {
            introMusic.stop();
        }
        if (Level_1.ambientMusic != null) {
            Level_1.ambientMusic.stop();
        }
        if (GameOver.gameOverMusic != null) {
            GameOver.gameOverMusic.stop();
        }
        
        // Start intro music
        introMusic = new GreenfootSound("Waiting Time Music.mp3");
        introMusic.playLoop(); 
        prepare();
    }

    public void act()
    {
        if (Greenfoot.isKeyDown("space"))
        {
            introMusic.stop();  // Stop music before changing world
            Greenfoot.setWorld(new Level_1()); 
        }
    }

    public void started() {
        if (introMusic != null) {
            introMusic.playLoop();
        }
    }

    public void stopped() {
        if (introMusic != null) {
            introMusic.stop();
        }
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}
