package com.example.seneda.minesweeper;

import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by seneda on 08/10/16.
 */

public class MineField
{
    Cell [][] cells;

    int rows;

    int cols;

    MineField(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[this.rows][this.cols];
        for (int c = 0; c < this.cols; c++){
            for (int r = 0; r < this.rows; r++){
                this.cells[r][c] = new Cell(Math.random() > 0.9);
            }
        }
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
        }

        this.cells[row][col].sweep(neighbours);
        if (this.cells[row][col].neighbour_mines == 0){
            for (int[] coord: neighbour_coords) {
                if (! this.cells[coord[0]][coord[1]].checked) {
                    this.sweep(coord[0], coord[1]);
                }
            }
        }
    }

    void sweep_all(){
        for (int r = 0; r < this.rows; r++){
            for (int c = 0; c < this.cols; c++){
                Log.d("sweeping"+r+" "+c, this.to_text());
                this.sweep(r, c);
            }
        }
    }
}
