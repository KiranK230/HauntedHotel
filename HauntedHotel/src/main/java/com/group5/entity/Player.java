package com.group5.entity;

import com.group5.main.GamePanel;
import com.group5.main.KeyHandler;
import java.util.ArrayList;

import java.awt.*;
import java.awt.image.BufferedImage;
import com.group5.object.Obj_ElectricBall;

/**
 * The {@code Player} class represents the player-controlled character in the game.
 * It handles user input, movement, attacks, interactions with objects, NPCs, and monsters,
 * as well as updating the player's status such as health, keys, and money.
 * <p>
 * This class extends {@link Entity} and integrates keyboard input through {@link KeyHandler}
 * to manage real-time gameplay behavior.
 * </p>
 * 
 * @author Group 5
 * @version 1.0
 */
public class Player extends Entity{


     /** Handles keyboard input for player movement and actions. */
    KeyHandler keyH;

    /** X-coordinate of the player's position on screen. */
    public final int screenX;

    /** Y-coordinate of the player's position on screen. */
    public final int screenY;

    /** Number of keys currently held by the player. */
    public int hasKey = 0;

    /** Amount of money currently collected by the player. */
    public int hasMoney = 0;

    /** Player's inventory storing collectible entities. */
    public ArrayList<Entity> inventory = new ArrayList<>();

    /** Maximum inventory capacity. */
    public final int maxInvntorySize = 12;

    /** Key image used for HUD display or item collection feedback. */
    BufferedImage key;

    /**
     * Constructs a {@code Player} object linked to the main {@link GamePanel} and key handler.
     * Initializes hitboxes, attack area, default position, images, and player stats.
     *
     * @param gp the {@link GamePanel} instance managing the game loop and rendering
     * @param keyH the {@link KeyHandler} managing user input
     */
    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 34;
        solidAreaDefultX = solidArea.x;
        solidAreaDefultY = solidArea.y;
        solidArea.width = 64;
        solidArea.height = 62;

        attackArea.width = 80;
        attackArea.height = 80;

