package ru.nsu.kuzminov;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < 100000000; i++) {
            numbers.add(5683);
        }
        double time = 1_000_000.0;
//        numbers.add(10000, 4);
        long t1, t2;
        double timeInSeconds;
        t1 = System.nanoTime();
        ConsistenceSearch.search(numbers);
        t2 = System.nanoTime();
        timeInSeconds = (t2 - t1) / time;
        System.out.print(timeInSeconds + " ");

        t1 = System.nanoTime();
        ThreadSearch.search(numbers, 1);
        t2 = System.nanoTime();
        timeInSeconds = (t2 - t1) / time;
        System.out.print(timeInSeconds + " ");

        t1 = System.nanoTime();
        ThreadSearch.search(numbers, 3);
        t2 = System.nanoTime();
        timeInSeconds = (t2 - t1) / time;
        System.out.print(timeInSeconds + " ");

        t1 = System.nanoTime();
        ThreadSearch.search(numbers, 5);
        t2 = System.nanoTime();
        timeInSeconds = (t2 - t1) / time;
        System.out.print(timeInSeconds + " ");

        t1 = System.nanoTime();
        ThreadSearch.search(numbers, 7);
        t2 = System.nanoTime();
        timeInSeconds = (t2 - t1) / time;
        System.out.print(timeInSeconds + " ");

        t1 = System.nanoTime();
        ThreadSearch.search(numbers, 9);
        t2 = System.nanoTime();
        timeInSeconds = (t2 - t1) / time;
        System.out.print(timeInSeconds + " ");

        t1 = System.nanoTime();
        ThreadSearch.search(numbers, 11);
        t2 = System.nanoTime();
        timeInSeconds = (t2 - t1) / time;
        System.out.print(timeInSeconds + " ");

        t1 = System.nanoTime();
        ThreadSearch.search(numbers, 32);
        t2 = System.nanoTime();
        timeInSeconds = (t2 - t1) / time;
        System.out.print(timeInSeconds + " ");

        t1 = System.nanoTime();
        ThreadSearch.search(numbers, 64);
        t2 = System.nanoTime();
        timeInSeconds = (t2 - t1) / time;
        System.out.print(timeInSeconds + " ");

        t1 = System.nanoTime();
        ThreadSearch.search(numbers, 128);
        t2 = System.nanoTime();
        timeInSeconds = (t2 - t1) / time;
        System.out.print(timeInSeconds + " ");

        t1 = System.nanoTime();
        ParallelStreamsSearch.search(numbers);
        t2 = System.nanoTime();
        timeInSeconds = (t2 - t1) / time;
        System.out.print(timeInSeconds + " ");
    }
}
