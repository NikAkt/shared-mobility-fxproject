package org.example.sharedmobilityfxproject.model;

public class Player implements motion {
    private int x; // x position
    private int y; // y position
    private int speed; // speed
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
    public void setCell(Cell cell) {

        this.playerCell = cell;
        this.x = cell.getColumn();
        this.y = cell.getRow();
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
