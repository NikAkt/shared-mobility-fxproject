package org.example.sharedmobilityfxproject.model;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
public class Player implements motion {
    private int x; // x position
    private int y; // y position
    private int stamina; // stamina
    private int speed; // speed
    private double co2; // co2 produced
    private Cell playerCell;
    public boolean isUnderground= false;

    public Player(int x, int y,int stamina,int speed,double co2,int gems) {
        this.x = x;
        this.y = y;
        this.stamina=100;
        this.speed=10;
        this.co2=0;
    }

    public int getCoordX() {
        return this.x;
    }
    public int getCoordY() {
        return this.y;
    }
    public int getStamina() {
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
    public void setStamina(int stamina) {
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
        if (this.playerCell != null) {
            // Calculate the new position for the player animation to transition to
            double cellWidth = grid.getWidth() / grid.getColumns();
            double cellHeight = grid.getHeight() / grid.getRows();
            double targetX = newCell.getColumn() * cellWidth - this.playerCell.getLayoutX(); // Change in X
            double targetY = newCell.getRow() * cellHeight - this.playerCell.getLayoutY(); // Change in Y

            // Unhighlight the old cell immediately
            this.playerCell.unhighlight();

            // Create and configure the translation transition for the visual representation of the player
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.1), this.playerCell);
            tt.setByX(targetX);
            tt.setByY(targetY);
            tt.setOnFinished(event -> updatePosition(newCell)); // Update position once animation is complete
            tt.play();
        } else {
            // If playerCell is not set, initialize it directly
            updatePosition(newCell);
        }
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



}
