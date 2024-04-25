package org.example.sharedmobilityfxproject.model;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

import java.io.File;


    public class Player implements motion {
        private int x; // x position
        private int y; // y position
        private double stamina; // stamina
        private int speed; // speed
        public double speedTime = .25;
        public Node playerVisual;
        private double co2; // co2 produced
        private Cell playerCell;

        //Check method of movement
        public boolean isWalking = false;
        public boolean isUnderground = false;

        public Player(int x, int y,double stamina,int speed,double co2) {
            this.x = x;
            this.y = y;
            this.stamina=100;
            this.speed=10;
            this.co2=0;

            Image sprite = new Image(new File("src/main/resources/images/playerSprite.png").toURI().toString());
            this.playerVisual = new ImageView(sprite);
            ((ImageView) this.playerVisual).setFitHeight(10); // Set the size as needed
            ((ImageView) this.playerVisual).setFitWidth(30);
            ((ImageView) this.playerVisual).setPreserveRatio(true);
            ((ImageView) this.playerVisual).getStyleClass().add("player"); // TODO: not working delete it if not needed
        }

        /**
         * Returns the x-coordinate of the player.
         *
         * @return The x-coordinate of the player.
         */
        public int getCoordX() {
            return this.x;
        }

        /**
         * Returns the y-coordinate of the player.
         *
         * @return The y-coordinate of the player.
         */
        public int getCoordY() {
            return this.y;
        }

        /**
         * Returns the stamina of the player.
         *
         * @return The stamina of the player.
         */
        public double getStamina() {
            return stamina;
        }

        /**
         * Sets the speed of the player.
         *
         * @param speed The speed to set for the player.
         */
        public void setSpeed(int speed) {
            this.speed = speed;
        }

        /**
         * Returns the speed of the player.
         *
         * @return The speed of the player.
         */
        public int getSpeed() {
            return speed;
        }

        /**
         * Returns the CO2 produced by the player.
         *
         * @return The CO2 produced by the player.
         */
        public double getCo2() {
            return co2;
        }

        /**
         * Sets the CO2 produced by the player.
         *
         * @param co2 The CO2 to set for the player.
         */
        public void setCo2(double co2) {
            this.co2 = co2;
        }

        /**
         * Sets the stamina of the player.
         *
         * @param stamina The stamina to set for the player.
         */
        public void setStamina(double stamina) {
            this.stamina = stamina;
        }

        @Override
        public void moveUp() {
            setIsWalking(true);
            y -= speed;
        }

        @Override
        public void moveDown() {
            y += speed;
        }

        @Override
        public void moveLeft() {
            x -= speed;
        }

        @Override
        public void moveRight() {
            x += speed;
        }

        /**
         * Decreases the player's stamina.
         * This method reduces the player's stamina by 10 units if the current stamina is greater than 0.
         * If the stamina becomes less than 0 after the reduction, it is set to 0.
         */
        public void decreaseStamina() { //alternatively we could have each item change the stamina
            if (this.stamina > 0) {
                this.stamina -= 5;
                if (this.stamina < 0) {
                    this.stamina = 0;
                }
            }
        }

        /**
         * Increases the player's stamina.
         * This method increases the player's stamina by 5 units if the current stamina is less than 95.
         * If the stamina is 95 or more, it is set to 100.
         * It also prints the new stamina value to the console.
         */
        public void increaseStamina() {
            if (stamina < 95) {
                stamina += 5;
            } else {
                stamina = 100;
            }
            System.out.println("Stamina increased to: " + stamina);
        }

        /**
         * Initializes the cell in which the player is located based on the grid.
         *
         * @param grid The grid on which the player is located.
         */
        public void initCell(Grid grid) {
            this.playerCell = grid.getCell(this.getCoordY(), this.getCoordX());
        }

        /**
         * Sets the x-coordinate of the player.
         *
         * @param i The x-coordinate to set for the player.
         */
        public void setX(int i) {
            this.x = i;
        }

        /**
         * Sets the y-coordinate of the player.
         *
         * @param j The y-coordinate to set for the player.
         */
        public void setY(int j) {
            this.y = j;
        }

        /**
         * Sets the cell for the player and moves the player to the new cell with an animation.
         * If the player is already in a cell, it moves the player from the current cell to the new cell.
         * If the player is not in a cell, it sets the new cell as the player's cell and adds the player to the new cell.
         *
         * @param newCell The new cell to set for the player.
         * @param grid The grid on which the player and cell are located.
         */
        public void setCell(Cell newCell, Grid grid) {
            // Retrieve grid dimensions
            double cellWidth = grid.getWidth() / grid.getColumns();
            double cellHeight = grid.getHeight() / grid.getRows();

            // Reset translation before any new setup
            playerVisual.setTranslateX(0);
            playerVisual.setTranslateY(0);

            if (this.playerCell != null) {
                // Calculate the starting positions based on grid cells directly
                double startX = this.playerCell.getColumn() * cellWidth;
                double startY = this.playerCell.getRow() * cellHeight;
                double endX = newCell.getColumn() * cellWidth;
                double endY = newCell.getRow() * cellHeight;

                // Calculate translation distances
                double translateX = endX - startX;
                double translateY = endY - startY;

                // Set the visual to the current logical position before starting animation
                playerVisual.relocate(startX, startY);

                // Create a translation transition
                TranslateTransition tt = new TranslateTransition(Duration.seconds(speedTime), playerVisual);
                tt.setByX(translateX);
                tt.setByY(translateY);
                tt.setOnFinished(event -> {
                    // After animation, ensure the logical state matches the visual state
                    this.playerCell = newCell;
                    this.x = newCell.getColumn();
                    this.y = newCell.getRow();

                    // Visual is already in place, ensure it's part of the new cell
                    if (!newCell.getChildren().contains(playerVisual)) {
                        newCell.getChildren().add(playerVisual);
                    }

                    // Reset translations to ensure the visual does not drift
                    playerVisual.setTranslateX(0);
                    playerVisual.setTranslateY(0);
                    // Highlight the new cell
                });
                tt.play();

                // Unhighlight the old cell
                this.playerCell.unhighlight();
            } else {
                // Set the player cell if not previously set
                updatePosition(newCell);

                newCell.getChildren().add(playerVisual); // Add visual to the new cell
                // Highlight the new cell
            }
        }

        // Example ! This method should be customized to fit your design
        private Node getPlayerVisual(Grid grid) {
            // This method should return the Node that visually represents the player.
            // It might be a subnode of the cell or a completely separate Node depending on your design.
            // Here's a simple example:
            if (this.playerVisual == null) {
                this.playerVisual = new Circle(grid.getWidth() / grid.getColumns(), Color.BLUE); // Just an example, customize as needed
                this.playerCell.getChildren().add(this.playerVisual);
            }
            return this.playerVisual;

        }

        /**
         * Updates the player's position to a new cell.
         * This method sets the player's current cell to the new cell, updates the player's x and y coordinates to match the new cell's column and row, and highlights the new cell.
         *
         * @param newCell The new cell to which the player's position should be updated.
         */
        public void updatePosition(Cell newCell) {
            this.playerCell = newCell;
            this.x = newCell.getColumn();
            this.y = newCell.getRow();
            this.playerCell.highlight(); // Highlight the new cell
        }

        /**
         * Sets the player's cell based on given x and y coordinates.
         * This method sets the player's current cell's column and row to the given x and y coordinates, and updates the player's x and y coordinates to match.
         *
         * @param grid The grid on which the player and cell are located.
         * @param x The x-coordinate (column) to set for the player's cell.
         * @param y The y-coordinate (row) to set for the player's cell.
         */
        public void setCellByCoords(Grid grid, int x, int y) {
            this.playerCell.setColumn(x);
            this.playerCell.setRow(y);
            this.x = x;
            this.y = y;
        }

        /**
         * Returns the cell in which the player is currently located.
         *
         * @return The cell in which the player is currently located.
         */
        public Cell getCell() {
            return this.playerCell;
        }

        /**
         * Sets the walking status of the player.
         *
         * @param isWalking The walking status to set. If true, the player is walking. If false, the player is not walking.
         */
        public void setIsWalking(boolean isWalking) {
            this.isWalking = isWalking;
        }

        /**
         * Gets the walking status of the player.
         *
         * @return The walking status of the player. Returns true if the player is walking, false otherwise.
         */
        public boolean getIsWalking() {
            return this.isWalking;
        }

    }
