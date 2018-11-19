package com.mlr.morpion.models;

public class Point {

  private int x;
  private int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private int x;
    private int y;

    public Builder setX(int x) {
      this.x = x;
      return this;
    }

    public Builder setY(int y) {
      this.y = y;
      return this;
    }

    public Point build() {
      return new Point(x, y);
    }
  }
}
