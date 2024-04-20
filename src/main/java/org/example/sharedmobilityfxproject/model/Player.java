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
    private int speed; // speed
    public double speedTime = .25;
    private Node playerVisual;
    private double co2; // co2 produced
    private Cell playerCell;
    public boolean isUnderground= false;
    public double staminaDrain=1;
    public double stamina;

    public Player(int x, int y,int stamina,int speed,double co2) {
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
    }

    public int getCoordX() {
        return this.x;
    }
    public int getCoordY() {
        return this.y;
    }
    public double getStamina() {
        return stamina;
    }
    public void setSpeed(int speed) {
    this.speed=speed;
    }
    public int getSpeed() {
        return speed;
    }
    public double getCo2() {
        return co2;
    }
    public void setCo2(double co2) {
        this.co2=co2;
    }
    public void setStamina(double stamina) {
        this.stamina=stamina;
    }
    @Override
    public void moveUp() {
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

    public void decreaseStamina(){ //alternatively we could have each item change the stamina
        stamina=stamina-10;
    }
    public void increaseStamina(){
        stamina=stamina+10;
    }

    public void initCell(Grid grid) {
        this.playerCell = grid.getCell(this.getCoordY(), this.getCoordX());
    }
    public void setX(int i){
        this.x =i;
    }
    public void setY(int j){
        this.y = j;
    }
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


    public void updatePosition(Cell newCell) {
        this.playerCell = newCell;
        this.x = newCell.getColumn();
        this.y = newCell.getRow();
        this.playerCell.highlight(); // Highlight the new cell
    }


    public void setCellByCoords(Grid grid, int x, int y) {
        this.playerCell.setColumn(x);
        this.playerCell.setRow(y);
        this.x = x;
        this.y = y;
    }

    public Cell getCell() {
        return this.playerCell;
    }

    public double getStaminaDrain(){
        return this.staminaDrain;
    }
    public void setStaminaDrain(double staminaDrain){
        this.staminaDrain=staminaDrain;
    }

}
