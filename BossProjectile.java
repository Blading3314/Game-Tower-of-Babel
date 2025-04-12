import greenfoot.*;

public class BossProjectile extends Actor {
    private int velocityX, velocityY;

    public BossProjectile(int type, int dx, int dy) {
       if (type == 1) {
    setImage("projectile1.png");
    getImage().scale(getImage().getWidth() / 2, getImage().getHeight() / 2);
} else {
    setImage("projectile2.png");
    getImage().scale(getImage().getWidth() / 2, getImage().getHeight() / 2);
}

        double length = Math.sqrt(dx * dx + dy * dy);
        velocityX = (int)(dx / length * 5);
        velocityY = (int)(dy / length * 5);
    }

    public void act() {
        setLocation(getX() + velocityX, getY() + velocityY);

        if (isAtEdge()) {
            getWorld().removeObject(this);
            return;
        }

        Actor hit = getOneIntersectingObject(MainCharacter.class);
        if (hit != null) {
            ((MainCharacter) hit).takeDamage(); // Must exist in MainCharacter
            getWorld().removeObject(this);
        }
    }
}
