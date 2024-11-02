package ru.nsu.kuzminov;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OneNodeTest {
    OneNode<Integer, String> node;

    @BeforeEach
    void before() {
        node = new OneNode<>(1, "one");
    }

    @Test
    void getNextWithoutNext() {
        assertNull(node.getNext());
    }

    @Test
    void setNext() {
        var secondNode = new OneNode<>(1, "one");
        node.setNext(secondNode);
        assertSame(node.getNext(), secondNode);

    }

    @Test
    void hasNext() {
        assertNull(node.getNext());
    }

    @Test
    void getValue() {
        assertEquals("one", node.getValue());
    }

    @Test
    void getKey() {
        assertEquals(1, node.getKey());
    }

    @Test
    void setValue() {
        node.setValue("two");
        assertEquals("two", node.getValue());

    }
}
