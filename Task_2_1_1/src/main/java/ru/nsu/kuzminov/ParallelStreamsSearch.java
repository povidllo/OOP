package ru.nsu.kuzminov;

import java.util.ArrayList;

public class ParallelStreamsSearch {

    private static boolean notPrime(int num) {
        if (num < 2) {
            return true;
        }
        for (int i = 2; i <= Math.pow(num, 0.5); i++) {
            if (num % i == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean search(ArrayList<Integer> numbers) {
        return numbers.parallelStream().anyMatch(p -> notPrime(p));
    }
}
