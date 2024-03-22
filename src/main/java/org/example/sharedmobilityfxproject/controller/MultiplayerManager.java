package org.example.sharedmobilityfxproject.controller;

import org.example.sharedmobilityfxproject.model.Player;

public class MultiplayerManager {
    private int currentPlayerIndex = 0;
    private Player[] players = new Player[2]; // Assuming 2 players for simplicity.

    public MultiplayerManager(Player playerOne, Player playerTwo) {
        players[0] = playerOne;
        players[1] = playerTwo;
    }

    public void switchTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    // Add methods for score comparison, cooperation, etc.
}
