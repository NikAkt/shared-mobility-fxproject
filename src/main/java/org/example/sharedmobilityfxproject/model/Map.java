package org.example.sharedmobilityfxproject.model;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

public class Map {

    /**
     * Initialises the map with a specified background image.
     */
    public Map() {
    }
    public static void main(String[] args) {
        Map map = new Map();
        try {
            int[][] arr = map.loadMap();
            System.out.println(arr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[][] loadMap() throws Exception {
        // Load the image
        File imageFile = new File("src/main/resources/images/Manhattan.png");
        BufferedImage image = ImageIO.read(imageFile);

        // The grid size
        final int ROWS = 80;
        final int COLUMNS = 120;
        int[][] mapArray = new int[ROWS][COLUMNS];

        // Dimensions of each cell
        int cellWidth = image.getWidth() / COLUMNS;
        int cellHeight = image.getHeight() / ROWS;

        // Define colors
        Color YELLOW_BUSSTOP = Color.decode("#ffeb3b");
        Color MAROON_METRO = Color.decode("#990030");
        Color GRAY_OBSTACLE = Color.decode("#808080");
        Color BLUE_LAKE = Color.decode("#0000ff");
        Color GREEN_GRASS = Color.decode("#00ff00");
        Color WHITE_ROAD = Color.decode("#FFFFFF");
        Color BLACK_BLOCKAGE = Color.decode("#000000");

        // Process each cell
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                // Get the color of the center pixel in the current cell
                Color pixelColor = new Color(image.getRGB(column * cellWidth + cellWidth / 2, row * cellHeight + cellHeight / 2));

                // Assign values based on color
                if (pixelColor.equals(YELLOW_BUSSTOP)) {
                    // Assign a special value when the pixel color is YELLOW_BUSSTOP
                    mapArray[row][column] = 4;
                } else if (pixelColor.equals(MAROON_METRO)) {
                    // Assign a special value when the pixel color is MAROON_METRO
                    mapArray[row][column] = 5;
                } else if (pixelColor.equals(GRAY_OBSTACLE)) {
                    // Assign a special value when the pixel color is GRAY_OBSTACLE
                    mapArray[row][column] = 1;
                } else if (pixelColor.equals(BLUE_LAKE)) {
                    // Assign a special value when the pixel color is BLUE_LAKE
                    mapArray[row][column] = 3;
                } else if (pixelColor.equals(GREEN_GRASS)) {
                    // Assign a special value when the pixel color is GREEN_GRASS
                    mapArray[row][column] = 2;
                } else if (pixelColor.equals(WHITE_ROAD)) {
                    // Assign a special value when the pixel color is WHITE_ROAD
                    mapArray[row][column] = 0;
                } else if (pixelColor.equals(BLACK_BLOCKAGE)) {
                    // Assign a special value when the pixel color is BLACK_BLOCKAGE
                    mapArray[row][column] = 9;
                } else {
                    // Default case, might need to adjust based on actual image
                    mapArray[row][column] = -1;
                }
            }
        }

        return mapArray;
    }
}