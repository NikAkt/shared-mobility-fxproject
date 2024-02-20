package org.example;

public class Player {
    public int x; // x position
    public int y; // y position
    public int stamina; // stamina
    public int speed; // speed
    public double co2; // co2 produced


public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.stamina=100;
        this.speed=10;
        this.co2=0;
    }

    public int getCoordX() {
        return x;
    }
    public int getCoordY() {
        return y;
    }
    public int getStamina() {
        return stamina;
    }
    public int getSpeed() {
        return speed;
    }
    public double getCo2() {
        return co2;
    }
    public void moveUp(){
        y=y-speed;
    }
    public void moveDown(){
        y=y+speed;
    }
    public void moveLeft(){
        x=x-speed;
    }
    public void moveRight(){
        x=x+speed;
    }
    public void decreaseStamina(){ //alternatively we could have each item change the stamina
        stamina=stamina-10;
    }
    public void increaseStamina(){
        stamina=stamina+10;
    }

}
