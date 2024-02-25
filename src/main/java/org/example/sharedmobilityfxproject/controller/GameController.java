package org.example.sharedmobilityfxproject.controller;
import javafx.scene.layout.Pane;
import org.example.sharedmobilityfxproject.model.Map;

public class GameController {
    private Map gameMap;

    public GameController() {
        // Initialize the game map with default dimensions
        this.gameMap = new Map();
    }

    public void startGame(Pane gamePane) {
        // Start game logic here
        gameMap.render(gamePane);
        System.out.println("Game Started");
    }
}

