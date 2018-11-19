package com.mlr.morpion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.mlr.morpion.models.Point;
import com.mlr.morpion.models.Token;
import com.mlr.morpion.models.Solution;
import com.mlr.morpion.views.Grid;

import static com.mlr.morpion.models.Token.CROSS;
import static com.mlr.morpion.models.Token.EMPTY;
import static com.mlr.morpion.models.Token.NOUGHT;

public class MorpionActivity extends AppCompatActivity {

  public static final String EXTRA_GRID_SIZE = "com.mlr.morpion.activities.morpionActivity.gridSize";
  private static final int ADJACENT_FOR_WIN = 5;

  private boolean isPlayer1;
  private int gridSize;
  private Grid grid;
  private Token[][] history;
  private Toast invalidSpotToast;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    init();

    grid.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        return play(event);
      }
    });
  }

  private void init() {
    Intent intent = getIntent();
    gridSize = intent.getIntExtra(EXTRA_GRID_SIZE, 20);

    history = initializeGrid(gridSize);
    isPlayer1 = true;
    invalidSpotToast = Toast.makeText(this, "Try another spot...", Toast.LENGTH_SHORT);

    grid = new Grid(this, gridSize, history);
    setContentView(grid);
  }


  /**
   * Places thew new mark on the board
   * @param event MotionEvent where the mark was placed
   */
  public boolean play(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_UP) {
      int pointX = (int) event.getX() / grid.getCellSize();
      int pointY = (int) event.getY() / grid.getCellSize();
      if (grid.isOnBoard(pointX, pointY)) {
        if (placeMark(pointX, pointY)) {
          if (!isGameOver(pointX, pointY)) {
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
      invalidSpotToast.show();
      return false;
    }

    invalidSpotToast.cancel();

    Token token = getMarkValue();
    history[pointX][pointY] = token;
    return true;
  }

  /**
   * We let the first player always start with crosses.
   * @return
   */
  private Token getMarkValue() {
    return isPlayer1 ? CROSS : NOUGHT;
  }

  private Token[][] initializeGrid(int gridSize) {
    Token[][] history = new Token[gridSize][gridSize];
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

  private boolean isGameOver(int pointX, int pointY) {

    // add the mark to the grid
    // Analyze the grid to see if latest player has won
    int[][] directions = {
      {1, 0}, // horizontal
      {0, 1}, // vertical
      {1, 1}, // diagonal \
      {1, -1}, // diagonal /
    };

    for (int[] direction : directions) {
      int adjacentAfter = countAdjacent(pointX, pointY, direction[0], direction[1]);
      int adjacentBefore = countAdjacent(pointX, pointY, -direction[0], -direction[1]);
      int totalAdjacentMarks = adjacentBefore + 1 + adjacentAfter;
      if (totalAdjacentMarks >= ADJACENT_FOR_WIN) {

        Solution solution = new Solution(
          pointX - adjacentBefore * direction[0],
          pointX + adjacentAfter * direction[0],
          pointY - adjacentBefore * direction[1],
          pointY + adjacentAfter * direction[1]);
        grid.setSolution(solution);
        grid.setOnTouchListener(null);

        return true;
      }
    }

    return false;
  }

  private int countAdjacent(int pointX, int pointY, int deltaX, int deltaY) {

    Token mark = history[pointX][pointY];

    int nextX = pointX + deltaX;
    int nextY = pointY + deltaY;
    int adjacents = 0;
    while (nextX >= 0 && nextX < gridSize && nextY >= 0 && nextY < gridSize) {

      if (history[nextX][nextY] == mark) {
        adjacents++;
        nextX += deltaX;
        nextY += deltaY;
      } else {
        break;
      }
    }

    return adjacents;
  }
}
