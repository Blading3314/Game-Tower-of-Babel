import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
import greenfoot.Color;

/**
 * Level_1 - A falling cavern level with screen shake effect and obstacles.
 */
public class Level_1 extends World {
    private int spawnTimer = 0;
    private int shakeTimer = 0;
    private Random random = new Random(); 
    private LivesCounter livesCounter;
    public static GreenfootSound ambientMusic = new GreenfootSound("cave_lvl1.mp3");
    private boolean musicStarted = false;

    public Level_1() {    
        super(900, 600, 1); 

        // Stop all other music
        if (WelcomeScreen.introMusic != null) {
            WelcomeScreen.introMusic.stop();
        }
        if (GameOver.gameOverMusic != null) {
            GameOver.gameOverMusic.stop();
        }
        if (ambientMusic != null) {
            ambientMusic.stop();
        }

        // Initialize new ambient music
        ambientMusic = new GreenfootSound("cave_lvl1.mp3");
        musicStarted = false;

        setBackground("cavernlvl1.png");
        getBackground().scale(getWidth(), getHeight()); 
        prepare();
    }

    public void act() {
        // Start music only when the world is actually running
        if (!musicStarted && ambientMusic != null) {
            ambientMusic.playLoop();
            musicStarted = true;
        }

        spawnTimer++;
        if (spawnTimer >= Greenfoot.getRandomNumber(35) + 10) {
            int x = Greenfoot.getRandomNumber(getWidth());
            addObject(new FallingObstacle(), Greenfoot.getRandomNumber(getWidth()), 0);
            addObject(new FallingObstacle(), Greenfoot.getRandomNumber(getWidth()), 0);
            spawnTimer = 0;
        }
        applyShakeEffect();
    }

    public void started() {
        if (ambientMusic != null && !musicStarted) {
            ambientMusic.playLoop();
            musicStarted = true;
        }
    }

    public void stopped() {
        // Stop music when the world is stopped
        if (ambientMusic != null) {
            ambientMusic.stop();
            musicStarted = false;
        }
    }

    private void applyShakeEffect() {
        int shakeStrength = 3;
        int xOffset = random.nextInt(shakeStrength * 2 + 1) - shakeStrength;
        int yOffset = random.nextInt(shakeStrength * 2 + 1) - shakeStrength;

        GreenfootImage bg = new GreenfootImage("cavernlvl1.png");
        bg.scale(getWidth(), getHeight());

        getBackground().clear(); // Clear previous shake
        getBackground().drawImage(bg, xOffset, yOffset);
    }

    /**
     * Prepare the world for the start of the program.
     */
    private void prepare() {
        // Add starting platform
        InvisiblePlatform startPlatform = new InvisiblePlatform();
        addObject(startPlatform, 100, 500);

        // Add main platforms
        addObject(new InvisiblePlatform(), 250, 500);
        addObject(new InvisiblePlatform(), 400, 500);
        addObject(new InvisiblePlatform(), 550, 500);
        addObject(new InvisiblePlatform(), 700, 500);
        
        // Add right corner platforms to prevent falling
        addObject(new InvisiblePlatform(), getWidth() - 100, 500);
        addObject(new InvisiblePlatform(), getWidth() - 50, 500);

        // Add void
        Invisible_Void invisibleVoid = new Invisible_Void();
        addObject(invisibleVoid, 300, getHeight() - 1);

        // Add obstacles
        addObject(new FallingObstacle(), 200, 0);

        // Add completely invisible win zone at the exit
        WinZone winZone = new WinZone();
        addObject(winZone, getWidth() - 75, 450);
        GreenfootImage winImg = new GreenfootImage(50, 50);
        winImg.setColor(new Color(0, 0, 0, 0));
        winImg.fill();
        winZone.setImage(winImg);
        winZone.setEnabled(true);

        // Add obstacle destroyer
        addObject(new ObstacleDestroyer(), getWidth() / 2, getHeight() - 5);

        // Add lives counter
        LivesCounter livesCounter = new LivesCounter(4);
        addObject(livesCounter, 70, 30);

        // Add main character
        MainCharacter mc = new MainCharacter();
        mc.setLivesCounter(livesCounter);
        addObject(mc, 100, 500 - startPlatform.getImage().getHeight()/2 - mc.getImage().getHeight()/2);
    }
}


