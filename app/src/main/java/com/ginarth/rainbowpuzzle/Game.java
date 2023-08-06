package com.ginarth.rainbowpuzzle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Game implements Serializable {
    public int getSize() {
        return size;
    }
    public int getTileValue(int row, int col) {
        return tiles[row][col];
    }
    public int getTimeSecs() {
        if (!this.isComplete() && startTime != 0) {
            finishTime = (System.currentTimeMillis() - startTime) / 1000;
        }

        if (finishTime > 359999) {
            finishTime = 359999;
        }

        return (int) finishTime;
    }
    public String getTimeString() {
        long time = getTimeSecs();
        int hours = (int) (time / 3600);
        int mins = (int) (time % 3600 / 60);
        int secs = (int) (time % 60);
        return String.format("%02d:%02d:%02d", hours, mins, secs);
    }
    public int getMoves() {
        if (moves > 9999) {
            return 9999;
        }
        return moves;
    }

    private final int size;
    private int[][] tiles;
    private long startTime, finishTime;
    private int moves;

    public Game(int size) {
        this.size = size;
        retry();
    }

    public void retry() {
        this.tiles = new int[size][size];
        startTime = 0;
        finishTime = 0;
        moves = 0;

        ArrayList<Integer> values = new ArrayList<Integer>();
        for (int i = size * size - 1; i > 0 ; i--) {
            values.add(i);
        }

        do {
            Collections.shuffle(values);
        } while (isComplete());

        int val;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                val = values.size() > 0 ?  values.remove(values.size() - 1) : 0;
                this.tiles[row][col] = val;
            }
        }
    }

    public boolean isComplete() {
        int counter = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                counter++;
                if (row != size - 1 || col != size - 1) {
                    if (this.tiles[row][col] != counter) {
                        return false;
                    }
                }
            }
        }

        if (getMoves() > 0)
            return true;
        return false;
    }

    public boolean move(int row, int col) {
        int row2, col2;
        if (row > 0 && this.tiles[row - 1][col] == 0) {
            row2 = row - 1;
            col2 = col;
        } else if (row < size - 1 && this.tiles[row + 1][col] == 0) {
            row2 = row + 1;
            col2 = col;
        } else if (col > 0 && this.tiles[row][col - 1] == 0) {
            row2 = row;
            col2 = col - 1;
        } else if (col < size - 1 && this.tiles[row][col + 1] == 0) {
            row2 = row;
            col2 = col + 1;
        } else {
            return false;
        }
        this.tiles[row2][col2] = this.tiles[row][col];
        this.tiles[row][col] = 0;

        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }

        moves++;
        return true;
    }
}
