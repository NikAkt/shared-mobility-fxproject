package org.example.sharedmobilityfxproject.model;

import javafx.scene.layout.StackPane;

public class Cell extends StackPane {

    int column;
    int row;

    // Constructor to initialise cell coordinates
    public Cell(int column, int row) {
        this.column = column;
        this.row = row;
        getStyleClass().add("cell");
        // Label label = new Label(this.toString());
        // getChildren().add(label);
        setOpacity(0.9);
    }

    // Highlight the cell
    public void highlight() {
        // Ensure the style is only coded once in the style list
        getStyleClass().remove("cell-highlight");
        // Add style
        getStyleClass().add("cell-highlight");
    }

    // Unhighlight the cell
    public void unhighlight() {
        getStyleClass().remove("cell-highlight");
    }

    // Highlight the cell when hovering over it
    public void hoverHighlight() {
        // Ensure the style is only coded once in the style list
        getStyleClass().remove("cell-hover-highlight");
        // Add style
        getStyleClass().add("cell-hover-highlight");
    }

    // Unhighlight the cell when hovering over it
    public void hoverUnhighlight() {
        getStyleClass().remove("cell-hover-highlight");
    }

    // Override toString method to provide coordinates
    public String toString() {
        return this.column + "/" + this.row;
    }
}
