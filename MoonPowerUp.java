import greenfoot.*;

public class MoonPowerUp extends Actor
{
    private int baseY;
    private int floatTick = 0;

    public MoonPowerUp() {
    
    }

    protected void addedToWorld(World world) {
        baseY = getY(); 
    }

    public void act()
    {
        floatTick++;
        int floatRange = 5; 
        double speed = 0.05; 

        
        int offsetY = (int)(Math.sin(floatTick * speed) * floatRange);
        setLocation(getX(), baseY + offsetY);
    }
}
