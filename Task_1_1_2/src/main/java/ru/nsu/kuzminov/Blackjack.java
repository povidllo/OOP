package ru.nsu.kuzminov;

import java.util.Scanner;

public class Blackjack {

    Deck gameDeck;
    Hand player, dealer;
    int round;
    int playerScore, dealerScore;
    //turn 0 is player, 1 is dealer

    /**
     * constructor
     */
    public Blackjack() {
        gameDeck = new Deck();
        player = new Hand();
        dealer = new Hand();
        round = 0;
        playerScore = 0;
        dealerScore = 0;
    }

    /**
     * give 2 cards to dealer and player
     */
    public void dealCard() {
        for (int i = 0; i < 2; i++) {
            giveCard(player);
            giveCard(dealer);
        }
    }

    /**
     * give card to chosen hand
     *
     * @param hand chosen hand
     * @return given card
     */
    public Card giveCard(Hand hand) {
        Card newCard = gameDeck.takeCard();
        hand.addCard(newCard);
        hand.score += newCard.showValue();
        if (hand.score > 21) {
            for (Card i : hand.cards) {
                if (i.showName().contains("Туз")) {
                    i.setVal(1);
                    hand.score -= 10;
                }
            }
        }
        return newCard;
    }

    /**
     * @return winner, if there's no winner then return null
     */
    public Hand checkWin() {
        if (player.score > 21 || dealer.score == 21) {
            return dealer;
        } else if (dealer.score > 21 || player.score == 21) {
            return player;
        }
        return null;
    }


    /**
     * run main game
     */
    public void game() {
        System.out.println("Добро пожаловать в Блэкджек!");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            round++;
            System.out.println("Раунд " + round);
            System.out.println("Дилер раздал карты");

            player.cards.clear();
            dealer.cards.clear();
            player.score = 0;
            dealer.score = 0;

            dealCard();
            player.printCards(0, 0);
            player.printCards(1, 1);

            while (true) { //обработка руки игрока
                System.out.println("Ваш ход\n-------");
                System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться .");
                int ans = scanner.nextInt();
                if (ans > 1 || ans < 0) {
                    System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться .");
                    continue;
                }
                if (ans == 1) {
                    Card newCard = giveCard(player);
                    System.out.println("Вы открыли карту " + newCard.showName() + " (" + newCard.showValue() + ")");
                    player.printCards(0, 0);
                    dealer.printCards(1, 1);
                } else {
                    break;
                }
            }

            System.out.println("Ход дилера\n-------");
            System.out.println("Дилер открывает закрытую карту " + dealer.cards.get(1).showName() + " (" + dealer.cards.get(1).showValue() + ")");
            player.printCards(0, 0);
            dealer.printCards(0, 1);


            if (checkWin() == player) {
                playerScore++;
                System.out.print("Вы выиграли раунд! Счет " + playerScore + ":" + dealerScore);
            } else if (checkWin() == dealer) {
                dealerScore++;
                System.out.print("Дилер выиграл! Счет " + playerScore + ":" + dealerScore);
            }
            if (checkWin() != null) {
                if (playerScore > dealerScore) {
                    System.out.println(" в вашу пользу\n");
                } else if (dealerScore > playerScore) {
                    System.out.println(" в пользу дилера\n");
                } else {
                    System.out.println(" Ничья\n");
                }
                continue;
            }


            while (dealer.score < 17 && dealer.score < player.score) { //обработка руки игрока
                System.out.println("Ход дилера\n-------");
                Card takenCard = giveCard(dealer);
                System.out.println("Дилер открывает карту " + takenCard.showName() + "(" + takenCard.showValue() + ")");
                player.printCards(0, 0);
                dealer.printCards(0, 1);
            }

            Hand winner = null;
            if ((player.score > dealer.score || dealer.score > 21) && player.score <= 21) {
                playerScore++;
                System.out.print("Вы выиграли раунд! Счет " + playerScore + ":" + dealerScore);
                winner = player;
            } else if ((player.score < dealer.score || player.score > 21) && dealer.score <= 21) {
                dealerScore++;
                System.out.print("Дилер выиграл! Счет " + playerScore + ":" + dealerScore);
                winner = dealer;
            } else if (player.score == dealer.score) {
                dealerScore++;
                playerScore++;
                System.out.print("Ничья! Счет " + playerScore + ":" + dealerScore);
                winner = null;
            }

            if (winner == player) {
                System.out.println(" в вашу пользу\n");
            } else if (winner == dealer) {
                System.out.println(" в пользу дилера\n");
            } else {
                System.out.println(" ничья\n");
            }

        }
    }

    public static void main(String[] args) {
        Blackjack game = new Blackjack();
        game.game();
    }
}