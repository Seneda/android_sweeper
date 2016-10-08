package com.example.seneda.minesweeper;

import java.util.List;

/**
 * Created by seneda on 08/10/16.
 */

public class Cell {
    public boolean checked;

    public boolean mine;

    public int neighbour_mines;

    Cell(boolean mine){
        this.mine = mine;
        this.checked = false;
        this.neighbour_mines = 0;
    }

    void sweep(List<Cell> cells){
        if (! this.checked){
            for (Cell cell : cells) {
                if (cell.mine) {
                    this.neighbour_mines += 1;
                }
            }
            this.checked = true;
        }
    }

    String to_text(){

        if (checked){
            if (mine){
                return "X";
            } else {
                return ""+neighbour_mines;
            }
        } else {
            return "?";
        }
    }
}
