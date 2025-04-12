import greenfoot.*;
import java.util.List;
public class MainCharacter extends Actor {
    private int ySpeed = 0;
    private boolean onGround = false;
    private GreenfootImage[] runFrames = new GreenfootImage[4];
    private GreenfootImage[] runLeftFrames = new GreenfootImage[4];
    private int frame = 0;
    private int animationDelay = 5;
    private int delayCount = 0;
    private boolean facingRight = true;
    private boolean climbing = false;
    private int climbSpeed = 2;

    private double gravityMultiplier = 1.0;
    private int moonGravityTimer = 0;
    private final int MOON_GRAVITY_DURATION = 600;

    private boolean fadingOut = false;
    private int fadeTimer = 0;
    private int lives = 4;
    private LivesCounter livesCounter;

    private boolean invulnerable = false;
    private int graceTimer = 0;

    public MainCharacter() {
        for (int i = 0; i < runFrames.length; i++) {
            runFrames[i] = new GreenfootImage("run" + (i + 1) + ".png");
            runLeftFrames[i] = new GreenfootImage("left" + (i + 1) + ".png");
        }
        setImage(runFrames[0]);
    }

    public void act() {
        if (fadingOut) {
            handleFading();
            return;
        }

        if (invulnerable) {
            graceTimer--;
            if (graceTimer <= 0) {
                invulnerable = false;
                getImage().setTransparency(255);
            }
        }

        checkWinZone();
        applyGravity();
        checkKeys();
        checkPowerUp();
        checkVoid();
        handleClimbing();
        checkHitByObstacle();
    }

    private void animateRun() {
        delayCount++;
        if (delayCount >= animationDelay) {
            delayCount = 0;
            frame = (frame + 1) % runFrames.length;
            setImage(facingRight ? runFrames[frame] : runLeftFrames[frame]);
        }
    }

    private void checkKeys() {
        boolean isMoving = false;

        if (Greenfoot.isKeyDown("a")) {
            move(-4);
            isMoving = true;
            facingRight = false;
        }

        if (Greenfoot.isKeyDown("d")) {
            move(4);
            isMoving = true;
            facingRight = true;
        }

        if (Greenfoot.isKeyDown("space") && onGround) {
            ySpeed = -12;
            onGround = false;
            // Small boost to ensure clean platform separation
            setLocation(getX(), getY() - 1);
        }

        if (isMoving) {
            animateRun();
        } else {
            setImage(facingRight ? runFrames[0] : runLeftFrames[0]);
            frame = 0;
            delayCount = 0;
        }
    }

    private void checkWinZone() {
        @SuppressWarnings("unchecked")
        List touching = getObjectsInRange(20, WinZone.class);
        if (!touching.isEmpty()) {
            Object obj = touching.get(0);
            if (obj instanceof WinZone && ((WinZone)obj).isEnabled()) {
                // Determine current level
                World world = getWorld();
                int currentLevel = (world instanceof Level_1) ? 1 : 2;
                Greenfoot.setWorld(new WinScreen(currentLevel));
            }
        }
    }

    private void checkPowerUp() {
        @SuppressWarnings("unchecked")
        List touching = getObjectsInRange(20, MoonPowerUp.class);
        if (!touching.isEmpty()) {
            Object obj = touching.get(0);
            if (obj instanceof Actor) {
                gravityMultiplier = 0.5;
                moonGravityTimer = MOON_GRAVITY_DURATION;
                getWorld().removeObject((Actor)obj);
            }
        }
    }

    private void applyGravity() {
        if (!climbing) {
            ySpeed++;
            int oldY = getY();
            int oldX = getX();
            setLocation(getX(), getY() + (int)(ySpeed * gravityMultiplier));
            
            Actor platform = getPlatformBelow();
            if (platform != null) {
                // If we're moving downward and there's a platform below
                if (ySpeed >= 0) {
                    // Place character precisely on top of platform
                    setLocation(getX(), platform.getY() - platform.getImage().getHeight()/2 
                              - getImage().getHeight()/2);
                    ySpeed = 0;
                    onGround = true;
                }
            } else {
                onGround = false;
                
                // Check if we're stuck in a platform (for cases where we might have moved through one)
                if (isTouching(VisiblePlatform.class) || isTouching(InvisiblePlatform.class)) {
                    // Move back to previous position
                    setLocation(oldX, oldY);
                    ySpeed = 0;
                    onGround = true;
                }
            }
            
            // Check for side collisions with platforms
            checkSidePlatformCollision();
        }

        // Apply moon gravity effect
        if (moonGravityTimer > 0) {
            moonGravityTimer--;
            if (moonGravityTimer == 0) {
                gravityMultiplier = 1.0;
            }
        }

        // Check for falling off the world
        if (getY() > getWorld().getHeight()) {
            if (Level_1.ambientMusic != null) {
                Level_1.ambientMusic.stop();
            }
            if (Level_2.ambientMusic != null) {
                Level_2.ambientMusic.stop();
            }
            Greenfoot.setWorld(new GameOver());
        }
    }

