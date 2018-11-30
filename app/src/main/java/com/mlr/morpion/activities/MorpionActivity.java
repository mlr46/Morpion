package com.mlr.morpion.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mlr.morpion.R;
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
  private static final int[][] DIRECTIONS = {
    {1, 0}, // horizontal
    {0, 1}, // vertical
    {1, 1}, // diagonal \
    {1, -1}, // diagonal /
  };

  private boolean isPlayer1;
  private int gridSize;
  private Grid grid;
  private Token[][] history;
  private Toast invalidSpotToast;
  private Token winningToken;
  private TextView textView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.morpion);
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

    grid = findViewById(R.id.grid);
    grid.setGridSize(gridSize);
    grid.setHistory(history);
    grid.setReadyToDraw();

    textView = findViewById(R.id.next_player);

    setNextPlayer();
  }

  private void setNextPlayer() {

    if (isPlayer1) {
      textView.setText(R.string.crosses_player);
    } else {
      textView.setText(R.string.naught_player);
    }
  }


  /**
   * Places thew new mark on the board
   * @param event MotionEvent where the mark was placed
   */
  public boolean play(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_UP) {
      Point point = Point.newBuilder()
        .setX((int) event.getX() / grid.getCellSize())
        .setY((int) event.getY() / grid.getCellSize())
        .build();
      if (grid.isOnBoard(point)) {
        if (placeMark(point)) {
          if (!isGameOver(point)) {
            togglePlayer();
          }
        }
      }
    }

    grid.postInvalidate();
    return true;
  }

  private boolean placeMark(Point point) {

    if (history[point.getX()][point.getY()] != EMPTY) {
      invalidSpotToast.show();
      return false;
    }

    invalidSpotToast.cancel();

    Token token = getMarkValue();
    history[point.getX()][point.getY()] = token;
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
    setNextPlayer();
  }

  /**
   * Checks whether the given point creates a line of more that {@code ADJACENT_FOR_WIN} tokens in
   * all 4 possible directions (horizontal, vertical, both diagonals) by checking how many similar
   * tokens exist in the given direction before and after the point.
   * @param point
   * @return
   */
  private boolean isGameOver(Point point) {

    for (int[] direction : DIRECTIONS) {
      int adjacentAfter = countAdjacent(point, direction[0], direction[1]);
      int adjacentBefore = countAdjacent(point, -direction[0], -direction[1]);
      int totalAdjacentMarks = adjacentBefore + 1 + adjacentAfter;
      if (totalAdjacentMarks >= ADJACENT_FOR_WIN) {

        Solution solution = Solution.newBuilder()
          .setStartX(point.getX() - adjacentBefore * direction[0])
          .setEndX(point.getX() + adjacentAfter * direction[0])
          .setStartY(point.getY() - adjacentBefore * direction[1])
          .setEndY(point.getY() + adjacentAfter * direction[1])
          .build();
        grid.setSolution(solution);
        setWinningToken(history[point.getX()][point.getY()]);
        announceWinner();
        grid.setOnTouchListener(null);

        return true;
      }
    }

    return false;
  }

  private void setWinningToken(Token winningToken) {
    this.winningToken = winningToken;
  }

  private void announceWinner() {
    AlertDialog alertDialog = new AlertDialog.Builder(this)
      .setMessage(String.format("%s wins!", winningToken.getTokenName()))
      .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          finish();
        }
      })
      .create();
    alertDialog.show();
  }

  private int countAdjacent(Point point, int deltaX, int deltaY) {

    Token mark = history[point.getX()][point.getY()];

    int nextX = point.getX() + deltaX;
    int nextY = point.getY() + deltaY;
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
