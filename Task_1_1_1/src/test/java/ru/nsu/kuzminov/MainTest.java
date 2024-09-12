package ru.nsu.kuzminov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void test1() {
        int[] testArr = {5, 4, 3, 2, 1};
        int[] expArr = {1, 2, 3, 4, 5};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void test2() {
        int[] testArr = new int[]{1, 2, 3, 4, 5};
        int[] expArr = new int[]{1, 2, 3, 4, 5};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void test3() {
        int[] testArr = new int[]{};
        int[] expArr = new int[]{};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void test4() {
        int[] testArr = new int[]{1};
        int[] expArr = new int[]{1};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void test5() {
        int[] testArr = new int[]{1, 1, 1, 1, 1, 1, 1};
        int[] expArr = new int[]{1, 1, 1, 1, 1, 1, 1};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void test6() {
        int[] testArr = new int[]{1, 1, 1, 1, 2, 2, 2, 2};
        int[] expArr = new int[]{1, 1, 1, 1, 2, 2, 2, 2};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void test7() {
        int[] testArr = new int[]{2, 2, 2, 2, 1, 1, 1, 1};
        int[] expArr = new int[]{1, 1, 1, 1, 2, 2, 2, 2};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void test8() {
        int[] testArr = new int[]{-1, -2, -3, -4, -5};
        int[] expArr = new int[]{-5, -4, -3, -2, -1};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void test9() {
        int[] testArr = new int[]{-5, -4, -3, -2, -1};
        int[] expArr = new int[]{-5, -4, -3, -2, -1};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void test10() {
        int[] testArr = new int[]{5, 7, 2, 1, 2, 4, 5};
        int[] expArr = new int[]{1, 2, 2, 4, 5, 5, 7};
        Main.heapSort(testArr);
        assertArrayEquals(expArr, testArr);

    }
}