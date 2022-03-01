package ru.regiuss;

public class Main {
    public static void main(String[] args) {
        Vector[] X = {
                new Vector(0, 0),
                new Vector(0, 1),
                new Vector(1, 0),
                new Vector(1, 1)
        };

        Vector[] Y = {
                new Vector(0.0), // 0 ^ 0 = 0
                new Vector(1.0), // 0 ^ 1 = 1
                new Vector(1.0), // 1 ^ 0 = 1
                new Vector(0.0) // 1 ^ 1 = 0
        };

        Network network = new Network(new int[]{2, 3, 1});
        network.train(X, Y, 0.5, 1e-7, 100000);
        for (int i = 0; i < 4; i++) {
            Vector output = network.forward(X[i]);
            System.out.printf("X: %s %s, Y: %s, output: %s%n", X[i].get(0), X[i].get(1), Y[i].get(0), output.get(0));
        }
    }
}
