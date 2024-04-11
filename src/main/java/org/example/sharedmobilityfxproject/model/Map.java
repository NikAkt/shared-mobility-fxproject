package org.example.sharedmobilityfxproject.model;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Map {

    /**
     * Initialises the map with a specified background image.
     */
    public Map() {
    }

    public static void main(String[] args) throws Exception {
        // Load the image
        File imageFile = new File("path_to_your_image.png");
        BufferedImage image = ImageIO.read(imageFile);

        // The grid size
        int rows = 80;
        int columns = 120;
        int[][] mapArray = new int[rows][columns];

        // Dimensions of each cell
        int cellWidth = image.getWidth() / columns;
        int cellHeight = image.getHeight() / rows;

        // Process each cell
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                // Get the color of the center pixel in the current cell
                int pixel = image.getRGB(column * cellWidth + cellWidth / 2, row * cellHeight + cellHeight / 2);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                // Assign values based on color
                if (red == 255 && green == 255 && blue == 255) {
                    // White
                    mapArray[row][column] = 0;
                } else if (red == 0 && green == 255 && blue == 0) {
                    // Green
                    mapArray[row][column] = 1;
                } else if (red == 128 && green == 128 && blue == 128) {
                    // Grey
                    mapArray[row][column] = 4;
                } else {
                    // Default case, might need to adjust based on actual image
                    mapArray[row][column] = -1;
                }
            }
        }

        // Here you can now use mapArray as needed
    }
}