package ru.nsu.kuzminov;

import java.util.ArrayList;

/**
 * Класс реализующий последовательный поиск.
 */
public class ParallelStreamsSearch {

    /**
     * Основной поиск.
     *
     * @param numbers - лист с числами.
     * @return true если в numbers есть не простые числа, иначе false.
     */
    public static boolean search(ArrayList<Integer> numbers) {
        return numbers.parallelStream().anyMatch(NotPrime::notPrime);
    }
}
