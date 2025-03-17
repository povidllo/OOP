package ru.nsu.kuzminov;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Основной класс приложения.
 * Создает пекарей, курьеров и управляет процессом обработки заказов.
 */
public class Main {
    private static int orderCount = 0;
    private static int bakerCount = 0;
    private static int courierCount = 0;
    private static int warehouseCap = 0;
    private static int timeBetweenOrders = 0;
    private static int timeAfterAllOrders = 0;

    private static final List<Integer> bakerSpeed = new ArrayList<>();
    private static final List<Integer> courierCap = new ArrayList<>();
    private static final List<Integer> courierSpeed = new ArrayList<>();

    private static final List<Thread> bakersThreads = new ArrayList<>();
    private static final List<Thread> courierThreads = new ArrayList<>();

    /**
     * Основной метод программы.
     * Инициализирует параметры системы, создает потоки для пекарей и курьеров,
     * генерирует заказы и управляет завершением работы.
     *
     * @param args Аргументы командной строки (не используются).
     * @throws InterruptedException Если поток был прерван во время выполнения.
     */
    public static void main(String[] args) throws InterruptedException, IOException, ParseException {

        readPizzeriaData();

        QueueOrder<Order> orderQueue = new QueueOrder<>(orderCount);
        QueueOrder<Order> deliveryOrderQueue = new QueueOrder<>(warehouseCap);

        createAndRunBakers(orderQueue, deliveryOrderQueue);
        createAndRunCourier(deliveryOrderQueue);

        for (int i = 0; i < orderCount; i++) {
            Order newOrder = new Order(i, "Prepare for baking");
            orderQueue.insert(newOrder);
            System.out.println("Поступил заказ " + i);
            Thread.sleep(timeBetweenOrders * 1000L);
        }
        Thread.sleep(timeAfterAllOrders * 1000L);

        orderQueue.close();
        for (Thread oneBaker : bakersThreads) {
            oneBaker.join();
        }
        deliveryOrderQueue.close();
        for (Thread oneCourier : courierThreads) {
            oneCourier.join();
        }

    }

    private static void createAndRunCourier(QueueOrder<Order> deliveryOrderQueue) {
        for (int i = 0; i < courierCount; i++) {
            Courier oneCourier = new Courier(i, courierCap.get(i),
                    courierSpeed.get(i), deliveryOrderQueue);
            Thread oneCourierThread = new Thread(oneCourier);
            courierThreads.add(oneCourierThread);
            oneCourierThread.start();
        }
    }

    private static void createAndRunBakers(QueueOrder<Order> orderQueue, QueueOrder<Order> deliveryOrderQueue) {
        for (int i = 0; i < bakerCount; i++) {
            Baker oneBaker = new Baker(i, bakerSpeed.get(i), orderQueue, deliveryOrderQueue);
            Thread oneBakerThread = new Thread(oneBaker);
            bakersThreads.add(oneBakerThread);
            oneBakerThread.start();
        }
    }

    private static void readPizzeriaData() throws IOException, ParseException {
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

        JSONArray cCap = (JSONArray) jo.get("courierCap");
        for (Object cap : cCap) {
            courierCap.add(((Long) cap).intValue());
        }

        JSONArray cSpeed = (JSONArray) jo.get("courierSpeed");
        for (Object speed : cSpeed) {
            courierSpeed.add(((Long) speed).intValue());
        }
    }
}