        setDefultValues();
        getPlayerImg();
        getPlayerAttackImg();
        setItems();

    }

      /** Sets default values for the player's position, speed, stats, and projectile type. */
    public void setDefultValues() {
        worldx = gp.tileSize * 10;;
        worldy = gp.tileSize * 14; // WAS CHANGED FROM GP.TILESIXE
        speed = 8;

        direction = "down";
        invincible = false;
        inventory.clear();

        // PLAYER STATUS
        maxLife = 10;
        life = maxLife;
        projectile = new Obj_ElectricBall(gp);
        //System.out.println(gp.tileSize);
    }

    /** Initializes the player's starting inventory items. */
    public void setItems() {
        //inventory.add(new Obj_Chest_Key(gp));
        //inventory.add(new Obj_Chest_Key(gp));
    }

    /** Loads all player movement sprite images. */
    public void getPlayerImg () {

        up1 = setUp("/player/Player276-up1", gp.tileSize, gp.tileSize);
        up2 = setUp("/player/Player276-up2", gp.tileSize, gp.tileSize);
        up3 = setUp("/player/Player276-up3", gp.tileSize, gp.tileSize);

        down1 = setUp("/player/Player276-down1", gp.tileSize, gp.tileSize);
        down2 = setUp("/player/Player276-down2", gp.tileSize, gp.tileSize);
        down3 = setUp("/player/Player276-down3", gp.tileSize, gp.tileSize);

        right1 = setUp("/player/Player276-right1", gp.tileSize, gp.tileSize);
        right2 = setUp("/player/Player276-right2", gp.tileSize, gp.tileSize);
        right3 = setUp("/player/Player276-right3", gp.tileSize, gp.tileSize);

        left1 = setUp("/player/Player276-left1", gp.tileSize, gp.tileSize);
        left2 = setUp("/player/Player276-left2", gp.tileSize, gp.tileSize);
        left3 = setUp("/player/Player276-left3", gp.tileSize, gp.tileSize);

    }

    /** Loads all player attack sprite images. */
    public void getPlayerAttackImg() {

        attackUp1 = setUp("/player/PlayerAttack-up1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setUp("/player/PlayerAttack-up2", gp.tileSize, gp.tileSize*2);
        attackUp3 = setUp("/player/PlayerAttack-up3", gp.tileSize, gp.tileSize*2);

        attackDown1 = setUp("/player/PlayerAttack-down1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setUp("/player/PlayerAttack-down2", gp.tileSize, gp.tileSize*2);
        attackDown3 = setUp("/player/PlayerAttack-down3", gp.tileSize, gp.tileSize*2);

        attackRight1 = setUp("/player/PlayerAttack-right1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setUp("/player/PlayerAttack-right2", gp.tileSize*2, gp.tileSize);
        attackRight3 = setUp("/player/PlayerAttack-right3", gp.tileSize*2, gp.tileSize);

        attackLeft1 = setUp("/player/PlayerAttack-left1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setUp("/player/PlayerAttack-left2", gp.tileSize*2, gp.tileSize);
        attackLeft3 = setUp("/player/PlayerAttack-left3", gp.tileSize*2, gp.tileSize);
    }

     /** Updates player state including movement, attacks, collisions, and status effects. */
    public  void update() {

        if(attacking == true) {
            attacking();
        }

        else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
            if(keyH.upPressed) { // COULD BE SWITCH STATEMENT
                direction = "up";

            } else if (keyH.downPressed) {
                direction = "down";

            } else if (keyH.leftPressed) {
                direction = "left";

            } else if (keyH.rightPressed) {
                direction = "right";

            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJ COLLISION
            int ObjIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(ObjIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISSION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK EVENT
            gp.eHandler.checkEvent();

            // IF COLLIS IS FALSE PLAYERS MOVES
            if(collisionOn == false && keyH.enterPressed == false){
                switch (direction){
                    case "up":
                        worldy -= speed;
                        break;
                    case "down":
                        worldy += speed;
                        break;
                    case "left":
                        worldx -= speed;
                        break;
                    case "right":
                        worldx += speed;
                        break;
                }
            }

            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1){
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 3;
                }
                else if (spriteNum == 3 ){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if(gp.keyH.shootKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30) {

            // DEFULT COORDINATES
            projectile.set(worldx, worldy, direction, true, this);

            gp.projectileList.add(projectile);

            shotAvailableCounter = 0;
        }

        // outside if statement
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

        if(life <= 0) {
            gp.gameState = gp.gameOverState;
            life = maxLife;
        }


    }


    /** Handles player attack animations and damage dealing logic. */
    public void attacking() {

        spriteCounter++;

        if(spriteCounter <= 5) {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 15) {
            spriteNum = 2;
        }
        if(spriteCounter > 15 && spriteCounter <= 25) {
            spriteNum = 3;
        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

        // CURRENT CURRENT VALUES
        int currentWorldX = worldx;
        int currentWorldY = worldy;
        int solidAreaWidth = solidArea.width;
        int solidAreaHeight = solidArea.height;

        // ATTACK AREA
        switch (direction) {
            case "up":
                worldy -= attackArea.height;
                break;
            case "down":
                worldy += attackArea.height;
                break;
            case "left":
                worldx -= attackArea.width;
                break;
            case "right":
                worldx += attackArea.width;
                break;
        }

        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;
        // CHECK MONSTER COLLISSION WITH UPDATED WORLD X AND Y
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        damageMonster(monsterIndex);

        // AFTER CHECKING COLLISION RESTORE VALUES
        worldx = currentWorldX;
        worldy = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;

    }

    /**
     * Handles object collection logic.
     * Adds items to inventory or displays a message if full.
     *
     * @param i the index of the object being picked up
     */
    public void pickUpObject(int i) {

        if(i != 999){


            // PICK UP INVENTORY ITEMS


            String text;

            if(inventory.size() != maxInvntorySize) {
                inventory.add(gp.obj[gp.currentMap][i]);
                text = "You got a " + gp.obj[gp.currentMap][i].name;
                if(gp.obj[gp.currentMap][i].name == "key") {
                    hasKey++;
                }
                if(gp.obj[gp.currentMap][i].name == "Money") {
                    hasMoney++;
                }
                gp.playSE(1); // BUGGY

            }
            else {
                text = "Inventory full";
            }
            gp.ui.showMessage(text);
            gp.obj[gp.currentMap][i] = null;


        }


    }

    /**
     * Handles interaction with NPCs when the player presses the interaction key.
     *
     * @param i the index of the NPC being interacted with
     */
    public void interactNPC(int i) {

        if(gp.keyH.enterPressed == true) {

            if(i != 999) {
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
        }
        if(gp.keyH.attackPressed == true) {
            attacking = true;
        }
        gp.keyH.attackPressed = false;
    }


    /**
     * Handles contact between the player and monsters.
     * Reduces health and triggers invincibility frames.
     *
     * @param i the index of the monster contacted
     */
    public void contactMonster(int i) {

        if(i != 999) {
            if(invincible == false) {
                life -= 2;
                invincible = true;

                for(int j = 0; j < 80; j++) {
                    onPath = false;
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
                onPath = true; 

            }

        }
    }


    /**
     * Inflicts damage on monsters when attacking.
     * Updates game state upon monster death and win/loss conditions.
     *
     * @param i the index of the monster being damaged
     */
    public void damageMonster(int i) {
        if (i != 999) {
           if(gp.monster[gp.currentMap][i].invincible == false && gp.monster[gp.currentMap][i].dying == false) {
               gp.monster[gp.currentMap][i].life -= 1;
               gp.monster[gp.currentMap][i].invincible = true;
               gp.monster[gp.currentMap][i].damageReaction();

               }

            if(gp.monster[gp.currentMap][i].life <= 0) {
                   gp.monster[gp.currentMap][i].dying = true;
                   hasMoney++;
                   
                   if(gp.monster[3][i].life <= 0 && hasMoney >= 2) {
                       gp.gameState = gp.gameWinState;
                   }
                   else if (gp.monster[3][i].life <= 0 && hasMoney <= 1){
                       gp.gameState = gp.gameOverState;
                   }

               }
           }
        }

    
    /**
     * Renders the player on the screen with the correct sprite based on direction and state.
     *
     * @param g2 the {@link Graphics2D} context used for drawing
     */
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch(direction) {
            case "up":
                if(attacking == false) {
                    if(spriteNum == 1){ image = up1; }
                    if(spriteNum == 2){ image = up2; }
                    if(spriteNum == 3){ image = up3; }
                }
                if(attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1){ image = attackUp1; }
                    if(spriteNum == 2){ image = attackUp2; }
                    if(spriteNum == 3){ image = attackUp3; }
                }
                break;
            case "down":
                if(attacking == false) {
                    if(spriteNum == 1){ image = down1; }
                    if(spriteNum == 2){ image = down2; }
                    if(spriteNum == 3){ image = down3; }
                }
                if(attacking == true) {
                    if(spriteNum == 1){ image = attackDown1; }
                    if(spriteNum == 2){ image = attackDown2; }
                    if(spriteNum == 3){ image = attackDown3; }
                }
                break;
            case "right":
                if(attacking == false) {
                    if(spriteNum == 1){ image = right1; }
                    if(spriteNum == 2){ image = right2; }
                    if(spriteNum == 3){ image = right3; }
                }
                if(attacking == true) {
                    if(spriteNum == 1){ image = attackRight1; }
                    if(spriteNum == 2){ image = attackRight2; }
                    if(spriteNum == 3){ image = attackRight3; }
                }
                break;
            case "left":
                if(attacking == false) {
                    if(spriteNum == 1){ image = left1; }
                    if(spriteNum == 2){ image = left2; }
                    if(spriteNum == 3){ image = left3; }
                }
                if(attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1){ image = attackLeft1; }
                    if(spriteNum == 2){ image = attackLeft2; }
                    if(spriteNum == 3){ image = attackLeft3; }
                }
                break;
        }

        if(invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    
    }
}