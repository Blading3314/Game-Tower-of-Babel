import greenfoot.*;

public class BarrelPiece extends Actor {
    private int vx = 0;
    private int vy = 0;
    private int gravity = 1;
    private int lifetime = 60;  // Pieces disappear after 1 second
    private boolean hasHitPlayer = false;  // Track if piece has already hit player
    
    public BarrelPiece() {
        // Create a new image for the barrel piece
        GreenfootImage image = new GreenfootImage(20, 20);
        
        // Set brown colors for the barrel piece
        Color darkBrown = new Color(139, 69, 19);
        Color lightBrown = new Color(160, 82, 45);
        
        // Fill the background
        image.setColor(darkBrown);
        image.fillRect(0, 0, 20, 20);
        
        // Add some detail with a lighter shade
        image.setColor(lightBrown);
        image.fillRect(2, 2, 16, 8);
        
        // Add a highlight
        image.setColor(Color.WHITE);
        image.fillRect(4, 4, 4, 2);
        
        setImage(image);
    }
    
    public void setVelocity(int x, int y) {
        vx = x;
        vy = y;
    }
    
    public void act() {
        if (getWorld() == null) return;  // Safety check
        
        // Update position
        setLocation(getX() + vx, getY() + vy);
        
        // Apply gravity
        vy += gravity;
        
        // Rotate piece for visual effect
        turn(5);
        
        // Check for player collision if we haven't hit the player yet
        if (!hasHitPlayer) {
            MainCharacter player = (MainCharacter)getOneIntersectingObject(MainCharacter.class);
            if (player != null) {
                // Deal damage to player
                player.takeDamage();
                hasHitPlayer = true;
                
                // Create hit effect
                GreenfootImage img = getImage();
                img.setTransparency(128);  // Make piece semi-transparent on hit
                
                // Remove after a short delay
                lifetime = 5;  // Remove quickly after hitting player
                return;
            }
        }
        
        // Remove if off screen
        if (getX() < 0 || getX() > getWorld().getWidth() || 
            getY() < 0 || getY() > getWorld().getHeight()) {
            getWorld().removeObject(this);
            return;
        }
        
        // Decrease lifetime and remove if expired
        lifetime--;
        if (lifetime <= 0) {
            getWorld().removeObject(this);
            return;
        }
        
        // Fade out gradually
        if (lifetime < 30) {
            getImage().setTransparency(lifetime * 8);  // Will go from 240 to 0
        }
    }
} 