package ru.nsu.kuzminov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {
    @Test
    void testHeapSort1() {
        int[] testArr = {5, 4, 3, 2, 1};
        int[] expArr = {1, 2, 3, 4, 5};
        HeapSort.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void testHeapSort2() {
        int[] testArr = new int[]{1, 2, 3, 4, 5};
        int[] expArr = new int[]{1, 2, 3, 4, 5};
        HeapSort.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void testHeapSort3() {
        int[] testArr = new int[]{};
        int[] expArr = new int[]{};
        HeapSort.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void testHeapSort4() {
        int[] testArr = new int[]{1};
        int[] expArr = new int[]{1};
        HeapSort.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void testHeapSort5() {
        int[] testArr = new int[]{1, 1, 1, 1, 1, 1, 1};
        int[] expArr = new int[]{1, 1, 1, 1, 1, 1, 1};
        HeapSort.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void testHeapSort6() {
        int[] testArr = new int[]{1, 1, 1, 1, 2, 2, 2, 2};
        int[] expArr = new int[]{1, 1, 1, 1, 2, 2, 2, 2};
        HeapSort.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void testHeapSort7() {
        int[] testArr = new int[]{2, 2, 2, 2, 1, 1, 1, 1};
        int[] expArr = new int[]{1, 1, 1, 1, 2, 2, 2, 2};
        HeapSort.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void testHeapSort8() {
        int[] testArr = new int[]{-1, -2, -3, -4, -5};
        int[] expArr = new int[]{-5, -4, -3, -2, -1};
        HeapSort.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void testHeapSort9() {
        int[] testArr = new int[]{-5, -4, -3, -2, -1};
        int[] expArr = new int[]{-5, -4, -3, -2, -1};
        HeapSort.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void testHeapSort10() {
        int[] testArr = new int[]{5, 7, 2, 1, 2, 4, 5};
        int[] expArr = new int[]{1, 2, 2, 4, 5, 5, 7};
        HeapSort.heapSort(testArr);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void testHeapify1() {
        int[] testArr = {1, 2, 3, 4, 5, 6, 7};
        int[] expArr = {3, 2, 7, 4, 5, 6, 1};
        HeapSort.heapify(testArr, 0, testArr.length);
        assertArrayEquals(expArr, testArr);
    }

    @Test
    void testHeapify2() {
        int[] testArr = new int[]{1, 2, 3, 4, 5, 6, 7};
        int[] expArr = new int[]{1, 2, 7, 4, 5, 6, 3};
        HeapSort.heapify(testArr, 2, testArr.length);
        assertArrayEquals(expArr, testArr);
    }
}
