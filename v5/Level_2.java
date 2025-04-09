import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Level_2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level_2 extends World
{

    /**
     * Constructor for objects of class Level_2.
     * 
     */
    public Level_2()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
        prepare();
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {

        InvisiblePlatform invisiblePlatform = new InvisiblePlatform();
        addObject(invisiblePlatform,47,359);
        InvisiblePlatform invisiblePlatform2 = new InvisiblePlatform();
        addObject(invisiblePlatform2,134,359);
        InvisiblePlatform invisiblePlatform3 = new InvisiblePlatform();
        addObject(invisiblePlatform3,207,358);
        InvisiblePlatform invisiblePlatform4 = new InvisiblePlatform();
        addObject(invisiblePlatform4,485,363);
        InvisiblePlatform invisiblePlatform5 = new InvisiblePlatform();
        addObject(invisiblePlatform5,562,364);
        InvisiblePlatform invisiblePlatform6 = new InvisiblePlatform();
        addObject(invisiblePlatform6,557,285);
        InvisiblePlatform invisiblePlatform7 = new InvisiblePlatform();
        addObject(invisiblePlatform7,467,284);
        InvisiblePlatform invisiblePlatform8 = new InvisiblePlatform();
        addObject(invisiblePlatform8,378,284);
        InvisiblePlatform invisiblePlatform9 = new InvisiblePlatform();
        addObject(invisiblePlatform9,290,283);
        VisiblePlatform visiblePlatform = new VisiblePlatform();
        addObject(visiblePlatform,311,388);
        VisiblePlatform visiblePlatform2 = new VisiblePlatform();
        addObject(visiblePlatform2,398,388);
        VisiblePlatform visiblePlatform3 = new VisiblePlatform();
        addObject(visiblePlatform3,149,302);
        VisiblePlatform visiblePlatform4 = new VisiblePlatform();
        addObject(visiblePlatform4,32,95);
        VisiblePlatform visiblePlatform5 = new VisiblePlatform();
        addObject(visiblePlatform5,96,95);
        VisiblePlatform visiblePlatform6 = new VisiblePlatform();
        addObject(visiblePlatform6,200,143);
        VisiblePlatform visiblePlatform7 = new VisiblePlatform();
        addObject(visiblePlatform7,308,188);
        VisiblePlatform visiblePlatform8 = new VisiblePlatform();
        addObject(visiblePlatform8,544,228);
        VisiblePlatform visiblePlatform9 = new VisiblePlatform();
        addObject(visiblePlatform9,478,169);
        VisiblePlatform visiblePlatform10 = new VisiblePlatform();
        addObject(visiblePlatform10,391,128);
        MainCharacter mainCharacter = new MainCharacter();
        addObject(mainCharacter,56,323);
        MoonPowerUp moonPowerUp = new MoonPowerUp();
        addObject(moonPowerUp,505,317);
    }
}
