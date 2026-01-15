package com.group5.object;

import com.group5.entity.Entity;
import com.group5.main.GamePanel;

/**
 * Represents a gift object in the game.
 * Extends the Entity class and can serve as a container for items such as keys or bombs.
 */
public class Obj_Gift extends Entity {

    /**
     * Constructor for the gifts hiding a basement key or bombs for christmas level.
     * Initializes its name, collision, and sets up its images.
     * 
     * @param gp Reference to the GamePanel
     */
    public Obj_Gift(GamePanel gp) {
        super(gp);
        name = "Gift";
        down1 = setUp("/objects/gift", gp.tileSize, gp.tileSize);
        
        collision = true;
    }
}