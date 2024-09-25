package ru.nsu.kuzminov;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Представляет класс Колоды.
 */
public class Deck {
    public ArrayList<Card> deck;
    public int count;

    /**
     * Конструктор.
     */
    public Deck() {
        createNewDeck();
    }

    /**
     * Помощник для конструктора.
     */
    public void createNewDeck() {
        deck = new ArrayList<Card>(52);

        String path = System.getProperty("user.dir") + File.separator + "CardsList.txt";

        try (Scanner sc = new Scanner(new File(path))) {

            int[] cardValues = {
                2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11, // Черви
                2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11, // Бубны
                2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11, // Трефы
                2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11  // Пики
            };

            for (int i = 0; i < 52 && sc.hasNext(); i++) {
                String name = sc.next() + " " + sc.next();
                deck.add(new Card(cardValues[i], name));
            }

            Collections.shuffle(deck);
            count = 52;
            sc.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Берет карту с колоды.
     *
     * @return Возвращает последнюю карту с колоды.
     */
    public Card takeCard() {
        if (count == 0) {
            createNewDeck();
        }
        count--;
        return deck.remove(deck.size() - 1);
    }


}
