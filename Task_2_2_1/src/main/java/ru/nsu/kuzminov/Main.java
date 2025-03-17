package ru.nsu.kuzminov;

import java.io.FileReader;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

        int orderCount = 0, bakerCount = 0, courierCount = 0, warehouseCap = 0,
                timeBetweenOrders = 0, timeAfterAllOrders = 0;
        ArrayList<Integer> bakerSpeed = new ArrayList<>();
        ArrayList<Integer> courierCap = new ArrayList<>();
        ArrayList<Integer> courierSpeed = new ArrayList<>();

        try {
            Object obj = new JSONParser().parse(new FileReader("data.json"));
            JSONObject jo = (JSONObject) obj;

            orderCount = ((Long) jo.get("orderCount")).intValue();
            bakerCount = ((Long) jo.get("bakerCount")).intValue();
            courierCount = ((Long) jo.get("courierCount")).intValue();
            warehouseCap = ((Long) jo.get("warehouseCap")).intValue();
            timeBetweenOrders = ((Long) jo.get("timeBetweenOrders")).intValue();
            timeAfterAllOrders = ((Long) jo.get("timeAfterAllOrders")).intValue();

            JSONArray bSpeed = (JSONArray) jo.get("bakerSpeed");
            for (Object speed : bSpeed) {
                bakerSpeed.add(((Long) speed).intValue());
            }

            JSONArray cCap = (JSONArray) jo.get("courier_cap");
            for (Object cap : cCap) {
                courierCap.add(((Long) cap).intValue());
            }

            JSONArray cSpeed = (JSONArray) jo.get("courierSpeed");
            for (Object speed : cSpeed) {
                courierSpeed.add(((Long) speed).intValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        QueueOrder<Order> orderQueue = new QueueOrder<>(orderCount);
        QueueOrder<Order> deliveryOrderQueue = new QueueOrder<>(warehouseCap);
        ArrayList<Thread> bakersThreads = new ArrayList<>();
        ArrayList<Thread> courierThreads = new ArrayList<>();

        for (int i = 0; i < bakerCount; i++) {
            Baker oneBaker = new Baker(i, bakerSpeed.get(i), orderQueue, deliveryOrderQueue);
            Thread oneBakerThread = new Thread(oneBaker);
            bakersThreads.add(oneBakerThread);
            oneBakerThread.start();
        }

        for (int i = 0; i < courierCount; i++) {
            Courier oneCourier = new Courier(i, courierCap.get(i),
                    courierSpeed.get(i), deliveryOrderQueue);
            Thread oneCourierThread = new Thread(oneCourier);
            courierThreads.add(oneCourierThread);
            oneCourierThread.start();
        }

        for (int i = 0; i < orderCount; i++) {
            Order newOrder = new Order(i, "Prepare for baking");
            orderQueue.insert(newOrder);
            System.out.println("Поступил заказ " + i);
            Thread.sleep(timeBetweenOrders * 1000L);
        }
        Thread.sleep(timeAfterAllOrders * 1000L);

        orderQueue.close();
        for (var oneBaker : bakersThreads) {
            oneBaker.join();
        }
        deliveryOrderQueue.close();
        for (var oneCourier : courierThreads) {
            oneCourier.join();
        }

    }
}