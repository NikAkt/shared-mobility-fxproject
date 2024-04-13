package org.example.sharedmobilityfxproject.model;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;

/**
 * Cell class represents a single cell within a grid.
 * It manages the visual representation and state of the cell, such as highlighting.
 */
public class Cell extends StackPane {
    int column;
    int row;

    public Cell(int column, int row) {
        this.column = column;
        this.row = row;
        getStyleClass().add("cell");
    }

    public void colorCell(int column, int row,String color) {
        this.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 0;");
    }

    public void highlight() {
        // ensure the style is only once in the style list
        getStyleClass().remove("cell-highlight");

        // add style
        getStyleClass().add("cell-highlight");
    }

    public void unhighlight() {
        getStyleClass().remove("cell-highlight");
    }
    public void animateMove(double newX, double newY) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.075), this);
        transition.setToX(newX - this.getLayoutX());
        transition.setToY(newY - this.getLayoutY());
        transition.play();
    }
    public void hoverHighlight() {
        // ensure the style is only once in the style list
        getStyleClass().remove("cell-hover-highlight");

        // add style
        getStyleClass().add("cell-hover-highlight");
    }

    public void hoverUnhighlight() {
        getStyleClass().remove("cell-hover-highlight");
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
    public void setColumn(int i ){
        this.column = i;
    }
    public void setRow(int j ){
        this.row = j;
    }

    public String toString() {
        return this.column + "/" + this.row;
    }
}
