package com.example.seneda.minesweeper;

import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by seneda on 08/10/16.
 */

public class MineField
{
    Cell [][] cells;

    int rows;

    int cols;
    int mines;

    MineField(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        mines = 0;
        this.cells = new Cell[this.rows][this.cols];
        for (int c = 0; c < this.cols; c++){
            for (int r = 0; r < this.rows; r++){
                this.cells[r][c] = new Cell(Math.random() > 0.9);
                if (this.cells[r][c].mine) {
                    mines += 1;
                }
            }
        }
        sweep_all();
    }


    String to_text() {
        String text = "";
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                Cell cell = this.cells[r][c];
                text += cell.to_text();
            }
            text += "\n";
        }
        return text;
    }

    void sweep(int row, int col){
        List<Cell> neighbours = new ArrayList<Cell>();
        List<int[]> neighbour_coords = new ArrayList<int[]>();
        // top left
        if ((row > 0) && (col > 0)) {
            neighbours.add(this.cells[row-1][col-1]);
            neighbour_coords.add(new int[]{row-1, col-1});
        }
        // top
        if (row > 0) {
            neighbours.add(this.cells[row-1][col]);
            neighbour_coords.add(new int[]{row-1, col});
        }
        // top right
        if ((row > 0) && (col < this.cols-1)) {
            neighbours.add(this.cells[row-1][col+1]);
            neighbour_coords.add(new int[]{row-1, col+1});
        }
        // left
        if (col > 0) {
            neighbours.add(this.cells[row][col-1]);
            neighbour_coords.add(new int[]{row, col-1});
        }
        // right
        if (col < this.cols-1) {
            neighbours.add(this.cells[row][col+1]);
            neighbour_coords.add(new int[]{row, col+1});
        }
        // bottom left
        if ((row < this.rows-1) && (col > 0)) {
            neighbours.add(this.cells[row+1][col-1]);
            neighbour_coords.add(new int[]{row+1, col-1});
        }
        // bottom
        if (row < this.rows-1) {
            neighbours.add(this.cells[row+1][col]);
            neighbour_coords.add(new int[]{row+1, col});
        }
        // bottom right
        if ((row < this.rows-1) && (col < this.cols-1)) {
            neighbours.add(this.cells[row+1][col+1]);
            neighbour_coords.add(new int[]{row+1, col+1});
        }

        this.cells[row][col].sweep(neighbours);
    }

    void check(int row, int col){
        List<Cell> neighbours = new ArrayList<Cell>();
        List<int[]> neighbour_coords = new ArrayList<int[]>();
        // top left
        if ((row > 0) && (col > 0)) {
            neighbours.add(this.cells[row-1][col-1]);
            neighbour_coords.add(new int[]{row-1, col-1});
        }
        // top
        if (row > 0) {
            neighbours.add(this.cells[row-1][col]);
            neighbour_coords.add(new int[]{row-1, col});
        }
        // top right
        if ((row > 0) && (col < this.cols-1)) {
            neighbours.add(this.cells[row-1][col+1]);
            neighbour_coords.add(new int[]{row-1, col+1});
        }
        // left
        if (col > 0) {
            neighbours.add(this.cells[row][col-1]);
            neighbour_coords.add(new int[]{row, col-1});
        }
        // right
        if (col < this.cols-1) {
            neighbours.add(this.cells[row][col+1]);
            neighbour_coords.add(new int[]{row, col+1});
        }
        // bottom left
        if ((row < this.rows-1) && (col > 0)) {
            neighbours.add(this.cells[row+1][col-1]);
            neighbour_coords.add(new int[]{row+1, col-1});
        }
        // bottom
        if (row < this.rows-1) {
            neighbours.add(this.cells[row+1][col]);
            neighbour_coords.add(new int[]{row+1, col});
        }
        // bottom right
        if ((row < this.rows-1) && (col < this.cols-1)) {
            neighbours.add(this.cells[row+1][col+1]);
            neighbour_coords.add(new int[]{row+1, col+1});
        }

        this.cells[row][col].check();
        if (this.cells[row][col].neighbour_mines == 0){
            for (int[] coord: neighbour_coords) {
                if (! this.cells[coord[0]][coord[1]].checked) {
                    this.check(coord[0], coord[1]);
                }
            }
        }
    }

    int flags(){
        int flags = 0;
        for (Cell[] cs: cells) {
            for (Cell c: cs) {
                if (c.flagged) {
                    flags += 1;
                    }
                }
            }
        return flags;
    }

    int unchecked_or_flagged(){
        int untouched = 0;
        for (Cell[] cs: cells) {
            for (Cell c: cs) {
                if ((!c.checked) && (!c.flagged)) {
                    untouched += 1;
                }
            }
        }
        return untouched;
    }

    void sweep_all(){
        for (int r = 0; r < this.rows; r++){
            for (int c = 0; c < this.cols; c++){
                this.sweep(r, c);
            }
        }
    }
    void check_all(){
        for (int r = 0; r < this.rows; r++){
            for (int c = 0; c < this.cols; c++){
                this.check(r, c);
            }
        }
    }
}
