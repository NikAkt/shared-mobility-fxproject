package org.example.sharedmobilityfxproject.model;

public class Player {
    public static int x; // x position
    public static int y; // y position
    public int stamina; // stamina
    public static int speed; // speed
    public double co2; // co2 produced


public Player(int x, int y,int stamina,int speed,double co2,int gems) {
        this.x = x;
        this.y = y;
        this.stamina=100;
        this.speed=10;
        this.co2=0;
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
    public void moveUp(){
        y=y-speed;
    }
    public static void moveDown(){
        y=y+speed;
    }
    public void moveLeft(){
        x=x-speed;
    }
    public static void moveRight(){
        x=x+speed;
    }
    public void decreaseStamina(){ //alternatively we could have each item change the stamina
        stamina=stamina-10;
    }
    public void increaseStamina(){
        stamina=stamina+10;
    }

}
