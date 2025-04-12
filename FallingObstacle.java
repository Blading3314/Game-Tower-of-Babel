import greenfoot.*;
import java.util.Random;

public class FallingObstacle extends Actor {
    private int speed;
    private boolean isBig;

    public FallingObstacle() {
        Random rand = new Random();
        isBig = rand.nextInt(100) < 40; // 50% chance it's a big obstacle

        if (isBig) {
            GreenfootImage img = new GreenfootImage("biggest_fragment_obstaclelvl1.PNG");
            img.scale(img.getWidth() - 10, img.getHeight() - 10); // smaller hitbox
            setImage(img);
            speed = rand.nextInt(10) + 6;
        } else {
            GreenfootImage img = new GreenfootImage("fragment_obstaclelvl1.PNG");
            img.scale(img.getWidth() - 12, img.getHeight() - 12); // smaller hitbox
            setImage(img);
            speed = rand.nextInt(12) + 6;
        }
    }

    public void act() {
        if (getWorld() == null) 
        return;

        setLocation(getX(), getY() + speed);

    
        if (isTouching(ObstacleDestroyer.class)) {
       getWorld().removeObject(this);
       return;
      }

    }
}
