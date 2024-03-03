package org.example.sharedmobilityfxproject.model;

public class Player {
    public static int x,y; // x,y position
    public int stamina; // stamina
    public static int speed; // speed
    public double co2; // co2 produced
    private int GemCount;

public Player(int x, int y,int stamina, int speed, int co2, int GemCount) {

    this.x = x;
    this.y = y;
    this.stamina = 100;
    this.speed = 10;
    this.co2 = 0;
    this.GemCount = GemCount;

    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setCo2(int co2) {
        this.co2 = co2;
    }
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public static int getCoordX() {
        return x;
    }
    public static int getCoordY() {
        return y;
    }
    public int getStamina() {
        return stamina;
    }
    public double getSpeed() {
        return speed;
    }
    public double getCo2() {
        return co2;
    }
    public void moveUp(){
        y=-speed;
    }

    public static void moveDown(){
        y=+speed;
    }
    public void moveLeft(){
        x=-speed;
    }
    public static void moveRight(){
        x=+speed;
    }
    public void decreaseStamina(){ //alternatively we could have each item change the stamina
        stamina=-10;
    }
    public void increaseStamina(){
        stamina=+10;
    }


}

