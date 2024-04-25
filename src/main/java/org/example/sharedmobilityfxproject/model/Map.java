package org.example.sharedmobilityfxproject.model;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class represents a map for a shared mobility project.
 * It provides functionality to load a map from an image and export the map to a file.
 */
public class Map {

    // The grid size
    final int ROWS;
    final int COLUMNS;

    /**
     * Default constructor for the Map class.
     */
    public Map() {
        this.ROWS = 80;
        this.COLUMNS = 120;
    }

    /**
     * Constructor for the Map class.
     *
     * @param rows    The number of rows in the map.
     * @param columns The number of columns in the map.
     */
    public Map(int rows, int columns) {
        this.ROWS = rows;
        this.COLUMNS = columns;
    }

    /**
     * The main method for testing the Map class, for Manhattan map !.
     * It creates a Map object, loads a map from an image, and exports the map to a file.
     */
    public static void main(String[] args) {
        Map map = new Map();
        try {
            int[][] arr = map.loadMap("Manhattan");
            System.out.println(arr);
            map.exportArrayToFile(arr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a map from an image.
     * The image is divided into a grid, and each cell is assigned a value based on its color.
     *
     * @return A 2D array representing the map.
     * @throws Exception If an error occurs while loading the image.
     */
    public int[][] loadMap(String mapName) throws Exception {
        // Load the image
        File imageFile = new File(String.format("src/main/resources/images/%sMap.png", mapName));
        BufferedImage image = ImageIO.read(imageFile);

        // The grid size
        int[][] mapArray = new int[ROWS][COLUMNS];

        // Clear the array
        clearArray(mapArray);

        // Dimensions of each cell
        int cellWidth = image.getWidth() / COLUMNS;
        int cellHeight = image.getHeight() / ROWS;

        // Define colors
        Color YELLOW_BUSSTOP = Color.decode("#fff200");
        Color YELLOW_BUSSTOP2 = Color.decode("#FFF210");
        Color YELLOW_BUSSTOP3 = Color.decode("#fff220");
        Color YELLOW_BUSSTOP4 = Color.decode("#fff230");
        Color MAROON_METRO = Color.decode("#990030");
        Color ORANGE_BICYCLE = Color.decode("#FFB200");
        Color GRAY_OBSTACLE = Color.decode("#808080");
        Color BLUE_LAKE = Color.decode("#0000ff");
        Color GREEN_GRASS = Color.decode("#00ff00");
        Color WHITE_ROAD = Color.decode("#FFFFFF");
        Color BLACK_BLOCKAGE = Color.decode("#000000");
        Color FINISH = Color.decode("#00EE00");
        // Process each cell
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                // Get the color of the center pixel in the current cell
                Color pixelColor = new Color(image.getRGB(column * cellWidth + cellWidth / 2, row * cellHeight + cellHeight / 2));

                // Assign values based on color
                if (pixelColor.equals(YELLOW_BUSSTOP)) {
                    // Assign a special value when the pixel color is YELLOW_BUSSTOP
                    mapArray[row][column] = 4;
                } else if (pixelColor.equals(YELLOW_BUSSTOP2)) {
                    // Assign a special value when the pixel color is YELLOW_BUSSTOP2
                    mapArray[row][column] = 42;
                } else if (pixelColor.equals(YELLOW_BUSSTOP3)) {
                    // Assign a special value when the pixel color is YELLOW_BUSSTOP3
                    mapArray[row][column] = 43;
                } else if (pixelColor.equals(YELLOW_BUSSTOP4)) {
                    // Assign a special value when the pixel color is YELLOW_BUSSTOP4
                    mapArray[row][column] = 44;
                } else if (pixelColor.equals(MAROON_METRO)) {
                    // Assign a special value when the pixel color is MAROON_METRO
                    mapArray[row][column] = 5;
                } else if (pixelColor.equals(ORANGE_BICYCLE)) {
                    // Assign a special value when the pixel color is ORANGE_BICYCLE
                    mapArray[row][column] = 6;
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
                } else if (pixelColor.equals(FINISH)) {
                    // Default case, might need to adjust based on actual image
                    mapArray[row][column] = 10;
                } else {
                    // Default case, might need to adjust based on actual image
                    mapArray[row][column] = -1;
                }
            }
        }

        return mapArray;
    }

    /**
     * Clears the given 2D array by setting all elements to 0.
     *
     * @param array The array to clear.
     */
    private void clearArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = 0;
            }
        }
    }

    /**
     * Exports a map to a file.
     * The map is represented as a 2D array, and each row of the array is written as a line in the file.
     *
     * @param mapArray The 2D array representing the map.
     */
    public void exportArrayToFile(int[][] mapArray) {

        // The file you want to write to
        File file = new File("mapArray.txt");

        try (PrintWriter pw = new PrintWriter(file)) {
            for (int[] ins : mapArray) {
                for (int j = 0; j < ins.length; j++) {
                    pw.print(ins[j] + (j < ins.length - 1 ? ", " : ""));
                }
                pw.println();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public int[][] getMapArray(String mapName) {

        int[][] arr = new int[ROWS][COLUMNS]; // change the rows and columns for your convinience
        try {
            arr = loadMap(mapName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }
}