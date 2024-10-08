package ru.nsu.kuzminov;

/**
 * Представляет класс одной карты со значением и именем.
 */
public class Card {
    private int val;
    private final String name;

    /**
     * Конструктор.
     *
     * @param newVal  Значение карты.
     * @param newName Имя карты.
     */
    public Card(int newVal, String newName) {
        val = newVal;
        name = newName;
    }

    /**
     * Возвращает значение карты.z
     *
     * @return Возвращает значение карты.
     */
    public int showValue() {
        return val;
    }

    /**
     * Возвращает имя карты.
     *
     * @return Возвращает имя карты.
     */
    public String showName() {
        return name;
    }

    /**
     * Устанавливает новое значение для карты.
     *
     * @param newVal Новое значение карты.
     */
    public void setVal(int newVal) {
        val = newVal;
    }
}
