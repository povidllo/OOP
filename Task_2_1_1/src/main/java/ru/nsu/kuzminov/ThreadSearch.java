package ru.nsu.kuzminov;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

class OneThread implements Runnable {

    private ArrayList<Integer> numbers;
    private int left;
    private int right;
    private AtomicBoolean res;

    public OneThread(ArrayList<Integer> numbers, int left, int right, AtomicBoolean res) {
        this.numbers = numbers;
        this.left = left;
        this.right = right;
        this.res = res;
    }

    private boolean notPrime(int num) {
        if (num < 2) return true;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return true;
        }
        return false;
    }

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

public class ThreadSearch {

    public static boolean search(ArrayList<Integer> numbers, int num_threads) {
        int len = numbers.size();
        if (num_threads > len) {
            num_threads = len;
        }

        Thread[] threads = new Thread[num_threads];
        int count = len / num_threads;
        AtomicBoolean res = new AtomicBoolean(false);

        for (int i = 0; i < num_threads; i++) {
            int left = i * count;
            int right;
            if(i == num_threads - 1) {
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
