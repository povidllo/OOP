package ru.nsu.kuzminov;


import java.util.*;

public class Deck {
    public ArrayList<Card> deck;
    public int count;

    public Deck() {
        createNewDeck();
    }

    /**
     * constructor assistant
     */
    public void createNewDeck() {
        deck = new ArrayList<Card>(52);

        String[] names = {"Двойка Червей", "Тройка Червей", "Четвёрка Червей", "Пятёрка Червей", "Шестёрка Червей", "Семёрка Червей", "Восьмёрка Червей", "Девятка Червей", "Десятка Червей", "Валет Червей", "Дама Червей", "Король Червей", "Туз Червей",
                "Двойка Бубен", "Тройка Бубен", "Четвёрка Бубен", "Пятёрка Бубен", "Шестёрка Бубен", "Семёрка Бубен", "Восьмёрка Бубен", "Девятка Бубен", "Десятка Бубен", "Валет Бубен", "Дама Бубен", "Король Бубен", "Туз Бубен",
                "Двойка Треф", "Тройка Треф", "Четвёрка Треф", "Пятёрка Треф", "Шестёрка Треф", "Семёрка Треф", "Восьмёрка Треф", "Девятка Треф", "Десятка Треф", "Валет Треф", "Дама Треф", "Король Треф", "Туз Треф",
                "Двойка Пик", "Тройка Пик", "Четвёрка Пик", "Пятёрка Пик", "Шестёрка Пик", "Семёрка Пик", "Восьмёрка Пик", "Девятка Пик", "Десятка Пик", "Валет Пик", "Дама Пик", "Король Пик", "Туз Пик"};
        int[] cardValues = {
                2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11,  // Черви
                2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11,  // Бубны
                2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11,  // Трефы
                2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11   // Пики
        };
        for (int i = 0; i < 52; i++) {
            deck.add(new Card(cardValues[i], names[i]));
        }

        Collections.shuffle(deck);

        count = 52;
    }

    /**
     * @return last card from the deck
     */
    public Card takeCard() {
        if (count == 0) {
            createNewDeck();
        }
        count--;
        return deck.remove(deck.size() - 1);
    }


}