package com.group5.monster;

import com.group5.entity.Entity;
import com.group5.main.GamePanel;
import com.group5.object.Obj_Money;
/**
 * This is the class for Michael Myers enemy
 * Extends Entity class and has movements and attacking
 */
public class MyersEnemy extends Entity {

    GamePanel gp;

    public MyersEnemy(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = 2; // monster
        name = "Myers";

        speed = 5;
        maxLife = 5;
        life = maxLife;

        solidArea.x = 16;
        solidArea.y = 32;
        solidArea.width = 24;
        solidArea.height = 24;
        solidAreaDefultX = solidArea.x;
        solidAreaDefultY = solidArea.y;

        // load sprites
        getImage();
    }

    public void getImage() {
        up1 = setUp("/monster/Myersrun_r1", gp.tileSize, gp.tileSize);
        up2 = setUp("/monster/Myersrun_r2", gp.tileSize, gp.tileSize);
        up3 = up1;

        down1 = setUp("/monster/Myersrun_L1", gp.tileSize, gp.tileSize);
        down2 = setUp("/monster/Myersrun_L2", gp.tileSize, gp.tileSize);
        down3 = down1;


        left1 = setUp("/monster/Myersrun_L1", gp.tileSize, gp.tileSize);
        left2 = setUp("/monster/Myersrun_L2", gp.tileSize, gp.tileSize);
        left3 = left1;

        right1 = setUp("/monster/Myersrun_r1", gp.tileSize, gp.tileSize);
        right2 = setUp("/monster/Myersrun_r2", gp.tileSize, gp.tileSize);
        right3 = right1;

        attackUp1 = setUp("/monster/Myers_stand", gp.tileSize, gp.tileSize);
        // standing images
       
    }

    @Override
    public void setAction() {

        //  chase player using pathfinding:
        onPath = true;

        int goalCol = (gp.player.worldx + gp.player.solidArea.x) / gp.tileSize;
        int goalRow = (gp.player.worldy + gp.player.solidArea.y) / gp.tileSize;

        goalCol = Math.max(0, Math.min(goalCol, gp.maxWorldCol -1 ));
        goalRow = Math.max(0, Math.min(goalRow, gp.maxWorldRow -1 ));


        int startCol = (worldx + solidArea.x)/ gp.tileSize;
        int startRow = (worldy + solidArea.y)/ gp.tileSize;

        gp.pFinder.setNode(startCol, startRow, goalCol, goalRow);

        

        if (gp.pFinder.search()) {
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;

            if (nextRow < startRow) direction = "up";
            else if (nextRow > startRow) direction = "down";
            else if (nextCol < startCol) direction = "left";
            else if (nextCol > startCol) direction = "right";
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;

        if(gp.player.direction == "up"){
            direction = "down";
        }
        if(gp.player.direction == "down"){
            direction = "up";
        }
        if(gp.player.direction == "left"){
            direction = "right";
        }
        if(gp.player.direction == "right"){
            direction = "left";
        }
        
    }

    /**
     * Determines what item the boss drops upon death.
     */
    public void checkDrop() {

        dropItem(new Obj_Money(gp));

    }
}