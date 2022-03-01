package ru.regiuss;

import java.util.Random;

public class Network {

    Matrix[] weights;
    LayerT[] L;
    Vector[] deltas;
    int layersN;

    public Network(int[] sizes) {
        Random random = new Random(System.currentTimeMillis());

        layersN = sizes.length - 1;
        L = new LayerT[layersN];
        weights = new Matrix[layersN];
        deltas = new Vector[layersN];

        for (int k = 1; k < sizes.length; k++) {
            weights[k - 1] = new Matrix(sizes[k], sizes[k - 1], random);
            L[k - 1] = new LayerT();
            L[k - 1].x = new Vector(sizes[k - 1]);
            L[k - 1].z = new Vector(sizes[k]);
            L[k - 1].df = new Vector(sizes[k]);
            deltas[k - 1] = new Vector(sizes[k]);
        }
    }

    public Vector forward(Vector input) {
        for (int k = 0; k < layersN; k++) {
            if (k == 0) {
                for (int i = 0; i < input.n; i++)
                    L[k].x.set(i, input.get(i));
            }
            else {
                for (int i = 0; i < L[k - 1].z.n; i++)
                    L[k].x.set(i, L[k - 1].z.get(i));
            }

            for (int i = 0; i < weights[k].n; i++) {
                double y = 0;

                for (int j = 0; j < weights[k].m; j++)
                    y += weights[k].get(i, j) * L[k].x.get(j);

                L[k].z.set(i, 1 / (1 + Math.exp(-y)));;
                L[k].df.set(i, L[k].z.get(i) * (1 - L[k].z.get(i)));
            }
        }
        return L[layersN - 1].z;
    }


    double backward(Vector output, double error) {
        int last = layersN - 1;

        error = 0D;

        for (int i = 0; i < output.n; i++) {
            double e = L[last].z.get(i) - output.get(i);

            deltas[last].set(i, e * L[last].df.get(i));
            error += e * e / 2;
        }

        for (int k = last; k > 0; k--) {
            for (int i = 0; i < weights[k].m; i++) {
                deltas[k - 1].set(i, 0);

                for (int j = 0; j < weights[k].n; j++)
                    deltas[k - 1].set(i, deltas[k - 1].get(i) + weights[k].get(j, i) * deltas[k].get(j));

                deltas[k - 1].set(i, deltas[k - 1].get(i) * L[k - 1].df.get(i));
            }
        }
        return error;
    }

    public void updateWeights(double alpha) {
        for (int k = 0; k < layersN; k++) {
            for (int i = 0; i < weights[k].n; i++) {
                for (int j = 0; j < weights[k].m; j++) {
                    weights[k].set(i, j, weights[k].get(i, j) - alpha * deltas[k].get(i) * L[k].x.get(j));
                }
            }
        }
    }

    public void train(Vector[] X, Vector[] Y, double alpha, double eps, int epochs) {
        int epoch = 1;

        double error;

        do {
            error = 0D;

            for (int i = 0; i < X.length; i++) {
                forward(X[i]);
                error = backward(Y[i], error);
                updateWeights(alpha);
            }

            System.out.printf("epoch: %s, error: %s%n", epoch, error);

            epoch++;
        } while (epoch <= epochs && error > eps);
    }
}
