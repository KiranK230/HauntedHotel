package com.group5.entity;

import com.group5.main.GamePanel;


import java.util.Random;


/**
 * The {@code NPC} class represents a non-player character within the game world.
 * NPCs can move randomly, display dialogues, and interact with the player through conversations.
 * This class extends {@link Entity} and inherits all rendering, movement, and collision logic.
 * 
 * <p>Each NPC can have multiple dialogue lines that are displayed in sequence
 * when the player interacts with them.</p>
 * 
 * @author Group 5
 * @version 1.0
 */
public class NPC extends Entity{

    /**
     * Constructs an {@code NPC} object linked to the main {@link GamePanel}.
     * Initializes the default direction, image, and dialogue lines.
     *
     * @param gp the {@link GamePanel} instance that manages this NPC
     */
    public NPC(GamePanel gp) {
        super(gp);

        type = 1;
        direction = "down";
        //speed = 1;
        //getNPCImage();
        down1 = setUp("/npc/npc1", gp.tileSize, gp.tileSize);
        setDialogue();

    }

    /**
     * Loads the NPC's sprite images for animation or display.
     * This method sets up the first and second NPC images.
     */
    public void getNPCImage() {
        down1 = setUp("/npc/npc1", gp.tileSize, gp.tileSize);
        down2 = setUp("/npc/npc2", gp.tileSize, gp.tileSize);
    }

    /**
     * Initializes the NPC's dialogue lines that appear during player interaction.
     * These dialogues provide narrative and quest-related information to the player.
     */
    public void setDialogue() {
 dialogues[0] = "Hello, are you by chance poor and in need of money?\n I am the owner of this hotel but its haunted. I need help.\n I'll pay you if you clear out the ghosts. You'll need keys to do so. \n Find 2 gold keys that are somewhere in the hotel to get to the \nbasement. The basment door is up and the key in the lobby leads\n to the rest of the hotel doors to the left and right. Good luck.";    }

    /**
     * Defines the NPC's behavior for random movement.
     * Every 180 frames, the NPC chooses a random direction to move in (up, down, left, or right).
     */
    public void setAction() {
        // FOR NPC AI MOVEMENT
        actionLockCounter++;

        if(actionLockCounter == 180) {
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    /**
     * Handles NPC-player interaction by cycling through dialogue lines.
     * Displays the next dialogue line on the game's UI. When all lines are shown, it restarts from the beginning.
     */
    public void speak() {

        if(dialogues[dialogeIndex] == null) {
            dialogeIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogeIndex];
        dialogeIndex++;
    }

    /*
    public void draw(Graphics2D g2) {
        BufferedImage image = down1;
        g2.drawImage(image, 30, 24, null);
    }
    */


}