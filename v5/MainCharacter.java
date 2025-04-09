import greenfoot.*;

public class MainCharacter extends Actor
{
    private int ySpeed = 0;
    private boolean onGround = false;
    private GreenfootImage[] runFrames = new GreenfootImage[4];
    private int frame = 0;
    private int animationDelay = 5;  
    private int delayCount = 0;
    private double gravityMultiplier = 1.0;
    private int moonGravityTimer = 0;
    private final int MOON_GRAVITY_DURATION = 600;
    private GreenfootImage[] runLeftFrames = new GreenfootImage[4];
    private boolean facingRight = true;

    public MainCharacter() {
        for (int i = 0; i < runFrames.length; i++) {
            runFrames[i] = new GreenfootImage("run" + (i + 1) + ".png");
            runLeftFrames[i] = new GreenfootImage("left" + (i + 1) + ".png");
        }
        setImage(runFrames[0]);
    }

    public void act()
    {
        applyGravity();
        checkKeys();
        checkPlatform();
        checkPowerUp();
    }
    
    private void checkPowerUp() {
        Actor powerUp = getOneIntersectingObject(MoonPowerUp.class);
        if (powerUp != null) {
           gravityMultiplier = 0.5; 
           moonGravityTimer = MOON_GRAVITY_DURATION;
           getWorld().removeObject(powerUp); 
        }
    }

    private void animateRun() {
    delayCount++;
    if (delayCount >= animationDelay) {
        delayCount = 0;
        frame = (frame + 1) % runFrames.length;
        if (facingRight) {
            setImage(runFrames[frame]);
        } else {
            setImage(runLeftFrames[frame]);
        }
    }
   }

    private void checkKeys()
    { 
        boolean isMoving = false;

       if (Greenfoot.isKeyDown("a")) {
        move(-3);
        isMoving = true;
        facingRight = false;
        }

       if (Greenfoot.isKeyDown("d")) {
        move(3);
        isMoving = true;
        facingRight = true;
       }

       if (Greenfoot.isKeyDown("space") && onGround) {
        ySpeed = (int)(-12 / gravityMultiplier); 
        onGround = false;
       }

       if (isMoving) {
        animateRun();
       } else {
        setImage(facingRight ? runFrames[0] : runLeftFrames[0]);
        frame = 0;
        delayCount = 0;
        }
    }

    private void applyGravity()
    {
      {
        setLocation(getX(), getY() + (int)(ySpeed * gravityMultiplier));
         ySpeed++;

         Actor platform = getPlatformBelow();
         if (platform != null && ySpeed >= 0) {
        setLocation(getX(), platform.getY() - getImage().getHeight()/2);
        ySpeed = 0;
        onGround = true;
       } else if (getY() > 400) {
        setLocation(getX(), 400);
        ySpeed = 0;
        onGround = true;
        } else {
        onGround = false;
        }

        if (moonGravityTimer > 0) {
        moonGravityTimer--;
        if (moonGravityTimer == 0) {
            gravityMultiplier = 1.0; 
        }
       }
    }
    }

    private void checkPlatform()
    {
        Actor platform = getPlatformBelow();
        if (platform != null && ySpeed >= 0) {
            setLocation(getX(), platform.getY() - getImage().getHeight()/2);
            ySpeed = 0;
            onGround = true;
        } else if (getY() > 400) {
            setLocation(getX(), 400);
            ySpeed = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }

    private Actor getPlatformBelow() {
        int offset = getImage().getHeight() / 2 + 5;
        Actor platform = getOneObjectAtOffset(0, offset, VisiblePlatform.class);
        if (platform == null) {
            platform = getOneObjectAtOffset(0, offset, InvisiblePlatform.class);
        }
        return platform;
    }
}
