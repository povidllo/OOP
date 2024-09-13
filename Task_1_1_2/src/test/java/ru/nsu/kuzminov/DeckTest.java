package ru.nsu.kuzminov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testCreateNewDeck() {
        Deck deck = new Deck();
        deck.createNewDeck();
        assertEquals(deck.count, 52);
        assertNotNull(deck.deck);

    }

    @Test
    void testTakeCard() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) { // we take all cards from deck
            Card card = deck.takeCard();
            assertNotNull(card);
        }
        Card card = deck.takeCard(); //take card from new deck
        assertNotNull(card);
    }
}