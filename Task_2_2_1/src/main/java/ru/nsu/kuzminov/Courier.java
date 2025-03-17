package ru.nsu.kuzminov;

import java.util.ArrayList;

/**
 * Класс, представляющий курьера.
 * Курьер забирает готовые заказы из очереди и доставляет их.
 */
public class Courier implements Runnable {
    final int id;
    final int cap;
    final int speed;
    QueueOrder<Order> orderQueue;

    /**
     * Конструктор класса Courier.
     *
     * @param id  Уникальный идентификатор курьера.
     * @param cap  Вместимость курьера (максимальное количество заказов за одну доставку).
     * @param speed Скорость доставки (в секундах).
     * @param deliveryOrderQueue Очередь заказов для доставки.
     */
    public Courier(int id, int cap, int speed, QueueOrder<Order> deliveryOrderQueue) {
        this.id = id;
        this.cap = cap;
        this.speed = speed;
        this.orderQueue = deliveryOrderQueue;
    }

    /**
     * Основной метод выполнения работы курьера.
     * Курьер забирает заказы из очереди и доставляет их.
     */
    @Override
    public void run() {
        try {
            int size = 0;
            ArrayList<Integer> orders = new ArrayList<>();
            while (!orderQueue.isClosed() || (orderQueue.isClosed() && orderQueue.getSize() != 0)
                    || (orderQueue.isClosed() && size != 0)) {
                Order order = orderQueue.see();
                if (size == 0 && order == null) {
                    continue;
                } else if ((order == null && size > 0) || size == this.cap) {
                    System.out.println("Курьер номер " + this.id + " доставляет заказ/ы " + orders);
                    size = 0;
                    Thread.sleep(this.speed * 1000L);
                    System.out.println("Курьер номер " + this.id + " доставил заказ/ы " + orders);
                    orders.clear();
                    continue;
                }
                order = orderQueue.get();
                orders.add(order.getId());
                size++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
