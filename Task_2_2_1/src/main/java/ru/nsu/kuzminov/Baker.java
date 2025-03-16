package ru.nsu.kuzminov;

/**
 * Класс, представляющий пекаря.
 * Пекарь получает заказы из очереди, готовит их и помещает в очередь доставки.
 */
public class Baker implements Runnable {

    final int id;
    final int speed;
    QueueOrder<Order> order_queue;
    QueueOrder<Order> delivery_order_queue;

    /**
     * Конструктор класса Baker.
     *
     * @param id                   Уникальный идентификатор пекаря.
     * @param speed                Скорость приготовления заказа (в секундах).
     * @param order_queue          Очередь заказов для приготовления.
     * @param delivery_order_queue Очередь заказов для доставки.
     */
    public Baker(int id, int speed, QueueOrder<Order> order_queue,
                 QueueOrder<Order> delivery_order_queue) {
        this.id = id;
        this.speed = speed;
        this.order_queue = order_queue;
        this.delivery_order_queue = delivery_order_queue;
    }

    /**
     * Основной метод выполнения работы пекаря.
     * Пекарь берет заказы из очереди, готовит их и помещает в очередь доставки.
     */
    @Override
    public void run() {
        Order cur_order;
        while (true) {
            try {
                cur_order = order_queue.get();
                if (cur_order == null) {
                    System.out.println("Пекарь номер " + this.id + " ушел домой");
                    return;
                }
                System.out.println("Пекарь номер " + this.id + " готовит заказ с номером " + cur_order.getId());
                Thread.sleep(speed * 1000L);
                delivery_order_queue.insert(cur_order);
                System.out.println("Пекарь номер " + this.id + " поместил на слад заказ " + cur_order.getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
