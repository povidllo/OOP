package ru.nsu.kuzminov;

import java.util.ArrayList;


public class Hand {
    public ArrayList<Card> cards;
    public int score;

    /**
     * constructor
     */
    public Hand() {
        score = 0;
        cards = new ArrayList<Card>();
    }

    /**
     * add card in hand
     *
     * @param newCard the card we put into our hand
     */
    public void addCard(Card newCard) {
        cards.add(newCard);
    }

    /**
     * print out hand
     *
     * @param flagHide - 1 if we need print dealer cards with hidden card, else 0
     * @param who      - 0 if player, 1 if dealer
     */
    public void printCards(int flagHide, int who) {
        if (flagHide == 1) {
            System.out.println("    Карты дилера: [" + cards.get(0).showName() + " (" + cards.get(0).showValue() + "), <закрытая карта>]");
            return;
        }
        if (who == 0) {  // player
            System.out.print("    Ваши карты: [");
        } else if (who == 1) {
            System.out.print("    Карты дилера: [");
        }
        for (int i = 0; i < cards.size(); i++) {
            System.out.print(cards.get(i).showName() + " (" + cards.get(i).showValue() + ")");
            if (i != cards.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("] => " + score);
        return;
    }
}
