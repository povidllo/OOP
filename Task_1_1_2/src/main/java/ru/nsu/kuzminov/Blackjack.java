package ru.nsu.kuzminov;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Класс нашей игры.
 */
public class Blackjack {

    final Deck gameDeck;
    Hand player;
    Hand dealer;
    int round;
    int playerScore;
    int dealerScore;
    //turn 0 is player, 1 is dealer

    /**
     * Конструктор.
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
     * Дает по две карты дилеру и игроку.
     */
    public void dealCard() {
        for (int i = 0; i < 2; i++) {
            giveCard(player);
            giveCard(dealer);
        }
    }

    /**
     * Дает карту выбранной руке.
     *
     * @param hand выбранная рука.
     * @return возвращает выданную карту.
     */
    public Card giveCard(Hand hand) {
        Card newCard = gameDeck.takeCard();
        hand.addCard(newCard);
        hand.score += newCard.showValue();
        if (hand.score > 21) {
            for (Card i : hand.cards) {
                if (i.showName().contains("Туз") && i.showValue() != 1) {
                    i.setVal(1);
                    hand.score -= 10;
                }
            }
        }
        return newCard;
    }

    /**
     * Проверяет есть ли победитель на данный момент или нет.
     *
     * @return hand победителя если таковой имеется или null в противном случае.
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
     * Выводит победителя после первой половины игры, если такой присутствует.
     *
     * @return true если вывел победителя, иначе false.
     */
    public boolean printWinnerFirstHalf() {
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
            return true;
        }
        return false;
    }

    /**
     * Выводит победителя в конце игры если после хода игрока нет победителя.
     */
    public boolean printWinnerSecondHalf() {
        Hand winner = null;
        try {
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
            }

            if (playerScore > dealerScore) {
                System.out.println(" в вашу пользу\n");
            } else if (dealerScore > playerScore) {
                System.out.println(" в пользу дилера\n");
            } else {
                System.out.println(" Ничья\n");
            }
        } catch (Exception exc) {
            return false;
        }

        return true;
    }

    /**
     * Предлагает пользователю ввести 1 или 0 для продолжения или завершения игры соответственно.
     *
     * @param scanner наш сканер.
     * @return возвращает введенное число если оно 1 или 0.
     */
    public int enterNumToContinue(Scanner scanner) {
        int num;
        System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться .");
        while (true) {
            try {
                num = scanner.nextInt();
                if (num == 0 || num == 1) {
                    break;
                }
            } catch (InputMismatchException exception) {
                System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться .");
                scanner.next();
            }
        }
        return num;
    }

    /**
     * Происходит обработка хода игрока.
     *
     * @param scanner сканер.
     */
    public void processPlayersHand(Scanner scanner) {
        while (true) {
            System.out.println("Ваш ход\n-------");

            int ans = enterNumToContinue(scanner); //ждем ввода 0 или 1

            if (ans == 1) {
                Card newCard = giveCard(player);
                System.out.println("Вы открыли карту " + newCard.showName()
                        + " (" + newCard.showValue() + ")");
                player.printCards(0, 0);
                dealer.printCards(1, 1);
            } else {
                break;
            }
        }
    }

    /**
     * Происходит обработка хода дилера.
     */
    public void processDealerHand() {
        while (dealer.score < 17 && dealer.score < player.score) {
            System.out.println("Ход дилера\n-------");
            Card takenCard = giveCard(dealer);
            System.out.println("Дилер открывает карту " + takenCard.showName()
                    + "(" + takenCard.showValue() + ")");
            player.printCards(0, 0);
            dealer.printCards(0, 1);
        }
    }

    /**
     * Запускает основную игру.
     */
    public void game(Scanner scanner) {
        System.out.println("Добро пожаловать в Блэкджек!");

        //Запускаем 4 раунда
        for (int i = 0; i < 4; i++) {
            round++;
            System.out.println("Раунд " + round);
            System.out.println("Дилер раздал карты");

            player.clearCards();
            dealer.clearCards();
            player.score = 0;
            dealer.score = 0;

            dealCard();
            player.printCards(0, 0);
            dealer.printCards(1, 1);

            //Обработка руки игрока
            processPlayersHand(scanner);

            System.out.println("Ход дилера\n-------");
            System.out.println("Дилер открывает закрытую карту "
                    + dealer.cards.get(1).showName() + " ("
                    + dealer.cards.get(1).showValue() + ")");
            player.printCards(0, 0);
            dealer.printCards(0, 1);


            //Вывести победиля если такой присутствует
            boolean cont = printWinnerFirstHalf();
            if (cont) {
                continue;
            }

            //Обработка руки дилера
            processDealerHand();

            //Выводим победителя в конце игры
            printWinnerSecondHalf();

        }
    }

    /**
     * main.
     *
     * @param args аргс.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Blackjack game = new Blackjack();
        game.game(scanner);
    }
}
