import greenfoot.*;

public class SplittingObstacle extends Actor {
    private int size;
    private int stability;
    private double velocityY;
    private double velocityX;
    
    public SplittingObstacle() {
        this(40);  // Default size
    }
    
    public SplittingObstacle(int size) {
        this(size, 0, 2);  // Default vertical speed of 2
    }
    
    public SplittingObstacle(int size, double vx, double vy) {
        this.size = size;
        this.stability = size;
        this.velocityX = vx;
        this.velocityY = vy;
        
        // Create and scale the image
        GreenfootImage img = new GreenfootImage("barrel.png");
        img.scale(size, size);
        setImage(img);
    }
    
    public void act() {
        // Move according to velocity
        setLocation(getX() + (int)velocityX, getY() + (int)velocityY);
        
        // Check for collisions with MainCharacter
        Actor character = getOneIntersectingObject(MainCharacter.class);
        if (character != null) {
            hit(10);  // Take damage when hitting the player
        }
        
        // Remove if off screen
        if (getY() > getWorld().getHeight() + 50) {
            getWorld().removeObject(this);
        }
    }
    
    public void hit(int damage) {
        stability -= damage;
        if (stability <= 0) {
            breakUp();
        }
    }
    
    private void breakUp() {
        // Play explosion sound
        GreenfootSound explosionSound = new GreenfootSound("Explosion.wav");
        explosionSound.setVolume(70);
        explosionSound.play();
        
        if (size <= 20) {
            // If too small, just disappear
            getWorld().removeObject(this);
            return;
        }
        
        // Create two smaller obstacles with angled trajectories
        int newSize = size / 2;
        double speed = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        
        // Left piece
        SplittingObstacle left = new SplittingObstacle(newSize, 
            -speed * 0.7, -Math.abs(speed * 0.7));  // Always bounce upward
        getWorld().addObject(left, getX() - 10, getY());
        
        // Right piece
        SplittingObstacle right = new SplittingObstacle(newSize, 
            speed * 0.7, -Math.abs(speed * 0.7));  // Always bounce upward
        getWorld().addObject(right, getX() + 10, getY());
        
        // Remove this obstacle
        getWorld().removeObject(this);
    }
    
    public int getStability() {
        return stability;
    }
} 