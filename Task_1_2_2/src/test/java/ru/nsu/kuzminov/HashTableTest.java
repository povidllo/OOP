package ru.nsu.kuzminov;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ConcurrentModificationException;

class HashTableTest {

    HashTable<Integer, String> table;

    @BeforeEach
    void before() {
        table = new HashTable<>();
    }

    @Test
    void putOneKeyOneValue() {
        table.put(1, "one");
        assertTrue(table.containsKey(1));
    }

    @Test
    void putOneKeyTwoValue() {
        table.put(1, "one");

        assertTrue(table.containsKey(1));
        String prev = table.put(1, "one one");
        assertTrue(table.containsValue("one one"));
        assertEquals(prev, "one");
        assertFalse(table.containsValue("one"));


    }

    @Test
    void putTwoKeyOneValue() {
        table.put(1, "one");
        table.put(2, "one");

        assertTrue(table.containsKey(1));
        assertTrue(table.containsKey(2));
        assertTrue(table.containsValue("one"));

    }

    @Test
    void testLoadFactorWithoutCollision() {
        table.put(1, "one");
        table.put(2, "two");
        table.put(3, "three");
        table.put(4, "four");
        table.put(5, "five");
        table.put(6, "six");
        table.put(7, "seven");

        assertTrue(table.containsKey(1));
        assertTrue(table.containsKey(2));
        assertTrue(table.containsKey(3));
        assertTrue(table.containsKey(4));
        assertTrue(table.containsKey(5));
        assertTrue(table.containsKey(6));
        assertTrue(table.containsKey(7));

        table.put(8, "eight");
        table.put(9, "nine");

        assertTrue(table.containsKey(8));
        assertTrue(table.containsKey(9));
    }

    @Test
    void testLoadFactorWithCollision() {
        table.put(1, "one");
        table.put(11, "two");
        table.put(2, "three");
        table.put(12, "four");
        table.put(3, "five");
        table.put(13, "six");
        table.put(4, "seven");

        assertTrue(table.containsKey(1));
        assertTrue(table.containsKey(2));
        assertTrue(table.containsKey(3));
        assertTrue(table.containsKey(4));
        assertTrue(table.containsKey(11));
        assertTrue(table.containsKey(12));
        assertTrue(table.containsKey(13));

        table.put(14, "eight");

        assertTrue(table.containsKey(14));
    }

    @Test
    void testRemoveWithoutCollision() {
        table.put(1, "one");
        table.put(2, "two");
        assertTrue(table.containsKey(1));
        assertTrue(table.containsKey(2));
        assertEquals(table.size(), 2);

        table.remove(2);
        assertTrue(table.containsKey(1));
        assertFalse(table.containsKey(2));
        assertEquals(table.size(), 1);
    }

    @Test
    void testRemoveWithCollisionSecond() {
        table.put(1, "one");
        table.put(11, "two");
        assertTrue(table.containsKey(1));
        assertTrue(table.containsKey(11));
        assertEquals(table.size(), 2);

        table.remove(11);
        assertTrue(table.containsKey(1));
        assertFalse(table.containsKey(11));
        assertEquals(table.size(), 1);
    }

    @Test
    void testRemoveWithCollisionFirst() {
        table.put(1, "one");
        table.put(11, "two");
        assertTrue(table.containsKey(1));
        assertTrue(table.containsKey(11));
        assertEquals(table.size(), 2);

        table.remove(1);
        assertTrue(table.containsKey(11));
        assertFalse(table.containsKey(1));
        assertEquals(table.size(), 1);
    }

    @Test
    void testRemoveWithCollisionMiddle() {
        table.put(1, "one");
        table.put(11, "two");
        table.put(111, "three");
        assertTrue(table.containsKey(1));
        assertTrue(table.containsKey(11));
        assertTrue(table.containsKey(111));
        assertEquals(table.size(), 3);

        table.remove(11);
        assertTrue(table.containsKey(1));
        assertTrue(table.containsKey(111));
        assertFalse(table.containsKey(11));
        assertEquals(table.size(), 2);
    }

    @Test
    void testRemoveException() {
        table.put(1, "one");

        assertThrows(IllegalArgumentException.class, () -> table.remove(11));

    }

    @Test
    void testGetException() {
        table.put(1, "one");

        assertThrows(IllegalArgumentException.class, () -> table.get(11));

    }

    @Test
    void testGetWithoutCollision() {
        table.put(1, "one");
        assertEquals("one", table.get(1));
    }

    @Test
    void testGetWithCollisionFirst() {
        table.put(1, "one");
        table.put(11, "two");
        table.put(111, "three");
        assertEquals("one", table.get(1));
    }

    @Test
    void testGetWithCollisionMiddle() {
        table.put(1, "one");
        table.put(11, "two");
        table.put(111, "three");
        assertEquals("one", table.get(1));
    }

    @Test
    void testGetWithCollisionLast() {
        table.put(1, "one");
        table.put(11, "two");
        table.put(111, "three");
        assertEquals("one", table.get(1));
    }

    @Test
    void testToString() {
        table.put(1, "one");
        table.put(11, "two");
        table.put(2, "three");
        assertEquals(table.toString(), "11 = two\n"
                + "1 = one\n"
                + "2 = three");
    }

    @Test
    void testConCurException() {
        table.put(1, "one");
        table.put(11, "two");
        table.put(2, "three");
        assertThrows(ConcurrentModificationException.class, () -> {
            for (var node : table) {
                table.put(3, "3");
            }
        });
    }

    @Test
    void testEqualsSame() {
        table.put(1, "one");
        table.put(11, "two");
        table.put(2, "three");
        assertTrue(table.equals(table));
    }

    @Test
    void testEqualsNull() {
        table.put(1, "one");
        table.put(11, "two");
        table.put(2, "three");
        HashTable<Integer, String> compared = null;
        assertFalse(table.equals(compared));
    }

    @Test
    void testEqualsDiffTrue() {
        table.put(1, "one");
        table.put(11, "two");
        table.put(2, "three");
        HashTable<Integer, String> compared = new HashTable<>();
        compared.put(1, "one");
        compared.put(11, "two");
        compared.put(2, "three");

        assertTrue(table.equals(compared));
    }

    @Test
    void testEqualsDiffFalse() {
        table.put(1, "one");
        table.put(11, "two");
        table.put(2, "three");
        HashTable<Integer, String> compared = new HashTable<>();
        compared.put(1, "one");
        compared.put(11, "two");

        assertFalse(table.equals(compared));
    }

}
