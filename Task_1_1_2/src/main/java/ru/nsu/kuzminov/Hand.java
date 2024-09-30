package ru.nsu.kuzminov;

import java.util.ArrayList;

/**
 * Представляет класс Руки.
 */
public class Hand {
    public ArrayList<Card> cards;
    public int score;

    /**
     * Конструктор.
     */
    public Hand() {
        score = 0;
        cards = new ArrayList<Card>();
    }

    /**
     * Добавляет карту в руку.
     *
     * @param newCard Новая карта, которую добавляем.
     */
    public void addCard(Card newCard) {
        cards.add(newCard);
    }

    /**
     * Выводим нашу руку.
     *
     * @param flagHide 1 если нужно вывести одну открытую, одну спрятанную карту дилера, иначе 0.
     * @param who      0 если игрок, 1 если дилер.
     */
    public void printCards(int flagHide, int who) {
        if (flagHide == 1) {
            System.out.println("    Карты дилера: [" + cards.get(0).showName()
                    + " (" + cards.get(0).showValue() + "), <закрытая карта>]");
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
    }

    public void clearCards() {
        this.cards.clear();
    }
}
