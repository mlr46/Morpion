package com.mlr.morpion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.mlr.morpion.models.Mark;
import com.mlr.morpion.models.MarkValue;
import com.mlr.morpion.views.Grid;

import static com.mlr.morpion.models.MarkValue.CROSS;
import static com.mlr.morpion.models.MarkValue.EMPTY;
import static com.mlr.morpion.models.MarkValue.NOUGHT;

public class MorpionActivity extends AppCompatActivity {

  public static final String EXTRA_GRID_SIZE = "com.mlr.morpion.activities.morpionActivity.gridSize";

  private boolean isPlayer1;
  private int gridSize;
  private Grid grid;
  private MarkValue[][] history;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    gridSize = intent.getIntExtra(EXTRA_GRID_SIZE, 20);
    history = getEmptyHistory(gridSize);
    isPlayer1 = true;

    grid = new Grid(this, gridSize, history);
    setContentView(grid);

    grid.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        return play(v, event);
      }
    });
  }


  public boolean play(View view, MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_UP) {
      int pointX = (int) event.getX() / grid.getCellSize();
      int pointY = (int) event.getY() / grid.getCellSize();
      if (grid.isOnBoard(pointX, pointY)) {
        if (placeMark(pointX, pointY)) {
          if (!isGameOver()) {
            togglePlayer();
          }
        }
      }
    }

    grid.postInvalidate();
    return true;
  }

  private boolean placeMark(int pointX, int pointY) {

    if (history[pointX][pointY] != EMPTY) {
      // Place a toast here
      Toast tryAgain = Toast.makeText(this, "Try another spot...", Toast.LENGTH_SHORT);
      tryAgain.show();
      return false;
    }

    MarkValue markValue = getMarkValue();
    history[pointX][pointY] = markValue;
    return true;
  }

  /**
   * We let the first player always start with crosses.
   * @return
   */
  private MarkValue getMarkValue() {
    return isPlayer1 ? CROSS : NOUGHT;
  }

  private MarkValue[][] getEmptyHistory(int gridSize) {

    MarkValue[][] history = new MarkValue[gridSize][gridSize];
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        history[i][j] = EMPTY;
      }
    }

    return history;
  }

  private void togglePlayer() {
    isPlayer1 = !isPlayer1;
  }

  private boolean isGameOver() {

    // add the mark to the grid
    // Analyze the grid to see if latest player has won
    return false;
  }
}
