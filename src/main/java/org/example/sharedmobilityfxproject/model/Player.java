package org.example.sharedmobilityfxproject.model;

public class Player implements motion {
    private int x; // x position
    private int y; // y position
    private int stamina; // stamina
    private int speed; // speed
    private double co2; // co2 produced
    private Cell playerCell;

    //Check method of movement
    public boolean isWalking = false;
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

    public void decreaseStamina(){ //alternatively we could have each item change the stamina
        System.out.print("Decreasing stamina called");
        if (this.stamina > 0) {
            this.stamina -= 10;
            if (this.stamina < 0) {
                this.stamina = 0;
            }
        }
    }
    public void increaseStamina(){
        if (stamina < 95) {
            stamina += 5;
        } else {
            stamina = 100;
        }
        System.out.println("Stamina increased to: " + stamina);
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

    public void setIsWalking(boolean isWalking) {
        this.isWalking = isWalking;
    }
    public boolean getIsWalking() {
        return this.isWalking;
    }

}
