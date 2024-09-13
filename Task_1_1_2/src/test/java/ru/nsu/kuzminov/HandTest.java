package ru.nsu.kuzminov;

import org.junit.jupiter.api.Test;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void testConstructor() {
        Hand hand = new Hand();
        assertEquals(hand.score, 0);
        assertTrue(hand.cards.isEmpty());
    }

    @Test
    void testAddCard() {
        Hand hand = new Hand();
        Card card1 = new Card(10, "first");
        Card card2 = new Card(20, "second");
        hand.addCard(card1);
        hand.addCard(card2);
        assertEquals(hand.cards.get(0), card1);
        assertEquals(hand.cards.get(1), card2);
    }

    @Test
    void testPrintCardsDealerHidden() {
        Hand hand = new Hand();
        Card card1 = new Card(10, "first");
        Card card2 = new Card(20, "second");
        hand.addCard(card1);
        hand.addCard(card2);

        java.io.ByteArrayOutputStream outString = new java.io.ByteArrayOutputStream();
        java.io.PrintStream newStream = new PrintStream(outString);
        System.setOut(newStream);

        String expString = "    Карты дилера: [first (10), <закрытая карта>]\n";
        hand.printCards(1, 1);
        assertEquals(outString.toString(), expString);
    }

    @Test
    void testPrintCardsDealerNotHidden() {
        Hand hand = new Hand();
        Card card1 = new Card(10, "first");
        Card card2 = new Card(20, "second");
        hand.addCard(card1);
        hand.addCard(card2);

        java.io.ByteArrayOutputStream outString = new java.io.ByteArrayOutputStream();
        java.io.PrintStream newStream = new PrintStream(outString);
        System.setOut(newStream);

        String expString = "    Карты дилера: [first (10), second (20)] => 0\n";
        hand.printCards(0, 1);
        assertEquals(outString.toString(), expString);
    }

    @Test
    void testPrintCardsPlayer() {
        Hand hand = new Hand();
        Card card1 = new Card(10, "first");
        Card card2 = new Card(20, "second");
        hand.addCard(card1);
        hand.addCard(card2);

        java.io.ByteArrayOutputStream outString = new java.io.ByteArrayOutputStream();
        java.io.PrintStream newStream = new PrintStream(outString);
        System.setOut(newStream);

        String expString = "    Ваши карты: [first (10), second (20)] => 0\n";
        hand.printCards(0, 0);
        assertEquals(outString.toString(), expString);
    }
}