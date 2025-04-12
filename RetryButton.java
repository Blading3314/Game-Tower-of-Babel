import greenfoot.*;

public class RetryButton extends Actor {
    private GreenfootImage defaultImage;
    private GreenfootImage hoverImage;
    private final int WIDTH = 120;  
    private final int HEIGHT = 70;

    public RetryButton() {
        defaultImage = new GreenfootImage("retry_button.png");
        hoverImage = new GreenfootImage("higlighted_retry.png");

        defaultImage.scale(WIDTH, HEIGHT);
        hoverImage.scale(WIDTH, HEIGHT);

        setImage(defaultImage);
    }

    public void act() {
        if (Greenfoot.mouseMoved(this)) {
            setImage(hoverImage);
        } else if (Greenfoot.mouseMoved(null)) {
            setImage(defaultImage);
        }

        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new Level_1()); // or your restart world
        }
    }
}
