package ru.nsu.kuzminov;

import java.util.ArrayList;
import java.util.List;

/**
 * Основной класс приложения.
 * Создает пекарей, курьеров и управляет процессом обработки заказов.
 */
public class Main {
    /**
     * Основной метод программы.
     * Инициализирует параметры системы, создает потоки для пекарей и курьеров,
     * генерирует заказы и управляет завершением работы.
     *
     * @param args Аргументы командной строки (не используются).
     * @throws InterruptedException Если поток был прерван во время выполнения.
     */
    public static void main(String[] args) throws InterruptedException {
        
        int order_count = 10;
        int baker_count = 4;
        int courier_count = 2;
        int warehouse_cap = 5;
        int time_between_orders = 1;
        int time_after_all_orders = 5;

        ArrayList<Integer> baker_speed = new ArrayList<>(List.of(3, 1, 1, 2));
        ArrayList<Integer> courier_cap = new ArrayList<>(List.of(2, 5));
        ArrayList<Integer> courier_speed = new ArrayList<>(List.of(3, 7));

        QueueOrder<Order> order_queue = new QueueOrder<>(order_count);
        QueueOrder<Order> delivery_order_queue = new QueueOrder<>(warehouse_cap);
        ArrayList<Thread> bakers_threads = new ArrayList<>();
        ArrayList<Thread> courier_threads = new ArrayList<>();

        for (int i = 0; i < baker_count; i++) {
            Baker one_baker = new Baker(i, baker_speed.get(i), order_queue, delivery_order_queue);
            Thread one_baker_thread = new Thread(one_baker);
            bakers_threads.add(one_baker_thread);
            one_baker_thread.start();
        }

        for (int i = 0; i < courier_count; i++) {
            Courier one_courier = new Courier(i, courier_cap.get(i), courier_speed.get(i), delivery_order_queue);
            Thread one_courier_thread = new Thread(one_courier);
            courier_threads.add(one_courier_thread);
            one_courier_thread.start();
        }

        for (int i = 0; i < order_count; i++) {
            Order new_order = new Order(i, "Prepare for baking");
            order_queue.insert(new_order);
            System.out.println("Поступил заказ " + i);
            Thread.sleep(time_between_orders * 1000L);
        }
        Thread.sleep(time_after_all_orders * 1000L);

        order_queue.close();
        for (var one_baker : bakers_threads) {
            one_baker.join();
        }
        delivery_order_queue.close();
        for (var one_courier : courier_threads) {
            one_courier.join();
        }

    }
}