package com.ginarth.rainbowpuzzle;

public class Record {

    private String name, date;
    private int moves, time;

    public Record(String name, String date, int moves, int time) {
        this.name = name;
        this.date = date;
        this.moves = moves;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getMoves() {
        return moves;
    }

    public int getTime() {
        return time;
    }
}
