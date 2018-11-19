package com.mlr.morpion.models;

public class Solution {

  private float startX;
  private float endX;
  private float startY;
  private float endY;

  public Solution(float startX, float endX, float startY, float endY) {
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
}
