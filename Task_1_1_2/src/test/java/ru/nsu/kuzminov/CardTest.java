package ru.nsu.kuzminov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void testConstructor() {
        Card card = new Card(10, "name");
        assertEquals(card.showValue(), 10);
        assertEquals(card.showName(), "name");
    }

    @Test
    void testShowValue() {
        Card card = new Card(3, "aga");
        assertEquals(card.showValue(), 3);
    }

    @Test
    void testShowName() {
        Card card = new Card(3, "ogo");
        assertEquals(card.showName(), "ogo");
    }

    @Test
    void testSetVal() {
        Card card = new Card(3, "ogo");
        card.setVal(4);
        assertEquals(card.showValue(), 4);
    }
}