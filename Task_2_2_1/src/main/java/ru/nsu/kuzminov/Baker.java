package ru.nsu.kuzminov;

/**
 * Класс, представляющий пекаря.
 * Пекарь получает заказы из очереди, готовит их и помещает в очередь доставки.
 */
public class Baker implements Runnable {

    private final int id;
    private final int speed;
    private final QueueOrder<Order> orderQueue;
    private final QueueOrder<Order> deliveryOrderQueue;

    /**
     * Конструктор класса Baker.
     *
     * @param id                   Уникальный идентификатор пекаря.
     * @param speed                Скорость приготовления заказа (в секундах).
     * @param orderQueue          Очередь заказов для приготовления.
     * @param deliveryOrderQueue Очередь заказов для доставки.
     */
    public Baker(int id, int speed, QueueOrder<Order> orderQueue,
                 QueueOrder<Order> deliveryOrderQueue) {
        this.id = id;
        this.speed = speed;
        this.orderQueue = orderQueue;
        this.deliveryOrderQueue = deliveryOrderQueue;
    }

    /**
     * Основной метод выполнения работы пекаря.
     * Пекарь берет заказы из очереди, готовит их и помещает в очередь доставки.
     */
    @Override
    public void run() {
        try {
            Order curOrder;
            while (true) {
                    curOrder = orderQueue.get();
                    if (curOrder == null) {
                        System.out.println("Пекарь номер " + this.id + " ушел домой");
                        return;
                    }
                    System.out.println("Пекарь номер " + this.id + " готовит заказ с номером "
                            + curOrder.getId());
                    Thread.sleep(speed * 1000L);
                    deliveryOrderQueue.insert(curOrder);
                    System.out.println("Пекарь номер " + this.id + " поместил на слад заказ "
                            + curOrder.getId());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
