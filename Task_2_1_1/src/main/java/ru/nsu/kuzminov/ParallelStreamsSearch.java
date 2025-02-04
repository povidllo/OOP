package ru.nsu.kuzminov;

import java.util.ArrayList;

/**
 * Класс реализующий последовательный поиск.
 */
public class ParallelStreamsSearch {

    /**
     * Метод просматривающий является ли число простым или нет.
     *
     * @param num - просматриваемое число.
     * @return true если оно не простое, иначе false.
     */
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

    /**
     * Основной поиск.
     *
     * @param numbers - лист с числами.
     * @return true если в numbers есть не простые числа, иначе false.
     */
    public static boolean search(ArrayList<Integer> numbers) {
        return numbers.parallelStream().anyMatch(p -> notPrime(p));
    }
}
