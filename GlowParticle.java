import greenfoot.*;

public class GlowParticle extends Actor
{
    private int lifetime = 30;
    private int vx = 0;
    private int vy = 0;
    
    public GlowParticle() {
        createGlowImage();
    }
    
    private void createGlowImage() {
        GreenfootImage img = new GreenfootImage(12, 12);
        
        // Bright yellow center
        Color centerColor = new Color(255, 223, 0);
        // Orange outer
        Color outerColor = new Color(255, 140, 0);
        
        // Draw outer glow
        img.setColor(outerColor);
        img.fillRect(2, 2, 8, 8);
        
        // Draw inner bright center
        img.setColor(centerColor);
        img.fillRect(4, 4, 4, 4);
        
        setImage(img);
    }
    
    public void setVelocity(int x, int y) {
        vx = x;
        vy = y;
    }
    
    public void act() {
        setLocation(getX() + vx, getY() + vy);
        if (vy != 0) { // If it's a moving particle
            vy += 1; // Apply gravity
        }
        
        lifetime--;
        if (lifetime <= 0) {
            if (getWorld() != null) {
                getWorld().removeObject(this);
                return;
            }
        }
        
        // Fade out
        getImage().setTransparency((int)((lifetime / 30.0) * 255));
    }
} 