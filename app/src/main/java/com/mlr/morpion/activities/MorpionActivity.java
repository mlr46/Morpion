package com.mlr.morpion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mlr.morpion.models.Mark;
import com.mlr.morpion.models.MarkValue;
import com.mlr.morpion.views.Grid;

import static com.mlr.morpion.models.MarkValue.CROSS;
import static com.mlr.morpion.models.MarkValue.EMPTY;
import static com.mlr.morpion.models.MarkValue.NOUGHT;

public class MorpionActivity extends AppCompatActivity {

  public static final String EXTRA_GRID_SIZE = "com.mlr.morpion.activities.morpionActivity.gridSize";

  private Grid[][] grid;
  private boolean isPlayer1;
  private int gridSize;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    gridSize = intent.getIntExtra(EXTRA_GRID_SIZE, 20);

    MarkValue[][] history = getEmptyHistory();
    Grid grid = new Grid(this, gridSize, history);
    setContentView(grid);
  }


  private MarkValue[][] getEmptyHistory() {

    MarkValue[][] history = new MarkValue[gridSize][gridSize];
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        history[i][j] = EMPTY;
      }
    }

    return history;
  }


  private void addMark(int i, int j, boolean isPlayer1) {
    MarkValue value = isPlayer1 ? CROSS : NOUGHT;
    Mark mark = new Mark(i, j, value);
  }

  private boolean isGameOver(Mark latestMark) {

    // add the mark to the grid
    // Analyze the grid to see if latest player has won
    return false;
  }
}
