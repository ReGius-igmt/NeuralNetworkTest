package ru.regiuss;

import java.util.Random;

public class Matrix {
    double[][] v;
    public int n, m;

    public Matrix(int n, int m, Random random) {
        this.n = n;
        this.m = m;

        v = new double[n][];

        for (int i = 0; i < n; i++) {
            v[i] = new double[m];

            for (int j = 0; j < m; j++)
                v[i][j] = random.nextDouble() - 0.5;
        }
    }

    public double get(int i, int j) {
        return v[i][j];
    }

    public void set(int i, int j, double value) {
        v[i][j] = value;
    }
}
