package com.ewyboy.worldspawn.json.objects;

public class Spawn {

    private int min_y;
    private int max_y;

    public Spawn(int min_y, int max_y) {
        this.min_y = min_y;
        this.max_y = max_y;
    }

    public int getMin_y() {
        return min_y;
    }

    public void setMin_y(int min_y) {
        this.min_y = min_y;
    }

    public int getMax_y() {
        return max_y;
    }

    public void setMax_y(int max_y) {
        this.max_y = max_y;
    }
}
