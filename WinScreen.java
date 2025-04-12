import greenfoot.*;

public class WinScreen extends World {
    public static GreenfootSound winMusic;
    private boolean musicStarted = false;
    private int level;
    private int victoryDelay = 0;
    private static final int VICTORY_DELAY_TIME = 100;  // Adjust this value to match music length

    public WinScreen(int level) {
        super(600, 400, 1);  // Set the screen size
        this.level = level;
        
        // Stop all other music
        if (Level_1.ambientMusic != null) {
            Level_1.ambientMusic.stop();
        }
        if (Level_2.ambientMusic != null) {
            Level_2.ambientMusic.stop();
        }
        if (GameOver.gameOverMusic != null) {
            GameOver.gameOverMusic.stop();
        }
        if (winMusic != null) {
            winMusic.stop();
        }
        
        // Initialize appropriate music based on level
        if (level == 2) {
            // Victory music for completing the game
            winMusic = new GreenfootSound("Victory Video Game Music.mp3");
            showText("Congratulations on beating the game prototype!", getWidth()/2, getHeight()/2 - 50);
        } else {
            // Regular level completion music
            winMusic = new GreenfootSound("8 Bit Dungeon Music.mp3");
            showText("Level " + level + " Complete!", getWidth()/2, getHeight()/2 - 50);
        }
        
        prepare();
    }

    public void act() {
        if (!musicStarted && winMusic != null) {
            if (level == 2) {
                winMusic.play();  // Play once for victory
                removeObjects(getObjects(ContinueButton.class));  // Remove continue button until music finishes
            } else {
                winMusic.playLoop();  // Loop for regular level completion
            }
            musicStarted = true;
        }

        // For level 2, only show continue button after victory music has played
        if (level == 2 && musicStarted) {
            if (victoryDelay < VICTORY_DELAY_TIME) {
                victoryDelay++;
            } else if (getObjects(ContinueButton.class).isEmpty()) {
                ContinueButton continueButton = new ContinueButton();
                addObject(continueButton, getWidth()/2, getHeight()/2 + 20);
                continueButton.setLevel(level);
            }
        }
    }

    public void stopped() {
        if (winMusic != null) {
            winMusic.stop();
            musicStarted = false;
        }
    }

    private void prepare() {
        if (level != 2) {  // Only add continue button immediately for level 1
            ContinueButton continueButton = new ContinueButton();
            addObject(continueButton, getWidth()/2, getHeight()/2 + 20);
            continueButton.setLevel(level);
        }
    }
}
