package com.group5.tile;

import com.group5.main.GamePanel;
import com.group5.main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Manages all the tiles in the game world.
 * Handles loading tile images, setting collision properties, loading maps from files,
 * and drawing visible tiles to the screen.
 */
public class TileManager {
    /** Reference to the main GamePanel for accessing game properties and player info. */
    GamePanel gp;

    /** Array of all tile types used in the game. */
    public Tile[] tile;

    /** 3D array storing tile numbers for each map and world position. */
    public int mapTilenum[][][];

    /** Flag to draw the path overlay for debugging or pathfinding visualization. */
    boolean drawPath = true;

    /**
     * Constructor for the TileManager.
     * Initializes tile array, loads all tile images, and loads maps into memory.
     * 
     * @param gp Reference to the GamePanel
    */
    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[35];
        mapTilenum = new int[gp.maxMap][gp.maxWorldCol] [gp.maxWorldRow];
        //mapTilenum2 = new int[gp.maxScreenCol] [gp.maxScreenRow];

        getTileImage();
        loadMap("/maps/Lobby.txt", 0); // will load map chang args for diff map
        loadMap("/maps/worldmap.txt", 1);
        loadMap("/maps/ChristmasMap.txt", 2);
        loadMap("/maps/Basement.txt", 3);
    }

    /**
     * Loads and sets up all tile images used in the game.
     * Calls setUp() for each tile index to initialize its image and collision properties.
     */
    public void getTileImage() {

            setUp(0, "BrickG",true);
            setUp(1, "Corner_Brick",true);
            setUp(2, "Wood",false);
            setUp(3, "Torch",true);
            setUp(4, "EvilP",true);
            setUp(5, "NormalP",false);
            setUp(6, "TimeP",false);

            setUp(7, "Fire",false);
            setUp(8, "Black", false);
            setUp(9, "LobbyFloor", false);
            setUp(10, "LobbyWall", true);
            setUp(11, "BottomLeftRug", false);
            setUp(12, "BottomRightRug", false);
            setUp(13, "TopLeftRug", false);
            setUp(14, "TopRightRug", false);
            setUp(15, "Desk1", true);
            setUp(16, "deadPlant", true);
            setUp(17, "floor", false);
            setUp(18, "wall", true);
            setUp(19, "door", true);
            setUp(20, "stool", false);
            
            setUp(21, "woodBlock", true);
            setUp(22, "CandyCane", false);
            setUp(23, "chimney", false);
            setUp(24, "WoodDoor", false);
            setUp(25, "Ice", false);
            setUp(26, "Snow", false);
            setUp(27, "snowing", true);
            setUp(28, "Soil", true);
            setUp(29, "BOMB_TILE", false);
            setUp(30, "RedBlock", true);

            setUp(31, "DoorT", false);
            
    }

    /**
     * Initializes a single tile at a given index.
     * Loads its image, scales it to the tile size, and sets its collision property.
     * 
     * @param index Index in the tile array
     * @param imagePath Path to the tile image (without the /tiles/ prefix and .png extension)
     * @param collision Whether the tile blocks movement
     */
    public void setUp(int index, String imagePath, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath +".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch (IOException e) {
           e.printStackTrace();
        }
    }

     /**
     * Loads a map from a text file into the mapTilenum array.
     * Each number in the file corresponds to a tile index.
     * 
     * @param filePath Path to the map file
     * @param map The index of the map in the mapTilenum array
     */
    public void loadMap(String filePath, int map){

        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;


            // WORLD MAP
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                //String numbers[] = line.split(" ");


                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTilenum[map][col][row] = num;
                    //System.out.println(num);
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();


        }catch(Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Draws all visible tiles to the screen based on the player's position.
     * Optionally overlays the path for debugging/pathfinding visualization.
     * 
     * @param g2 Graphics2D object used to draw tiles
     */
    public void draw(Graphics2D g2) {


        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTilenum[gp.currentMap][worldCol][worldRow];

            //System.out.println(tileNum);

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldx + gp.player.screenX;
            int screenY = worldY - gp.player.worldy + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldx - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldx + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldy - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldy + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;


            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }

        /* 
        if(drawPath == true) {
            g2.setColor(new Color(225,0, 0, 70));
            for(int i = 0; i < gp.pFinder.pathList.size(); i++) {
                int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldx + gp.player.screenX;
                int screenY = worldY - gp.player.worldy + gp.player.screenY;
                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }
        */ 
    }
}