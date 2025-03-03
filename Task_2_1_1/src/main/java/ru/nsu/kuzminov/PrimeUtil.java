package ru.nsu.kuzminov;

/**
 * Класс который проверяет, является ли число простым или нет.
 */
public class PrimeUtil {
    /**
     * Метод просматривающий является ли число простым или нет.
     *
     * @param num - просматриваемое число.
     * @return true если оно не простое, иначе false.
     */
    static boolean notPrime(int num) {
        if (num <= 2) {
            return true;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return true;
            }
        }
        return false;
    }
}
