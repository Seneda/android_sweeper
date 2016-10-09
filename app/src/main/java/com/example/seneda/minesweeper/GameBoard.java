package com.example.seneda.minesweeper;

import android.content.res.ColorStateList;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameBoard extends AppCompatActivity {

    MineField mineField;
    Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reset();
        }

    public void reset(){
        setContentView(R.layout.activity_game_board);
        this.mineField = new MineField(15, 12);
        GridLayout grid = (GridLayout)findViewById(R.id.grid);

        grid.setColumnCount(this.mineField.cols);
        grid.setRowCount(this.mineField.rows);

        buttons = new Button[this.mineField.rows][this.mineField.cols];


        for (int r = 0; r < this.mineField.rows; r++) {
            for (int c = 0; c < this.mineField.cols; c++) {
                Button b = new Button(this);
                buttons[r][c] = b;
                b.setMinimumWidth(0);
                b.setWidth(63);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                b.setText(mineField.cells[r][c].to_text());
                grid.addView(b);
                final int r_ = r;
                final int c_ = c;

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("", "Clicking button "+r_+" "+c_);
                        mineField.check(r_, c_);
                        redraw();
                        if (mineField.cells[r_][c_].mine){
                            check_all();
                            end_game();
                        }

                    }});

                b.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view){
                        Log.d("", "Clicking button "+r_+" "+c_);
                        mineField.cells[r_][c_].flag();
                        redraw();
                        return true;
                        }
                    } );
            }
        }
        redraw();
    }
    public void reset(View v){
        reset();
    }

    public void end_game(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Game Over");
        alertDialog.setMessage("You Lose!");
        alertDialog.show();
    }

    public void win_game(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Game Completed");
        alertDialog.setMessage("Well Done!");
        alertDialog.show();
    }

    public void redraw(){
        Log.d("1 ", "Redrawing");
        Log.d("", this.mineField.to_text());
//        TextView t = (TextView)findViewById(R.id.board_text);
//        t.setText(this.mineField.to_text());

        for (int r = 0; r < mineField.rows; r++) {
            for (int c = 0; c < mineField.cols; c++) {
                Cell cell = mineField.cells[r][c];
                Button button = buttons[r][c];
                button.setText(cell.to_text());

                if ((cell.checked ))
                    if (cell.mine) {
                        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_red)));
                    } else if (cell.neighbour_mines == 0) {
                        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    } else if (cell.neighbour_mines == 1) {
                        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                    } else if (cell.neighbour_mines == 2) {
                        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                    } else if (cell.neighbour_mines == 3) {
                        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_orange)));
                    } else {
                        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                } else if (cell.flagged) {
                    button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
                }
            }
        }
        TextView t2 = (TextView)findViewById(R.id.no_flags);
        t2.setText(""+mineField.flags());
        TextView t1 = (TextView)findViewById(R.id.no_mines);
        t1.setText(""+mineField.mines);

        if ((mineField.unchecked_or_flagged() == 0) && (mineField.mines == mineField.flags())) {
            win_game();
        }
    }

    public void redraw(View v){
        redraw();
    }


    public void check_all(){  //}, int row, int col){
        Log.d("", "Checking all");
        Log.d("", this.mineField.to_text());
        this.mineField.check_all();
        Log.d("", this.mineField.to_text());
        redraw();
    }
}
