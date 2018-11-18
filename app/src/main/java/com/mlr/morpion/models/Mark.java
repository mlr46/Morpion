package com.mlr.morpion.models;

public class Mark {

    private final int x;
    private final int y;
    private MarkValue value;

    public Mark(int x, int y, MarkValue value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public MarkValue getValue() {
        return value;
    }

    public void setValue(MarkValue value) {
        this.value = value;
    }
}
