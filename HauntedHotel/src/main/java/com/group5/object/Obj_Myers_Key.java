package com.group5.object;

import com.group5.entity.Entity;
import com.group5.main.GamePanel;

/**
 * Represents a key object in the game.
 * Extends the Entity class and is a key collected to unlock basement door.
 */
public class Obj_Myers_Key extends Entity {

    GamePanel gp;

    /**
     * Constructor for the keys for basement door.
     * Initializes its name, type, collision, and sets up its images.
     * 
     * @param gp Reference to the GamePanel
     */
    public Obj_Myers_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "key";
        type = 3;
        down1 = setUp("/objects/key", gp.tileSize, gp.tileSize);
        collision = false;

    }

    
}
