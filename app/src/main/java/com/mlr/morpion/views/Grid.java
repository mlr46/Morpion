package com.mlr.morpion.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mlr.morpion.models.Point;
import com.mlr.morpion.models.Token;
import com.mlr.morpion.models.Solution;

public class Grid extends View {

  private Paint gridPaint;
  private Paint crossPaint;
  private Paint naughtPaint;
  private Paint winPaint;
  private int gridSize;
  private Token[][] history;
  private int cellSize;
  private Solution solution;
  private boolean isReadyToDraw;

  public Grid(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
    init();
  }

  public void setGridSize(int gridSize) {
    this.gridSize = gridSize;
  }

  public void setHistory(Token[][] history) {
    this.history = history;
  }

  public void setReadyToDraw() {
    this.isReadyToDraw = true;
  }

  /**
   * Should build the {@code size} x {@code size} grid
   */
  private void init() {

    gridPaint = new Paint();
    gridPaint.setColor(Color.BLACK);


    crossPaint = new Paint();
    crossPaint.setColor(Color.BLUE);
    crossPaint.setStrokeWidth(8);

    naughtPaint = new Paint();
    naughtPaint.setColor(Color.GREEN);
    naughtPaint.setStrokeWidth(8);
    naughtPaint.setStyle(Paint.Style.STROKE);

    winPaint = new Paint();
    winPaint.setColor(Color.RED);
    winPaint.setStrokeWidth(20);

  }

  /**
   * Draws the grid
   *
   * @param canvas the canvas on which the background will be drawn
   */
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    if (isReadyToDraw) {

    this.cellSize = Math.min(getMeasuredHeight(), getMeasuredWidth()) / gridSize;

      drawGrid(canvas);
      drawHistory(canvas);
      drawSolution(canvas);
    }
  }

  /**
   * Call this view's OnClickListener, if it is defined.  Performs all normal
   * actions associated with clicking: reporting accessibility event, playing
   * a sound, etc.
   *
   * @return True there was an assigned OnClickListener that was called, false
   * otherwise is returned.
   */
  @Override
  public boolean performClick() {
    return super.performClick();
  }

  public int getCellSize() {
    return cellSize;
  }

  public void setSolution(Solution solution) {
    this.solution = solution;
  }

  public boolean isOnBoard(Point point) {
    return point.getX() >= 0 && point.getX() < gridSize &&
      point.getY() >= 0 && point.getY() < gridSize;
  }

  private void drawGrid(Canvas canvas) {
    for (int i = 0; i <= gridSize; i++) {
      canvas.drawLine(0, cellSize * i, gridSize * cellSize, cellSize * i, gridPaint);
      canvas.drawLine(cellSize * i, 0, cellSize * i, gridSize * cellSize, gridPaint);
    }
  }

  /**
   * We know the solution goes from (solution.startX, solution.startY) until (solution.endX, solution.endY).
   * We want to draw the line that connect the center of both those cells.
   * @param canvas
   */
  private void drawSolution(Canvas canvas) {
    if (solution != null) {
      int centerStartX = (int)(cellSize * (solution.getStartX() + 0.5));
      int centerStartY = (int)(cellSize * (solution.getStartY() + 0.5));
      int centerEndX = (int)(cellSize * (solution.getEndX() + 0.5));
      int centerEndY = (int)(cellSize * (solution.getEndY() + 0.5));

      canvas.drawLine(centerStartX, centerStartY, centerEndX, centerEndY, winPaint);
    }
  }

  private void drawHistory(Canvas canvas) {

    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        if (history[i][j] == Token.EMPTY) {
          continue;
        }

        if (history[i][j] == Token.CROSS) {
          drawCross(i, j, canvas);
        } else {
          drawNought(i, j, canvas);
        }
      }
    }
  }


  private void drawCross(int i, int j, Canvas canvas) {
    canvas.drawLine(i * cellSize, j * cellSize, (i + 1) * cellSize, (j + 1) * cellSize, crossPaint);
    canvas.drawLine((i + 1) * cellSize, j * cellSize, i * cellSize, (j + 1) * cellSize, crossPaint);
  }

  private void drawNought(int i, int j, Canvas canvas) {
    int radius = cellSize / 2;
    canvas.drawCircle((int)(cellSize * (i + 0.5)), (int)(cellSize * (j + 0.5)), radius, naughtPaint);
  }
}
