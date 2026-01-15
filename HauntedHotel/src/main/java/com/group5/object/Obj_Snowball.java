package com.group5.object;

import com.group5.main.GamePanel;
import com.group5.entity.Projectile;

public class Obj_Snowball extends Projectile {

    GamePanel gp;
    
    /**
     * Constructor for the snowball projectile.
     * Initializes its name, speed, life, and sets up its images.
     * 
     * @param gp Reference to the GamePanel
     */
    public Obj_Snowball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Snowball";
        speed = 7;
        maxLife = 60;
        life = maxLife;
        alive = false;
        
        getImage();
    }
    
    /**
     * Loads the sprite images for the snowball in all four directions.
     */
    public void getImage() {
        // Load snowball sprite
        up1 = setUp("/objects/snowball", gp.tileSize, gp.tileSize);
        up2 = setUp("/objects/snowball", gp.tileSize, gp.tileSize);
        down1 = setUp("/objects/snowball", gp.tileSize, gp.tileSize);
        down2 = setUp("/objects/snowball", gp.tileSize, gp.tileSize);
        left1 = setUp("/objects/snowball", gp.tileSize, gp.tileSize);
        left2 = setUp("/objects/snowball", gp.tileSize, gp.tileSize);
        right1 = setUp("/objects/snowball", gp.tileSize, gp.tileSize);
        right2 = setUp("/objects/snowball", gp.tileSize, gp.tileSize);
    }
}

