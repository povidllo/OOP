package ru.nsu.kuzminov;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParallelStreamsSearchTest {
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
        assertTrue(ParallelStreamsSearch.search(array1));
    }

    @Test
    void search2() {
        assertFalse(ParallelStreamsSearch.search(array2));
    }

}