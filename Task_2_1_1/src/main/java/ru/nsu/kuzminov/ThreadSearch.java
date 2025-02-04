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
     * Метод просматривающий является ли число простым или нет.
     *
     * @param num - просматриваемое число.
     * @return true если оно не простое, иначе false.
     */
    private boolean notPrime(int num) {
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

    /**
     * Запуск.
     */
    @Override
    public void run() {
        for (int i = left; i < right; i++) {
            if (res.get()) return;
            if (notPrime(numbers.get(i))) {
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
