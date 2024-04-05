package org.example.sharedmobilityfxproject.model;


import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.Objects;
public class busStop extends Cell{
    private int x;
    private int y;
    private int flagIsPlayerHere;

    public busStop(int i,int j ){
        super(i, j);
        this.x = i;
        this.y = j;
        this.flagIsPlayerHere = 0;
        getStyleClass().add("busStop");
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    };}
public int isPlayerHere() {
    return this.flagIsPlayerHere;
}

