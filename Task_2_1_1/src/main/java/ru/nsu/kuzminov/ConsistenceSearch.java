package ru.nsu.kuzminov;

import java.util.ArrayList;

public class ConsistenceSearch {
    private static boolean notPrime(int num) {
        if (num < 2) {
            return true;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean search(ArrayList<Integer> numbers) {
        for (int num : numbers) {
            if (notPrime(num)) {
                return true;
            }
        }
        return false;
    }
}