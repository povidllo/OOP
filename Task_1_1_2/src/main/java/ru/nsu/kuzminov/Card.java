package ru.nsu.kuzminov;


public class Card {
    private int val;
    private String name;

    /**
     *Constructor
     * @param newVal value of the card
     * @param newName name of the card
     */
    public Card(int newVal, String newName) {
        val = newVal;
        name = newName;
    }

    /**
     * @return card value
     */
    public int showValue() {
        return val;
    }

    /**
     * @return card name
     */
    public String showName() {
        return name;
    }

    /**
     * set new card value
     * @param newVal new card value
     */
    public void setVal(int newVal) {
        val = newVal;
    }
}
