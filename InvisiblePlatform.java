import greenfoot.*;

public class InvisiblePlatform extends Actor
{
    public InvisiblePlatform() {
        // Create a transparent platform image
        GreenfootImage img = new GreenfootImage(100, 20);  // More reasonable size
        img.setColor(new Color(0, 0, 0, 0));  // Fully transparent
        img.fill();  // Fill with transparent color
        setImage(img);  // Set the image to the actor
    }
}