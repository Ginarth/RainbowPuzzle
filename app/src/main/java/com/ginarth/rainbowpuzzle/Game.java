package com.ginarth.rainbowpuzzle;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Game implements Serializable {
    private final int size;
    private long startTime;
    private int moves;
    private long finishTime;
    private boolean isComplete;
    private int[][] tiles;

    public int getSize() {
        return size;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public long getFinishTime() {
        if (startTime == 0) {
            return 0;
        }
        if (!this.getIsComplete()) {
            this.finishTime = (System.currentTimeMillis() - startTime) / 1000;
        }
        if (finishTime > 359999) {
            return 359999;
        }
        return finishTime;
    }
    public int getMoves() {
        if (moves > 9999) {
            return 9999;
        }
        return moves;
    }
    public int getTileValue(int row, int col) {
        return tiles[row][col];
    }

    public Game(int size) {
        this.size = size;
        this.tiles = new int[size][size];
        moves = 0;
        startTime = 0;
        isComplete = false;

        ArrayList<Integer> values = new ArrayList<Integer>();
        for (int i = size * size - 1; i > 0 ; i--) {
            values.add(i);
        }
        //Collections.shuffle(values);

        int val;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                val = values.size() > 0 ?  values.remove(values.size() - 1) : 0;
                this.tiles[row][col] = val;
            }
        }
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
        isComplete = isComplete();
        return true;
    }

    public void retry() {
        startTime = 0;
        moves = 0;
        isComplete = false;
        ArrayList<Integer> values = new ArrayList<Integer>();
        for (int i = size * size - 1; i > 0 ; i--) {
            values.add(i);
        }
        Collections.shuffle(values);

        int val;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                val = values.size() > 0 ?  values.remove(values.size() - 1) : 0;
                this.tiles[row][col] = val;
            }
        }
    }

    public boolean isComplete() {
        Log.d("TerminatorProverator", "check");
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
        return true;
    }
}
