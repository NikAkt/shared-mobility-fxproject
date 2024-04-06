package org.example.sharedmobilityfxproject.model.tranportMode;


import org.example.sharedmobilityfxproject.model.Cell;
import org.example.sharedmobilityfxproject.model.Grid;
import org.example.sharedmobilityfxproject.model.busStop;

import java.util.ArrayList;

public class Bus extends Cell {

    private int speed;
    private double carbonFootprintAmount;
    private final double carbonFootprintPerKm = 97; // Specific rate for a bus
    private int x;
    private int y;
    public boolean isWaiting = false;
    public int waitTime = 0;
    public int flagMove = 0;
    private ArrayList stopList;
    public Bus(ArrayList<busStop> stops,int i, int j) {
        super(i, j); // Assuming default speed is 10 and CO2 is 0 for initialization
        this.x = i;
        this.y = j;
        this.getStyleClass().add("bus");
        stopList  = stops;

         // Initial carbon footprint amount for Bus
    }

    public void setX(int i ){
        this.x = i;
    }
    public void setY(int j ){
        this.y = j;
    }
    public int getX(){
        return this.x;
    }
    public busStop nextStop() { // Method now correctly returns a BusStop object
        return this.list().get(0);
    }
    public ArrayList<busStop> list() { // Specify the return type here
        return stopList;
    }
    public int getY(){
        return this.y;
    }
    // Method to calculate carbon footprint based on distance traveled, specific to the Bus.
    public double calculateCarbonFootprint(double distance) {
        double journeyCarbonFootprint = carbonFootprintPerKm * distance;
        // Update the total carbon footprint
        this.carbonFootprintAmount += journeyCarbonFootprint;
        return journeyCarbonFootprint;
    }
    // bus waiting at stop method
    public void waitASec() {

        busStop stop = (busStop) stopList.get(0);
        // Check if the current X coordinate of the bus matches the X coordinate of the first bus stop and if bus is currently not waiting.
        if(this.x == stop.getX() && this.y == stop.getY()&& !this.isWaiting) {
            // Bus is waiting at a stop
            this.isWaiting = true;
            this.waitTime = 10;
        } else {
            this.isWaiting = false;
        }
    }
    // Getter and Setter for speed
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // Getter and Setter for carbonFootprintAmount
    public double getCarbonFootprintAmount() {
        return carbonFootprintAmount;
    }
//    public void moveBus(int x,int y) {
//        // Example movement logic: move the bus to the right and loop around
//        this.move
//
//        // Assuming there's a method in Grid to move items or update the cell's content
//        // This part depends on how your Grid and Cell classes are implemented
//        // You need to implement this method based on your design
//
//        // If your grid doesn't directly support moving items like this, you may need to manually update cells to reflect the bus's new position
//    }

    public void setCarbonFootprintAmount(double carbonFootprintAmount) {
        this.carbonFootprintAmount = carbonFootprintAmount;
    }
}
