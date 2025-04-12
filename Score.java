import greenfoot.*;

public class Score extends Actor {
    private int score = 0;

    public Score() {
        updateImage();
    }

    public void addPoints(int value) {
        score += value;
        updateImage();
    }

    private void updateImage() {
        setImage(new GreenfootImage("Score: " + score, 24, Color.WHITE, new Color(0, 0, 0, 128)));
    }
}