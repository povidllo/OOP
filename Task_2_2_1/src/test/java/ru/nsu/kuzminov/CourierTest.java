package ru.nsu.kuzminov;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourierTest {

    @Test
    void testCourierRun() throws InterruptedException {
        QueueOrder<Order> deliveryQueue = new QueueOrder<>(10);
        Courier courier = new Courier(1, 2, 1, deliveryQueue);

        Thread courierThread = new Thread(courier);
        courierThread.start();

        deliveryQueue.insert(new Order(1, "Ready"));
        deliveryQueue.insert(new Order(2, "Ready"));
        Thread.sleep(2000); // Даем курьеру время на доставку

        assertEquals(0, deliveryQueue.getSize());
        courierThread.interrupt();
    }
} 