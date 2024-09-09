package ru.nsu.kuzminov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void test() {
        int[] testArr = {5, 4, 3, 2, 1};
        int[] expArr = {1, 2, 3, 4, 5};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);

        testArr = new int[]{1, 2, 3, 4, 5};
        expArr = new int[]{1, 2, 3, 4, 5};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);

        testArr = new int[]{};
        expArr = new int[]{};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);

        testArr = new int[]{1};
        expArr = new int[]{1};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);

        testArr = new int[]{1, 1, 1, 1, 1, 1, 1};
        expArr = new int[]{1, 1, 1, 1, 1, 1, 1};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);

        testArr = new int[]{1, 1, 1, 1, 2, 2, 2, 2};
        expArr = new int[]{1, 1, 1, 1, 2, 2, 2, 2};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);

        testArr = new int[]{2, 2, 2, 2, 1, 1, 1, 1};
        expArr = new int[]{1, 1, 1, 1, 2, 2, 2, 2};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);

        testArr = new int[]{-1, -2, -3, -4, -5};
        expArr = new int[]{-5, -4, -3, -2, -1};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);

        testArr = new int[]{-5, -4, -3, -2, -1};
        expArr = new int[]{-5, -4, -3, -2, -1};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);

        testArr = new int[]{5, 7, 2, 1, 2, 4, 5};
        expArr = new int[]{1, 2, 2, 4, 5, 5, 7};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);

    }
}