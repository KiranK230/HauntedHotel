package com.group5.main;

import com.group5.entity.Player;

/**
 * The {@code EventHandler} class manages special in-game events such as damage zones and teleportation.
 * It monitors the player's position and triggers appropriate responses when specific tiles or event
 * areas are interacted with. Events can include traps, transitions between maps, or scripted interactions.
 * <p>
 * This class uses a 3D array of {@link EventRect} objects to track event zones for each map tile and
 * ensures that repeated triggering of the same event is prevented until the player moves away.
 * </p>
 * 
 * @author Group 5
 * @version 1.0
 */
public class EventHandler {

    /** Reference to the main {@link GamePanel} instance controlling game state and entities. */
    GamePanel gp;

    /** 3D array representing all event trigger areas across different maps. */
    EventRect[][][] eventRect;

    /** Tracks the previous X and Y position where an event occurred. */
    int previousEventX, getPreviousEventY;

     /** Indicates whether the player can currently trigger an event. */
    boolean canTouchEvent = true;

    /**
     * Constructs a new {@code EventHandler} and initializes event rectangles for each map tile.
     *
     * @param gp the main {@link GamePanel} instance containing the game world data
     */
    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;

        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 20;
            eventRect[map][col][row].height = 20;
            eventRect[map][col][row].eventRectDefultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;

                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }

    }

    /**
     * Checks if the player is currently standing in or near any event area and triggers appropriate
     * event responses such as teleportation or environmental hazards.
     * <p>
     * This method ensures that players cannot repeatedly trigger the same event without moving away.
     * </p>
     */
    public void checkEvent() {

        // check if player is more than 1 tile away so they dont get hurt again
        int xDistance = Math.abs(gp.player.worldx - previousEventX);
        int yDistance = Math.abs(gp.player.worldy - getPreviousEventY);
        int distance = Math.max(xDistance, yDistance);

        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if(canTouchEvent == true) {

            // FIRE PUNISHMENT
            if(hit(3, 25, 19) == true) { 
                firePunishment(gp.dialogueState);
            }
            else if(hit(3, 44, 3) == true) { 
                firePunishment(gp.dialogueState);
            }
            else if(hit(3, 65, 2) == true) { 
                firePunishment(gp.dialogueState);
            }
            else if(hit(3, 55, 7) == true) { 
                firePunishment(gp.dialogueState);
            }
            else if(hit(3, 39, 10) == true) { 
                firePunishment(gp.dialogueState);
            }
            else if(hit(3, 65, 13) == true) { 
                firePunishment(gp.dialogueState);
            }
            else if(hit(3, 65, 26) == true) { 
                firePunishment(gp.dialogueState);
            }
            else if(hit(3, 44, 15) == true) { 
                firePunishment(gp.dialogueState);
            }
            else if(hit(3, 43, 27) == true) { 
                firePunishment(gp.dialogueState);
            }
            else if(hit(3, 33, 21) == true) { 
                firePunishment(gp.dialogueState);
            }
            else if(hit(3, 29, 27) == true) { 
                firePunishment(gp.dialogueState);
            }
            else if(hit(3, 55, 20) == true) { 
                firePunishment(gp.dialogueState);
            }
            else if(hit(3, 25, 5) == true) { 
                firePunishment(gp.dialogueState);
            }


            // BOMB PUNISHMENT
            if(hit(2, 17, 13) == true) { 
                bombPunishment(gp.dialogueState);
            }
            else if(hit(2, 22, 17) == true) { 
                bombPunishment(gp.dialogueState);
            }
            else if(hit(2, 31, 15) == true) { 
                bombPunishment(gp.dialogueState);
            }
            else if(hit(2, 34, 18) == true) { 
                bombPunishment(gp.dialogueState);
            }
            else if(hit(2, 37, 9) == true) { 
                bombPunishment(gp.dialogueState);
            }

            // TELEPORTATION
            else if(hit(0, 1, 7) == true && (gp.player.hasKey == 1 || gp.player.hasKey == 2)) {
                teleport(1, 40, 7);
            }
            else if(hit(1, 40, 7) == true && (gp.player.hasKey == 2 || gp.player.hasKey == 3)){
                teleport(0,1,7);
            }
            else if(hit(0, 18, 7) == true && (gp.player.hasKey == 1 || gp.player.hasKey == 2)){
                teleport(2,16,10);
            }
            else if(hit(2, 16, 10) == true && (gp.player.hasKey == 2 || gp.player.hasKey == 3)){
                teleport(0,18,7);
            }
            else if(hit(0, 10, 1) == true && gp.player.hasKey == 3){
                teleport(3,1,15);
            }
            else if(hit(0, 9, 1) == true && gp.player.hasKey == 3){
                teleport(3,1,15);
            }
            
        }

    }

    /**
     * Determines whether the player is currently colliding with a specific event rectangle
     * on the given map and tile coordinates.
     *
     * @param map the map number to check for events
     * @param col the column coordinate of the event
     * @param row the row coordinate of the event
     * @return {@code true} if the player has collided with the event rectangle; {@code false} otherwise
     */
    public boolean hit(int map,int col, int row) {
        boolean hit = false;
        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldx + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldy + gp.player.solidArea.y;
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][col][row])) {
                hit = true;

                previousEventX = gp.player.worldx;
                getPreviousEventY = gp.player.worldy;
            }
            // reset values
            gp.player.solidArea.x = gp.player.solidAreaDefultX;
            gp.player.solidArea.y = gp.player.solidAreaDefultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefultY;
    }
        return hit;
    }

    /**
     * Triggers a fire-related punishment event when the player touches a hazardous tile.
     * <p>
     * This reduces the player's life, displays a message, and prevents immediate retriggering
     * until the player moves away from the event zone.
     * </p>
     *
     * @param gameState the state to switch the game into (e.g., dialogue state)
     */
    public void firePunishment(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You burned yourself";
        gp.player.life -= 1;
        canTouchEvent = false;
    }


    public void bombPunishment(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You got a bomb for christmas";
        gp.player.life -= 1;
        canTouchEvent = false;
    }


    /**
     * Teleports the player to a specified map and tile location.
     * <p>
     * This method updates the player’s world coordinates and prevents immediate retriggering
     * of the teleport event.
     * </p>
     *
     * @param map the destination map index
     * @param col the destination column coordinate
     * @param row the destination row coordinate
     */
    public void teleport(int map, int col, int row) {

        gp.currentMap = map;
        gp.player.worldx = gp.tileSize * col;
        gp.player.worldy = gp.tileSize * row;
        previousEventX = gp.player.worldx;
        getPreviousEventY = gp.player.worldy;
        canTouchEvent = false;
    }
}