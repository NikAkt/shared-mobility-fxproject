package org.example.sharedmobilityfxproject.model;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<MapTile> tiles; // Represent each spot on the map as a tile.

    public Map() {
        tiles = new ArrayList<>();
        initializeTiles();
    }

    private void initializeTiles() {
        // Example initialization
        tiles.add(new MapTile("Start", TileType.START));
        tiles.add(new MapTile("Dublin", TileType.CITY));
        tiles.add(new MapTile("Community Chest", TileType.COMMUNITY_CHEST));
        // Add other tiles similarly. Consider using a data-driven approach to load this from a file.
    }

    // Additional methods for interacting with the map
}

class MapTile {
    private String name;
    private TileType type;

    public MapTile(String name, TileType type) {
        this.name = name;
        this.type = type;
    }

    // Getters and additional methods
}

enum TileType {
    START, CITY, COMMUNITY_CHEST, CHANCE, JAIL, FREE_PARKING, GO_TO_JAIL, TRANSPORT
}