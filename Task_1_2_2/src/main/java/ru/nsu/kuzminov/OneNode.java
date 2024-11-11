package ru.nsu.kuzminov;

import java.util.Objects;

/**
 * Класс для предоставления элемента хэш таблицы.
 *
 * @param <K> - тип ключа.
 * @param <V> - тип значения.
 */
public class OneNode<K, V> {
    private K key;
    private V value;

    private OneNode<K, V> next;

    /**
     * Конструктор.
     *
     * @param newKey   - новый ключ.
     * @param newValue - новое значение.
     */
    public OneNode(K newKey, V newValue) {
        key = newKey;
        value = newValue;
        next = null;
    }

    /**
     * Получить следующий элемент в списке элементов.
     *
     * @return - следующий элемент.
     */
    public OneNode<K, V> getNext() {
        return next;
    }

    /**
     * Задать следующий элемент в списке элементов.
     *
     * @param newNext - Новое значение.
     */
    public void setNext(OneNode<K, V> newNext) {
        next = newNext;
    }

    /**
     * Получить значение элемента.
     *
     * @return - значение элемента.
     */
    public V getValue() {
        return value;
    }

    /**
     * Получить ключ элемента.
     *
     * @return - ключ элемента.
     */
    public K getKey() {
        return key;
    }

    /**
     * Задать новое значение элементу.
     *
     * @param newValue - новое значение.
     */
    public void setValue(V newValue) {
        value = newValue;
    }

    /**
     * Сравнение на равенство.
     *
     * @param o с чем сравниваем.
     * @return равны или нет.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OneNode<?, ?> oNode = (OneNode<?, ?>) o;
        return Objects.equals(key, oNode.key) && Objects.equals(value, oNode.value);
    }

    /**
     * Приведение к строке.
     *
     * @return строка.
     */
    @Override
    public String toString() {
        return this.getKey() + " = " + this.getValue();
    }
}
