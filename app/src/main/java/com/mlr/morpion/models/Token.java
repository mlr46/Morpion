package com.mlr.morpion.models;

public enum Token {

  CROSS("Crosses"),
  NOUGHT("Noughts"),
  EMPTY("");

  private String tokenName;

  Token(String tokenName) {
    this.tokenName = tokenName;
  }

  public String getTokenName() {
    return tokenName;
  }
}
