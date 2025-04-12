import greenfoot.*;

public class Invisible_Void extends Actor {
    public Invisible_Void() {
        GreenfootImage img = new GreenfootImage(600, 10);  // adjust width & height as needed
        img.setColor(new Color(0, 0, 0, 0));  // fully transparent
        img.fill();
        setImage(img);
    }
}
