package org.example.sharedmobilityfxproject.controller;
import java.io.Serializable;
import java.util.Map;

/**
 * The SaveGame class represents the state of the game at a certain point in time.
 * It includes the positions of the player and the bus, as well as the current gem counter.
 * This class implements the Serializable interface, which allows it to be written to a file or sent over a network.
 */
public class SaveGame implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Boolean> stageClearFlags;

    private int playerX;
    private int playerY;
    private int busX;
    private int busY;
    private int gemCounter;

    /**
     * Constructs a new SaveGame object with the given player and bus positions, and gem counter.
     *
     * @param playerX         the x-coordinate of the player's position
     * @param playerY         the y-coordinate of the player's position
     * @param busX            the x-coordinate of the bus's position
     * @param busY            the y-coordinate of the bus's position
     * @param gemCounter      the current gem counter
     * @param stageClearFlags
     */
    public SaveGame(int playerX, int playerY, int busX, int busY, int gemCounter, Map<String, Boolean> stageClearFlags) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.busX = busX;
        this.busY = busY;
        this.gemCounter = gemCounter;
        this.stageClearFlags = stageClearFlags;
    }

    /**
     * Returns the x-coordinate of the player's position.
     *
     * @return the x-coordinate of the player's position
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     * Sets the x-coordinate of the player's position.
     *
     * @param playerX the new x-coordinate of the player's position
     */
    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    /**
     * Returns the y-coordinate of the player's position.
     *
     * @return the y-coordinate of the player's position
     */
    public int getPlayerY() {
        return playerY;
    }

    /**
     * Sets the y-coordinate of the player's position.
     *
     * @param playerY the new y-coordinate of the player's position
     */
    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    /**
     * Returns the x-coordinate of the bus's position.
     *
     * @return the x-coordinate of the bus's position
     */
    public int getBusX() {
        return busX;
    }

    /**
     * Sets the x-coordinate of the bus's position.
     *
     * @param busX the new x-coordinate of the bus's position
     */
    public void setBusX(int busX) {
        this.busX = busX;
    }

    /**
     * Returns the y-coordinate of the bus's position.
     *
     * @return the y-coordinate of the bus's position
     */
    public int getBusY() {
        return busY;
    }

    /**
     * Sets the y-coordinate of the bus's position.
     *
     * @param busY the new y-coordinate of the bus's position
     */
    public void setBusY(int busY) {
        this.busY = busY;
    }

    /**
     * Returns the current gem counter.
     *
     * @return the current gem counter
     */
    public int getGemCounter() {
        return gemCounter;
    }

    /**
     * Sets the current gem counter.
     *
     * @param gemCounter the new gem counter
     */
    public void setGemCounter(int gemCounter) {
        this.gemCounter = gemCounter;
    }

   /**
     * Returns the map of stage clear flags.
     * Each entry in the map represents a stage, with the key being the stage name and the value being a boolean indicating whether the stage has been cleared.
     *
     * @return the map of stage clear flags
     */
    public Map<String, Boolean> getStageClearFlags() {
        return stageClearFlags;
    }
}