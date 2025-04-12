import greenfoot.*;

public class GameOver extends World {
    public static GreenfootSound gameOverMusic;
    private boolean musicStarted = false;

    public GameOver() {
        super(600, 400, 1);
        
        // Initialize game over music
        if (gameOverMusic == null) {
            gameOverMusic = new GreenfootSound("Game Over 8-Bit Chiptune.mp3");
        }
        
        showText("GAME OVER", getWidth() / 2, getHeight() / 2 - 50);
        prepare();
    }

    public void act() {
        if (!musicStarted) {
            if (gameOverMusic != null) {
                gameOverMusic.play();
                musicStarted = true;
            }
        }
    }

    public void stopped() {
        if (gameOverMusic != null) {
            gameOverMusic.stop();
            musicStarted = false;
        }
    }

    private void prepare() {
        addObject(new RetryButton(), getWidth() / 2, getHeight() / 2 + 20);
    }
}
