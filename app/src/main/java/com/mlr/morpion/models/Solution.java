package com.mlr.morpion.models;

public class Solution {

  private float startX;
  private float endX;
  private float startY;
  private float endY;

  private Solution(float startX, float endX, float startY, float endY) {
    this.startX = startX;
    this.endX = endX;
    this.startY = startY;
    this.endY = endY;
  }

  public float getStartX() {
    return startX;
  }

  public float getEndX() {
    return endX;
  }

  public float getStartY() {
    return startY;
  }

  public float getEndY() {
    return endY;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private float startX;
    private float endX;
    private float startY;
    private float endY;

    public Builder setStartX(float value) {
      this.startX = value;
      return this;
    }

    public Builder setStartY(float value) {
      this.startY = value;
      return this;
    }

    public Builder setEndX(float value) {
      this.endX = value;
      return this;
    }

    public Builder setEndY(float value) {
      this.endY = value;
      return this;
    }

    public Solution build() {
      return new Solution(startX, endX, startY, endY);
    }

  }
}
