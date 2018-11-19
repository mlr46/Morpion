package com.mlr.morpion.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mlr.morpion.models.Mark;
import com.mlr.morpion.models.MarkValue;

public class Grid extends View {

  private Paint gridPaint;
  private Paint crossPaint;
  private Paint naughtPaint;
  private int gridSize;
  private MarkValue[][] history;

  public Grid(Context context, int gridSize, MarkValue[][] history) {
    super(context);
    this.gridSize = gridSize;
    this.history = history;

    init();
  }

  /**
   * Should build the {@code size} x {@code size} grid
   */
  private void init() {

    gridPaint = new Paint();
    gridPaint.setColor(Color.BLACK);

    crossPaint = new Paint();
    crossPaint.setColor(Color.BLUE);
    crossPaint.setStrokeWidth(3);

    naughtPaint = new Paint();
    naughtPaint.setColor(Color.GREEN);
    naughtPaint.setStrokeWidth(3);
    naughtPaint.setStyle(Paint.Style.STROKE);
  }

  /**
   * Draws the grid
   *
   * @param canvas the canvas on which the background will be drawn
   */
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int cellSize = Math.min(getHeight(), getWidth()) / gridSize;

    drawGrid(canvas, cellSize);
    drawHistory(canvas, cellSize);

  }

  private void drawGrid(Canvas canvas, int cellSize) {

    for (int i = 1; i <= gridSize; i++) {
      canvas.drawLine(0, cellSize * i, gridSize * cellSize, cellSize * i, gridPaint);
      canvas.drawLine(cellSize * i, 0, cellSize * i, gridSize * cellSize, gridPaint);
    }
  }

  private void drawHistory(Canvas canvas, int cellSize) {

    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        if (history[i][j] == MarkValue.EMPTY) {
          continue;
        }

        if (history[i][j] == MarkValue.CROSS) {
          drawCross(i, j, cellSize, canvas);
        } else {
          drawNought(i, j, cellSize, canvas);
        }
      }
    }
  }

  private void drawCross(int i, int j, int cellSize, Canvas canvas) {
    canvas.drawLine(i * cellSize, j * cellSize, (i + 1) * cellSize, (j + 1) * cellSize, crossPaint);
    canvas.drawLine((i + 1) * cellSize, j * cellSize, i * cellSize, (j + 1) * cellSize, crossPaint);
  }

  private void drawNought(int i, int j, int cellSize, Canvas canvas) {
    int radius = cellSize / 2;
    canvas.drawCircle((int)(cellSize * (i + 0.5)), (int)(cellSize * (j + 0.5)), radius, naughtPaint);
  }
}
