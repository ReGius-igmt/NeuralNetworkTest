package ru.regiuss;

import java.util.Arrays;

public class Vector {
    public double[] v;
    public int n;

    public Vector(int n) {
        this.n = n;
        v = new double[n];
    }

    public Vector(Double... values) {
        n = values.length;
        v = new double[n];

        for (int i = 0; i < values.length; i++) {
            v[i] = values[i];
        }
    }

    public Vector(Integer... values) {
        n = values.length;
        v = new double[n];

        for (int i = 0; i < values.length; i++) {
            v[i] = values[i];
        }
    }

    public double get(int i) {
        return v[i];
    }

    public void set(int i, double val) {
        v[i] = val;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "v=" + Arrays.toString(v) +
                ", n=" + n +
                '}';
    }
}
