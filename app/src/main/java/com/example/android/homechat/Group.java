package com.example.android.homechat;

import android.location.Location;

public class Group {
    public float getN() {
        return N;
    }

    public void setN(float n) {
        N = n;
    }

    private float N;

    public float getE() {
        return E;
    }

    public void setE(float e) {
        E = e;
    }

    private float E;
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
