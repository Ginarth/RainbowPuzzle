package com.ginarth.rainbowpuzzle;

public class Coordinates
{
    private final float startX, startY, finishX, finishY;

    public Coordinates(float startX, float startY, float finishX, float finishY) {
        this.startX = startX;
        this.startY = startY;
        this.finishX = finishX;
        this.finishY = finishY;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getFinishX() {
        return finishX;
    }

    public float getFinishY() {
        return finishY;
    }

    public float getWidth() {
        return finishX - startX;
    }

    public float getHeight() {
        return finishY - startY;
    }
}
