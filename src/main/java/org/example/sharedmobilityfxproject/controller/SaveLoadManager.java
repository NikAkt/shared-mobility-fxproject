package org.example.sharedmobilityfxproject.controller;

import org.example.sharedmobilityfxproject.controller.GameController;

import java.io.*;

public class SaveLoadManager {
    private static final String SAVE_FILE_PATH = "game_save.dat";

    public static void saveGame(GameController gameController) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH))) {
            out.writeObject(gameController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameController loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH))) {
            return (GameController) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
