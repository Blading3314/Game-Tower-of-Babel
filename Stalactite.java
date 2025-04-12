import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Stalactite extends Actor
{
    private int fallSpeed = 0;
    private static final int MAX_FALL_SPEED_STAGE1 = 6;
    private static final int MAX_FALL_SPEED_STAGE2 = 12;
    private boolean isStage2;
    private boolean hasHitGround = false;
    private int glowTimer = 0;
    
    public Stalactite() {
        createStalactiteImage(false); // Start with smaller size
    }
    
    private void createStalactiteImage(boolean large) {
        int width = large ? 40 : 30;
        int height = large ? 80 : 60;
        GreenfootImage img = new GreenfootImage(width, height);
        
        // Dark brown for the stalactite body
        Color darkBrown = new Color(101, 67, 33);
        // Medium brown for variation
        Color medBrown = new Color(139, 69, 19);
        // Light brown for highlights
        Color lightBrown = new Color(160, 82, 45);
        
        // Draw the main body
        img.setColor(darkBrown);
        for(int y = 0; y < height; y++) {
            int rowWidth = width - (y * width / height);
            if(rowWidth > 0) {
                img.fillRect((width - rowWidth)/2, y, rowWidth, 1);
            }
        }
        
        // Add some texture/highlights
        img.setColor(medBrown);
        for(int y = height/4; y < height*3/4; y++) {
            int rowWidth = (width - (y * width / height))/2;
            if(rowWidth > 0) {
                img.fillRect((width - rowWidth)/2, y, rowWidth/2, 1);
            }
        }
        
        setImage(img);
    }
    
    public void act()
    {
        if (getWorld() == null) return;
        
        // Check current stage
        if (getWorld() instanceof Level_2) {
            Level_2 level2 = (Level_2)getWorld();
            if (level2.isStage2 && !isStage2) {
                isStage2 = true;
                createStalactiteImage(true); // Make it larger in stage 2
            }
        }
        
        if (!hasHitGround) {
            fall();
        }
        
        // Add glowing particles effect
        glowTimer++;
        if (glowTimer % 5 == 0) {
            addGlowParticle();
        }
    }
    
    private void fall() {
        int maxFallSpeed = isStage2 ? MAX_FALL_SPEED_STAGE2 : MAX_FALL_SPEED_STAGE1;
        fallSpeed = Math.min(fallSpeed + 1, maxFallSpeed);
        
        setLocation(getX(), getY() + fallSpeed);
        
        Actor platform = getOneObjectAtOffset(0, getImage().getHeight()/2, VisiblePlatform.class);
        if (platform == null) {
            platform = getOneObjectAtOffset(0, getImage().getHeight()/2, InvisiblePlatform.class);
        }
        
        if (platform != null) {
            createImpactEffect();
            hasHitGround = true;
            getWorld().removeObject(this);
        }
        
        // Check for player collision
        Actor player = getOneIntersectingObject(MainCharacter.class);
        if (player != null && player instanceof MainCharacter) {
            ((MainCharacter)player).takeDamage();
            getWorld().removeObject(this);
        }
        
        if (getY() > getWorld().getHeight() + 50) {
            getWorld().removeObject(this);
        }
    }
    
    private void addGlowParticle() {
        if (getWorld() != null) {
            GlowParticle glow = new GlowParticle();
            getWorld().addObject(glow, 
                getX() + Greenfoot.getRandomNumber(41) - 20,
                getY() + Greenfoot.getRandomNumber(41) - 20);
        }
    }
    
    private void createImpactEffect() {
        if (getWorld() != null) {
            for (int i = 0; i < 8; i++) {
                GlowParticle particle = new GlowParticle();
                getWorld().addObject(particle, getX(), getY() + getImage().getHeight()/2);
                particle.setVelocity(
                    Greenfoot.getRandomNumber(10) - 5,
                    -(Greenfoot.getRandomNumber(5) + 2)
                );
            }
        }
    }
} 