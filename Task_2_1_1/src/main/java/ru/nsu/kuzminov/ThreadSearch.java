package ru.nsu.kuzminov;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс для потока.
 */
class OneThread implements Runnable {

    private ArrayList<Integer> numbers;
    private int left;
    private int right;
    private AtomicBoolean res;

    /**
     * Конструктор.
     *
     * @param numbers - лист с числами.
     * @param left    - левая граница.
     * @param right   - правая граница.
     * @param res     - результат.
     */
    public OneThread(ArrayList<Integer> numbers, int left, int right, AtomicBoolean res) {
        this.numbers = numbers;
        this.left = left;
        this.right = right;
        this.res = res;
    }

    /**
     * Запуск.
     */
    @Override
    public void run() {
        for (int i = left; i < right; i++) {
            if (res.get()) return;
            if (notPrimeClass.notPrime(numbers.get(i))) {
                res.set(true);
                return;
            }
        }
    }
}

/**
 * Класс реализующий последовательный поиск.
 */
public class ThreadSearch {
    /**
     * Основной метод поиска.
     *
     * @param numbers     - лист с числами
     * @param numThreads - число потоков
     * @return true если в numbers есть не простое число, иначе false.
     */
    public static boolean search(ArrayList<Integer> numbers, int numThreads) {
        int len = numbers.size();
        if (numThreads > len) {
            numThreads = len;
        }

        Thread[] threads = new Thread[numThreads];
        int count = len / numThreads;
        AtomicBoolean res = new AtomicBoolean(false);

        for (int i = 0; i < numThreads; i++) {
            int left = i * count;
            int right;
            if (i == numThreads - 1) {
                right = len;
            } else {
                right = left + count;
            }
            threads[i] = new Thread(new OneThread(numbers, left, right, res));
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res.get();
    }
}
