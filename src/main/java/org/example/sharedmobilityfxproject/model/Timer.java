package org.example.sharedmobilityfxproject.model;



import javafx.animation.AnimationTimer;


public class Timer extends AnimationTimer {
    private long startTime;
    private long endTime;
    private boolean running = false;


    @Override
    public void start() {
        super.start();
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }


    @Override
    public void stop() {
        super.stop();
        this.running = false;
    }


    @Override
    public void handle(long now) {
        if (running) {
            long currentTime = System.currentTimeMillis();
            long elapsed = currentTime - startTime;
            // Update UI or check for timeout here.
        }
    }


    // Additional timer-related methods.
}
