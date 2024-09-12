package ru.nsu.kuzminov;


public class Main {

    /**
     * Sort array using heapSort
     * @param arr is an integer array
     */
    static void heapSort(int[] arr) {
        int size = arr.length;
        for (int i = size / 2 - 1; i >= 0; i--)
            heapify(arr, i, size);
        for (int i = size - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, 0, i);
        }
    }

    /**
     * heapify checks if the subtree with root i is a heap
     * @param arr is the integer the array
     * @param i is the root of the subtree
     * @param size is the size of the subtree
     */
    public static void heapify(int[] arr, int i, int size) {
        int l = i * 2 + 1;
        int r = i * 2 + 2;
        int max = i;

        if (l < size && arr[max] < arr[l]) {
            max = l;
        }
        if (r < size && arr[max] < arr[r]) {
            max = r;
        }

        if (max != i) {
            int temp = arr[i];
            arr[i] = arr[max];
            arr[max] = temp;

            heapify(arr, max, size);
        }
    }

    public static void main(String[] args) {
        int[] testArr = {1,2,3,4,5,6, 7};
        Main.heapify(testArr, 2, testArr.length);
        for(int i:testArr)
        {
            System.out.println(i);
        }
    }
}