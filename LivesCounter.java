import greenfoot.*;

public class LivesCounter extends Actor {
    private int lives;

    public LivesCounter(int startLives) {
        lives = startLives;
        updateImage();
    }

    public void setLives(int lives) {
        this.lives = lives;
        updateImage();
    }

    private void updateImage() {
        GreenfootImage img = new GreenfootImage("Lives: " + lives, 24, Color.WHITE, new Color(0, 0, 0, 100));
        setImage(img);
    }
}