    private void checkSidePlatformCollision() {
        int width = getImage().getWidth() / 2;
        int height = getImage().getHeight() / 2;
        
        @SuppressWarnings("unchecked")
        List platforms = getObjectsInRange(width + 5, VisiblePlatform.class);
        platforms.addAll(getObjectsInRange(width + 5, InvisiblePlatform.class));
        
        for (Object obj : platforms) {
            Actor platform = (Actor)obj;
            // Check if platform is to the right
            if (platform.getX() > getX() && Math.abs(platform.getY() - getY()) < height) {
                setLocation(platform.getX() - platform.getImage().getWidth()/2 - width, getY());
            }
            // Check if platform is to the left
            else if (platform.getX() < getX() && Math.abs(platform.getY() - getY()) < height) {
                setLocation(platform.getX() + platform.getImage().getWidth()/2 + width, getY());
            }
        }
    }

    private Actor getPlatformBelow() {
        int height = getImage().getHeight() / 2;
        int width = getImage().getWidth() / 4;
        
        // Check multiple points below the character
        int[] xOffsets = {0, -width, width};
        
        for (int xOffset : xOffsets) {
            @SuppressWarnings("unchecked")
            List platforms = getObjectsInRange(height + 5, VisiblePlatform.class);
            platforms.addAll(getObjectsInRange(height + 5, InvisiblePlatform.class));
            
            for (Object obj : platforms) {
                Actor platform = (Actor)obj;
                if (platform.getY() > getY() && 
                    Math.abs(platform.getX() - (getX() + xOffset)) < platform.getImage().getWidth()/2) {
                    return platform;
                }
            }
        }
        return null;
    }

    private void handleClimbing() {
        @SuppressWarnings("unchecked")
        List touching = getObjectsInRange(20, Ladder.class);
        if (!touching.isEmpty()) {
            climbing = true;
            if (Greenfoot.isKeyDown("up")) {
                setLocation(getX(), getY() - climbSpeed);
            }
            if (Greenfoot.isKeyDown("down")) {
                setLocation(getX(), getY() + climbSpeed);
            }
            ySpeed = 0;
        } else {
            climbing = false;
        }
    }

    private void checkVoid() {
        @SuppressWarnings("unchecked")
        List touching = getObjectsInRange(20, Invisible_Void.class);
        if (!touching.isEmpty()) {
            Object obj = touching.get(0);
            if (obj instanceof Invisible_Void) {
                if (Level_1.ambientMusic != null) {
                    Level_1.ambientMusic.stop();
                }
                Greenfoot.setWorld(new GameOver());
            }
        }
    }

    public void setLivesCounter(LivesCounter counter) {
        this.livesCounter = counter;
    }

    private void checkHitByObstacle() {
        if (!fadingOut && !invulnerable) {
            @SuppressWarnings("unchecked")
            List obstacles = getObjectsInRange(20, FallingObstacle.class);
            @SuppressWarnings("unchecked")
            List splittingObstacles = getObjectsInRange(20, SplittingObstacle.class);
            
            boolean isHit = false;
            if (!obstacles.isEmpty()) {
                Object obj = obstacles.get(0);
                if (obj instanceof FallingObstacle) isHit = true;
            }
            if (!splittingObstacles.isEmpty()) {
                Object obj = splittingObstacles.get(0);
                if (obj instanceof SplittingObstacle) isHit = true;
            }
            
            if (isHit) {
                takeDamage();
            }
        }
    }

    public void loseLife() {
        if (!invulnerable) {
            takeDamage();
        }
    }

    public void takeDamage() {
        if (!fadingOut && !invulnerable) {
            lives--;
            if (livesCounter != null) {
                livesCounter.setLives(lives);
            }

            if (lives <= 0) {
                // Stop appropriate music based on level
                if (getWorld() instanceof Level_1 && Level_1.ambientMusic != null) {
                    Level_1.ambientMusic.stop();
                } else if (getWorld() instanceof Level_2 && Level_2.ambientMusic != null) {
                    Level_2.ambientMusic.stop();
                }
                Greenfoot.setWorld(new GameOver());
            } else {
                
                fadingOut = true;
                fadeTimer = 0;
                invulnerable = true;
                graceTimer = 100;
                getImage().setTransparency(0);
            }
        }
    }

    private void handleFading() {
        fadeTimer++;
        if (fadeTimer <= 255) {
            GreenfootImage img = getImage();
            img.setTransparency(fadeTimer);
            setImage(img);
        } else {
            if (lives > 0) {
                setLocation(100, 480);
                setImage(facingRight ? runFrames[0] : runLeftFrames[0]);
                getImage().setTransparency(255);
                fadeTimer = 0;
                fadingOut = false;
            } else {
                Greenfoot.setWorld(new GameOver());
            }
        }
    }

    /**
     * Resets the jump state and vertical velocity of the character
     */
    public void resetJump() {
        ySpeed = 0;
        onGround = false;
    }

    /**
     * Public method to check if character is touching a WinZone
     */
    public boolean isCollidingWithWinZone() {
        @SuppressWarnings("unchecked")
        List touching = getObjectsInRange(20, WinZone.class);
        if (!touching.isEmpty()) {
            Object obj = touching.get(0);
            return (obj instanceof WinZone);
        }
        return false;
    }
}
