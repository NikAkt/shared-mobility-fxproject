package org.example.sharedmobilityfxproject.model;

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

//          Label label = new Label(this.toString());
//
//          getChildren().add(label);

        setOpacity(0.9);
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

    public String toString() {
        return this.column + "/" + this.row;
    }
}
