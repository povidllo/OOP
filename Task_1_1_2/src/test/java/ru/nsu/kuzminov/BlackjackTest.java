package ru.nsu.kuzminov;

import java.util.Scanner;
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
    void testGiveCard() {
        Blackjack game = new Blackjack();

        Card givenCard = game.giveCard(game.player);
        assertNotNull(givenCard);
        assertEquals(game.player.cards.get(0), givenCard);

        givenCard = game.giveCard(game.dealer);
        assertNotNull(givenCard);
        assertEquals(game.dealer.cards.get(0), givenCard);
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

    @Test
    void testPrintWinnerFirstHalf() {
        Blackjack game = new Blackjack();

        //      ТЕСТ СЕРЕДИНЫ ИГРЫ

        //Нет победителя в середине игры
        game.player.score = 10;
        game.dealer.score = 11;
        assertFalse(game.printWinnerFirstHalf());

        //Игрок победитель в середине игры со счетом 21
        game.player.score = 21;
        game.dealer.score = 11;
        assertTrue(game.printWinnerFirstHalf());

        //Дилер победитель в середине игры со счетом 21
        game.player.score = 10;
        game.dealer.score = 21;
        assertTrue(game.printWinnerFirstHalf());

        //Игрок победитель в середине игры со счетом больше 21
        game.player.score = 31;
        game.dealer.score = 11;
        assertTrue(game.printWinnerFirstHalf());

        //Дилер победитель в середине игры со счетом больше 21
        game.player.score = 10;
        game.dealer.score = 32;
        assertTrue(game.printWinnerFirstHalf());
    }

    @Test
    void testPrintWinnerSecondHalf() {
        //      ТЕСТ КОНЦА ИГРЫ
        Blackjack game = new Blackjack();

        //Игрок победитель в конце игры со счетом 21
        game.player.score = 21;
        game.dealer.score = 11;
        assertTrue(game.printWinnerSecondHalf());

        //Дилер победитель в конце игры со счетом 21
        game.player.score = 10;
        game.dealer.score = 21;
        assertTrue(game.printWinnerSecondHalf());

        //Игрок победитель в конце игры со счетом больше 21
        game.player.score = 31;
        game.dealer.score = 11;
        assertTrue(game.printWinnerSecondHalf());

        //Дилер победитель в конце игры со счетом больше 21
        game.player.score = 10;
        game.dealer.score = 32;
        assertTrue(game.printWinnerSecondHalf());

        //Ничья
        game.player.score = 21;
        game.dealer.score = 21;
        assertTrue(game.printWinnerSecondHalf());
    }

    @Test
    void testEnterNumToContinue() {
        Blackjack blackjack = new Blackjack();

        //Проверяем 1
        Scanner scanner = new Scanner("1\n");
        int result = blackjack.enterNumToContinue(scanner);
        assertEquals(1, result);

        //Проверяем 0
        scanner = new Scanner("0\n");
        result = blackjack.enterNumToContinue(scanner);
        assertEquals(0, result);

        //Проверяем неправильный ввод без ошибки
        scanner = new Scanner("2\n1\n");
        result = blackjack.enterNumToContinue(scanner);
        assertEquals(1, result);

        //Проверяем неправильный ввод с ошибкой
        scanner = new Scanner("abs\n1\n");
        result = blackjack.enterNumToContinue(scanner);
        assertEquals(1, result);
    }

    @Test
    void testGame() {
        Blackjack blackjack = new Blackjack();
        Scanner scanner = new Scanner("1\n1\n0\n0\n1\n1\n0\n0\n");
        blackjack.game(scanner);
    }
}
