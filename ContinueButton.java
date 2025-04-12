import greenfoot.*;

public class ContinueButton extends Actor {
    private int currentLevel = 1;
    
    public ContinueButton() {
        GreenfootImage buttonImage = new GreenfootImage(100, 30);
        buttonImage.setColor(Color.GREEN);
        buttonImage.fill();
        buttonImage.setColor(Color.BLACK);
        buttonImage.drawString("Continue", 20, 20);
        setImage(buttonImage);
    }
    
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            // Stop win music
            if (WinScreen.winMusic != null) {
                WinScreen.winMusic.stop();
            }
            
            // Transition to appropriate level
            if (currentLevel == 1) {
                Greenfoot.setWorld(new Level_2());
            } else if (currentLevel == 2) {
                // Game is complete, return to welcome screen
                Greenfoot.setWorld(new WelcomeScreen());
            }
        }
    }
    
    public void setLevel(int level) {
        this.currentLevel = level;
    }
}
