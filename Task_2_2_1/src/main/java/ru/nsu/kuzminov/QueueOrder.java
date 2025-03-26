package ru.nsu.kuzminov;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Класс, реализующий потокобезопасную очередь с ограниченной вместимостью.
 * Очередь поддерживает операции добавления, извлечения и просмотра элементов.
 *
 * @param <T> Тип элементов, хранящихся в очереди.
 */
public class QueueOrder<T> {
    Queue<T> queue = new LinkedList<>();
    int capacity;
    boolean isClosed = false;

    /**
     * Конструктор очереди.
     *
     * @param capacity Максимальная вместимость очереди
     */
    public QueueOrder(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Закрывает очередь, уведомляя все ожидающие потоки.
     */
    public synchronized void close() {
        isClosed = true;
        notifyAll();
    }

    /**
     * Проверяет, закрыта ли очередь.
     *
     * @return true, если очередь закрыта, иначе false.
     */
    public synchronized boolean isClosed() {
        return isClosed;
    }

    /**
     * Возвращает текущее количество элементов в очереди.
     *
     * @return Количество элементов в очереди.
     */
    public synchronized int getSize() {
        return queue.size();
    }

    /**
     * Добавляет элемент в очередь.
     * Если очередь заполнена, поток блокируется до появления свободного места.
     *
     * @param elem Элемент для добавления.
     * @throws InterruptedException Если поток был прерван во время ожидания.
     */
    public synchronized void insert(T elem) throws InterruptedException {
        while (this.getSize() >= capacity) {
            wait();
        }

        queue.add(elem);
        notifyAll();
    }

    /**
     * Просматривает первый элемент в очереди без его извлечения.
     *
     * @return Первый элемент в очереди или null, если очередь пуста.
     * @throws InterruptedException Если поток был прерван во время выполнения.
     */
    public synchronized T see() throws InterruptedException {
        if (this.getSize() == 0) {
            return null;
        }

        return queue.peek();
    }

    /**
     * Извлекает и удаляет первый элемент из очереди.
     * Если очередь пуста, поток блокируется до появления элемента или закрытия очереди.
     *
     * @return Первый элемент в очереди или null, если очередь закрыта и пуста.
     * @throws InterruptedException Если поток был прерван во время ожидания.
     */
    public synchronized T get() throws InterruptedException {
        while (this.getSize() == 0 && !this.isClosed()) {
            wait();
        }
        if (this.isClosed() && this.getSize() == 0) {
            return null;
        }

        T elem = queue.poll();
        notifyAll();
        return elem;
    }
}
