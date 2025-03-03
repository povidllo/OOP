package ru.nsu.kuzminov;

import java.util.ArrayList;

//import static ru.nsu.kuzminov.notPrimeClass.notPrime;

/**
 * Класс реализующий последовательный поиск.
 */
public class ConsistenceSearch {
    /**
     * Основной поиск.
     *
     * @param numbers - лист с числами.
     * @return true если в numbers есть не простые числа, иначе false.
     */
    public static boolean search(ArrayList<Integer> numbers) {
        for (int num : numbers) {
            if (PrimeUtil.notPrime(num)) {
                return true;
            }
        }
        return false;
    }
}