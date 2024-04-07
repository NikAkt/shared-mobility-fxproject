package org.example.sharedmobilityfxproject.model;

public class Player implements motion {
    private int x; // x position
    private int y; // y position
    private int stamina; // stamina
    private int speed; // speed
    private double co2; // co2 produced
    private Cell playerCell;


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
        this.playerCell = grid.getCell(this.getCoordX(), this.getCoordY());
    }

    public Cell getCell() {
        return this.playerCell;
    }

}
