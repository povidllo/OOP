package ru.nsu.kuzminov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackTest {

    @Test
    void testConstructor() {
        Blackjack game = new Blackjack();
        assertNotNull(game.gameDeck);
        assertNotNull(game.player);
        assertNotNull(game.dealer);
        assertEquals(game.round, 0);
        assertEquals(game.playerScore, 0);
        assertEquals(game.dealerScore, 0);
    }

    @Test
    void testDealCard() {
        Blackjack game = new Blackjack();
        game.dealCard();
        assertNotNull(game.player.cards.get(0));
        assertNotNull(game.player.cards.get(1));
        assertNotNull(game.dealer.cards.get(0));
        assertNotNull(game.dealer.cards.get(1));
    }

    @Test
    void giveCard() {
        Blackjack game = new Blackjack();

        Card givenCard = game.giveCard(game.player);
        assertNotNull(givenCard);
        assertEquals(game.player.cards.getFirst(), givenCard);

        givenCard = game.giveCard(game.dealer);
        assertNotNull(givenCard);
        assertEquals(game.dealer.cards.getFirst(), givenCard);
    }

    @Test
    void testCheckWin() {
        Blackjack game = new Blackjack();
        game.player.score = 22;
        game.dealer.score = 20;
        assertEquals(game.checkWin(), game.dealer);

        game.player.score = 20;
        game.dealer.score = 22;
        assertEquals(game.checkWin(), game.player);
    }
}