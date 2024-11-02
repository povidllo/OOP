package ru.nsu.kuzminov;

import java.util.*;


/**
 * Реализация хэш таблицы.
 *
 * @param <K> - Тип ключей.
 * @param <V> - Тип значений.
 */
public class HashTable<K, V> implements Iterable<OneNode<K, V>> {
    private ArrayList<OneNode<K, V>> ourTable;
    private int tableSize;
    private int nodeSize;
    private int modCount;

    /**
     * Конструктор.
     */
    public HashTable() {
        ourTable = new ArrayList<>(10);
        tableSize = 10;
        nodeSize = 0;
        modCount = 0;
        for (int i = 0; i < tableSize; i++) {
            ourTable.add(null);
        }
    }

    /**
     * Количество элементов в хэш таблице.
     *
     * @return - количество элементов.
     */
    public int size() {
        return nodeSize;
    }

    /**
     * Решает проблемы с коллизией.
     */
    private void resolveLoadFactor() {
        tableSize = tableSize * 2;
        ArrayList<OneNode<K, V>> oldOurTable = ourTable;
        ourTable = new ArrayList<>(tableSize * 2);
        for (int i = 0; i < tableSize; i++) {
            ourTable.add(null);
        }
        for (OneNode<K, V> node : oldOurTable) {
            while (node != null) {
                int hash = node.getKey().hashCode() % tableSize;
                var newNode = new OneNode<>(node.getKey(), node.getValue());
                newNode.setNext(ourTable.get(hash));
                ourTable.set(hash, newNode);
                node = node.getNext();
            }
        }
        modCount++;
    }

    /**
     * Смотрит, содержит ли хэш таблица данный ключ.
     *
     * @param key - ключ, который смотрим.
     * @return - true если содержит, иначе false.
     */
    public boolean containsKey(K key) {
        int hash = key.hashCode() % tableSize;
        OneNode<K, V> node = ourTable.get(hash);
        while (node != null) {
            if (node.getKey() == key) {
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    /**
     * Содержит ли какой-нибудь ключ заданное значение.
     *
     * @param value - значение, по которому смотрим.
     * @return - true если содержит, иначе false.
     */
    public boolean containsValue(V value) {
        for (var node : ourTable) {
            for (; node != null; node = node.getNext()) {
                if (node.getValue() == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Ассоциирует новое значение V с ключом K.
     * Либо обновляет значение на новое.
     *
     * @param ourKey   - наш ключ.
     * @param newValue - новое значение.
     * @return возвращает предыдущее значение, если его нет то null.
     */
    public V put(K ourKey, V newValue) {
        int hash = ourKey.hashCode() % tableSize;
        V oldValue = null;

        OneNode<K, V> node = ourTable.get(hash);
        for (; node != null; node = node.getNext()) {
            if (node.getKey() == ourKey) {
                oldValue = node.getValue();
                node.setValue(newValue);
                return oldValue;
            }
        }

        node = new OneNode<>(ourKey, newValue);
        node.setNext(ourTable.get(hash));
        ourTable.set(hash, node);
        nodeSize++;
        modCount++;

        double loadFactor = (double) nodeSize / (double) tableSize;
        if (loadFactor >= 0.7) {
            resolveLoadFactor();
        }
        return oldValue;
    }

    /**
     * Удалят элемент по ключу в хэш таблице.
     *
     * @param key - ключ, который ищем.
     * @return - предыдущее значение по ключу.
     */
    public V remove(K key) {
        if (!containsKey(key)) {
            throw new IllegalArgumentException("Нет значения с таким ключом");
        }
        int hash = key.hashCode() % tableSize;
        OneNode<K, V> node = ourTable.get(hash);
        OneNode<K, V> prev = null;
        V prevValue = null;
        while (node != null) {
            if (node.getKey() == key) {
                prevValue = node.getValue();
                if (prev != null) {
                    prev.setNext(node.getNext());

                } else {
                    ourTable.set(hash, node.getNext());
                }
                nodeSize--;
                modCount++;
                break;
            }
            prev = node;
            node = node.getNext();
        }

        return prevValue;
    }

    /**
     * Выводит значение по ключу.
     *
     * @param key - ключ по, которому ищем.
     * @return значение.
     */
    public V get(K key) {
        if (!containsKey(key)) {
            throw new IllegalArgumentException("Нет значения с таким ключом");
        }
        int hash = key.hashCode() % tableSize;
        OneNode<K, V> node = ourTable.get(hash);
        while (node != null) {
            if (node.getKey() == key) {
                return node.getValue();
            }
            node = node.getNext();
        }
        return null;
    }

    /**
     * Возвращает set все пар ключ = значение.
     *
     * @return set.
     */
    Set<OneNode<K, V>> oneNodeSet() {
        Set<OneNode<K, V>> set = new HashSet<>();
        for (var node : this) {
            set.add(node);
        }
        return set;
    }

    /**
     * Создает итератор.
     *
     * @return итератор.
     */
    @Override
    public Iterator<OneNode<K, V>> iterator() {
        return new Iterator<OneNode<K, V>>() {
            private int iterModCount = modCount;
            private int indexList = 0;
            private OneNode<K, V> cur = null;

            @Override
            public boolean hasNext() {
                if (iterModCount != modCount) {
                    throw new ConcurrentModificationException("Было произведено изменение");
                }
                while (cur == null && indexList < tableSize) {
                    cur = ourTable.get(indexList++);
                }
                return cur != null;
            }

            @Override
            public OneNode<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Нет больше элементов");
                }
                OneNode<K, V> nodeToReturn = cur;
                cur = cur.getNext();
                return nodeToReturn;
            }

        };
    }

    /**
     * Переводит хэш таблицу в строку.
     *
     * @return - строку.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        int counter = 0;
        for (var node : this) {
            if (counter > 0) {
                str.append("\n");
            }
            str.append(node.toString());
            counter++;

        }
        return str.toString();
    }

    /**
     * Сравнивает две хэш таблицы.
     *
     * @param o - объект, с которым сравниваем.
     * @return - true если равны, иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.ourTable.equals(((HashTable<?, ?>) o).ourTable);
    }
}
