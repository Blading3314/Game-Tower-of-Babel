import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.Random;
import java.util.List;

/**
 * Level_2 - Jungle temple level with Moon power-up.
 */
public class Level_2 extends World
{
    private int spawnTimer = 0;
    private Random random = new Random();
    public static GreenfootSound ambientMusic = new GreenfootSound("8 Bit Dungeon Music.mp3");
    private boolean musicStarted = false;
    private static final int SPAWN_DELAY = 120;  // Delay between obstacle spawns
    private int stage = 1;
    private int stageTimer = 0;
    private static final int STAGE_1_DURATION = 3600; // 60 seconds (60 * 60 fps)
    private static final int STAGE_2_DURATION = 3600; // 60 seconds for stage 2
    private String message = "";
    private int messageTimer = 0;
    private MainCharacter mainCharacter;
    public boolean isStage2 = false;
    private boolean showingStage1Complete = false;
    private int stage1CompleteTimer = 0;
    private static final int COMPLETE_SCREEN_DURATION = 180; // 3 seconds
    private WinZone winZone;
    private boolean stage1Completed = false;
    private int voidFalls = 0;
    private static final int MAX_VOID_FALLS = 6;
    private LivesCounter livesCounter;

    public Level_2()
    {    
        super(1152, 768, 1); // Updated to match the image resolution

        // Stop all other music
        if (WelcomeScreen.introMusic != null) {
            WelcomeScreen.introMusic.stop();
        }
        if (GameOver.gameOverMusic != null) {
            GameOver.gameOverMusic.stop();
        }
        if (Level_1.ambientMusic != null) {
            Level_1.ambientMusic.stop();
        }
        if (ambientMusic != null) {
            ambientMusic.stop();
        }

        // Initialize new ambient music
        ambientMusic = new GreenfootSound("8 Bit Dungeon Music.mp3");
        musicStarted = false;

        setBackground("lvl2.png");
        getBackground().scale(getWidth(), getHeight());
        prepare();
    }

