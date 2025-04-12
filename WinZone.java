import greenfoot.*;


import greenfoot.Color;

public class WinZone extends Actor {
    private boolean enabled = true;
    private int messageTimer = 0;
    
    public WinZone() {
        // Create a transparent image
        GreenfootImage img = new GreenfootImage(50, 50);
        img.setColor(new Color(0, 0, 0, 0));  // Fully transparent
        img.fill();
        setImage(img);
        enabled = false;
    }
    
    public void act() {
        if (messageTimer > 0) {
            messageTimer--;
            if (messageTimer == 0) {
                getWorld().showText("", getWorld().getWidth()/2, 50);
            }
        }
        
        MainCharacter player = (MainCharacter)getOneIntersectingObject(MainCharacter.class);
        if (player != null) {
            if (!enabled) {
                showMessage("Not open yet!");
            }
        }
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        // Visual feedback - make it slightly transparent when disabled
        getImage().setTransparency(enabled ? 255 : 128);
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    private void showMessage(String text) {
        if (messageTimer <= 0) {  // Only show new message if previous one is done
            getWorld().showText(text, getWorld().getWidth()/2, 50);
            messageTimer = 120;  // Show for 2 seconds
        }
    }
}