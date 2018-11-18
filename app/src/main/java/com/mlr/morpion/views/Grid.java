package com.mlr.morpion.views;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mlr.morpion.models.Mark;

public class Grid extends View {

  private Paint gridPaint;
  private Paint crossPaint;
  private Paint naughtPaint;
  private int size;
  private Mark[][] grid;

  public Grid(Context context) {
    super(context);
  }

  public Grid(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public Grid(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  /**
   * Should build the {@code size} x {@code size} grid
   */
  private void init() {

  }
}