    public void act() {
        // Start music only when the world is actually running
        if (!musicStarted && ambientMusic != null) {
            ambientMusic.playLoop();
            musicStarted = true;
        }

        if (showingStage1Complete) {
            handleStage1CompleteScreen();
            return;
        }
        
        // Update and display timer
        if (!isStage2 && !stage1Completed) {
            stageTimer++;
            int timeLeft = (STAGE_1_DURATION - stageTimer) / 60;
            showText("Stage 1 - Time Left: " + timeLeft + "s", getWidth()/2, 30);
            
            if (stageTimer >= STAGE_1_DURATION) {
                showStage1Complete();
                stage1Completed = true;
                return;
            }
            
            // Check for premature WinZone touch in Stage 1
            if (mainCharacter != null && mainCharacter.getWorld() != null) {
                if (mainCharacter.isCollidingWithWinZone()) {
                    showText("Entrance is not open yet!", getWidth()/2, 50);
                    messageTimer = 60;
                }
            }
            
            // Spawn stalactites in Stage 1
            if (stageTimer % 120 == 0) {  // Every 2 seconds
                addObject(new Stalactite(), Greenfoot.getRandomNumber(getWidth()-200) + 100, 0);
            }
        } else if (isStage2) {
            stageTimer++;
            int timeLeft = (STAGE_2_DURATION - stageTimer) / 60;
            showText("Stage 2 - Time Left: " + timeLeft + "s", getWidth()/2, 30);
            
            if (stageTimer >= STAGE_2_DURATION) {
                Greenfoot.setWorld(new GameOver());
                return;
            }
            
            // Spawn stalactites more frequently in Stage 2
            if (stageTimer % 60 == 0) {  // Every second
                addObject(new Stalactite(), Greenfoot.getRandomNumber(getWidth()-200) + 100, 0);
            }
        }

        // Handle message display
        if (messageTimer > 0) {
            messageTimer--;
            if (messageTimer == 0) {
                showText("", getWidth()/2, 50);
            }
        }
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

    private void prepare() {
        // Set and scale background
        setBackground("lvl2.png");
        getBackground().scale(getWidth(), getHeight());

        // === GROUND LEVEL PLATFORMS ===
        VisiblePlatform groundLeft = new VisiblePlatform();
        addObject(groundLeft, 437, 720); // Ground left
        addObject(new VisiblePlatform(), 591, 720); // Ground right - exact same height
        
        // === MAIN PLATFORMS ===
        // Right side platforms
        addObject(new VisiblePlatform(), 708, 537);
        addObject(new InvisiblePlatform(), 708, 537); // Support platform
        
        // Left side platforms
        addObject(new VisiblePlatform(), 236, 307);
        addObject(new VisiblePlatform(), 315, 251);
        addObject(new VisiblePlatform(), 229, 217);
        addObject(new VisiblePlatform(), 154, 543);
        
        // === STAIRS AND CONNECTIONS ===
        // Bottom stairs - smooth progression
        addObject(new InvisiblePlatform(), 224, 634);
        addObject(new InvisiblePlatform(), 271, 677);
        addObject(new InvisiblePlatform(), 296, 698);
        addObject(new InvisiblePlatform(), 346, 698); // Connection
        addObject(new InvisiblePlatform(), 396, 698); // Extra connection

        // Middle platform to moon - consistent height
        for (int x = 767; x <= 1113; x += 87) {  // Equal spacing
            addObject(new InvisiblePlatform(), x, 649);
        }

        // Moon top level - aligned platforms
        for (int x = 870; x <= 1116; x += 82) {  // Equal spacing
            addObject(new InvisiblePlatform(), x, 404);
        }

        // Center jungle wall - reinforced
        addObject(new InvisiblePlatform(), 621, 394);
        addObject(new InvisiblePlatform(), 648, 394);
        addObject(new InvisiblePlatform(), 676, 394);
        addObject(new InvisiblePlatform(), 634, 394); // Extra support

        // Top jungle wall - no gaps
        addObject(new InvisiblePlatform(), 538, 285);
        addObject(new InvisiblePlatform(), 449, 284);
        addObject(new InvisiblePlatform(), 493, 284);
        addObject(new InvisiblePlatform(), 594, 285);
        addObject(new InvisiblePlatform(), 649, 189);

        // Left area - complete coverage
        addObject(new InvisiblePlatform(), 52, 471);
        addObject(new InvisiblePlatform(), 52, 633);
        addObject(new InvisiblePlatform(), 138, 634);
        addObject(new InvisiblePlatform(), 72, 470);
        addObject(new InvisiblePlatform(), 218, 393);
        addObject(new InvisiblePlatform(), 261, 393);
        addObject(new InvisiblePlatform(), 305, 394);
        addObject(new InvisiblePlatform(), 89, 196);
        addObject(new InvisiblePlatform(), 119, 197);
        addObject(new InvisiblePlatform(), 148, 197);

        // === VOID + WIN ZONE ===
        Invisible_Void invisibleVoid = new Invisible_Void() {
            
            public void checkVoid() {
                List<MainCharacter> characters = getObjectsInRange(50, MainCharacter.class);
                for (MainCharacter character : characters) {
                    if (character.getY() >= getWorld().getHeight() - 50) {
                        voidFalls++;
                        if (voidFalls >= MAX_VOID_FALLS) {
                            Greenfoot.setWorld(new GameOver());
                        } else {
                            // Reset character position based on current stage
                            if (!isStage2) {
                                character.setLocation(100, 700);
                            } else {
                                character.setLocation(100, 400);
                            }
                        }
                    }
                }
            }
        };
        addObject(invisibleVoid, 543, 762);

        // Store the WinZone reference and make it completely invisible
        winZone = new WinZone();
        addObject(winZone, 1073, 569);
        winZone.setEnabled(false);
        winZone.getImage().setTransparency(0);
        winZone.getImage().scale(1, 1);

        // === LIVES COUNTER ===
        livesCounter = new LivesCounter(6);
        addObject(livesCounter, 70, 30);

        // === MAIN CHARACTER ===
        mainCharacter = new MainCharacter() {
        
            public void checkVoid() {
                @SuppressWarnings("unchecked")
                List touching = getObjectsInRange(20, Invisible_Void.class);
                if (!touching.isEmpty()) {
                    voidFalls++;
                    if (voidFalls >= MAX_VOID_FALLS) {
                        // Reset to Level 1
                        if (Level_2.ambientMusic != null) {
                            Level_2.ambientMusic.stop();
                        }
                        if (GameOver.gameOverMusic != null) {
                            GameOver.gameOverMusic.playLoop();
                        }
                        Greenfoot.delay(60); // Play sound for 1 second
                        if (GameOver.gameOverMusic != null) {
                            GameOver.gameOverMusic.stop();
                        }
                        Greenfoot.setWorld(new Level_1());
                    } else {
                        // Reset position and take damage
                        takeDamage();
                        setLocation(437, 720 - getImage().getHeight()/2);
                    }
                }
            }
        };
        mainCharacter.setLivesCounter(livesCounter);
        
        // Precise spawn position
        int groundHeight = 40; // Height of ground platform
        int playerHeight = mainCharacter.getImage().getHeight();
        addObject(mainCharacter, 437, 720 - (groundHeight/2) - (playerHeight/2));

        // === POWER-UP ===
        MoonPowerUp moonPowerUp = new MoonPowerUp();
        addObject(moonPowerUp, 1050, 370);
    }

    private void handleStage1CompleteScreen() {
        stage1CompleteTimer--;
        if (stage1CompleteTimer <= 0) {
            showText("", getWidth()/2, getHeight()/2);
            showingStage1Complete = false;
        }
    }
    
    private void showStage1Complete() {
        showingStage1Complete = true;
        stage1CompleteTimer = 60;  // Show for 1 second
        showText("Get Ready for Stage 2!", getWidth()/2, getHeight()/2);
        isStage2 = true;
        stageTimer = 0;
    }

    public int getStage() {
        return stage;
    }
}

