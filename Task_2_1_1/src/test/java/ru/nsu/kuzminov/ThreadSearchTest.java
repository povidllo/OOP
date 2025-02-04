package ru.nsu.kuzminov;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThreadSearchTest {
    static ArrayList<Integer> array1;
    static ArrayList<Integer> array2;
    @BeforeAll
    static void prepare(){
        array1 = new ArrayList<>(List.of(6, 8, 7, 13, 5, 9, 4));
        array2 = new ArrayList<>(List.of(20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053));
    }

    @Test
    void search1() {
        assertTrue(ThreadSearch.search(array1, 1));
    }

    @Test
    void search2() {
        assertTrue(ThreadSearch.search(array1, 5));
    }

    @Test
    void search3() {
        assertTrue(ThreadSearch.search(array1, 10));
    }

    @Test
    void search4() {
        assertFalse(ThreadSearch.search(array2, 5));
    }

}