package ru.nsu.kuzminov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class QueueOrderTest {

    @Test
    void testInsertAndGet() throws InterruptedException {
        QueueOrder<Integer> queue = new QueueOrder<>(5);
        queue.insert(1);
        queue.insert(2);
        assertEquals(1, queue.get());
        assertEquals(2, queue.get());
    }

    @Test
    void testQueueCapacity() throws InterruptedException {
        QueueOrder<Integer> queue = new QueueOrder<>(2);
        queue.insert(1);
        queue.insert(2);
        // Попытка вставить третий элемент должна заблокировать поток
        Thread thread = new Thread(() -> {
            try {
                queue.insert(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(100); // Даем потоку время для запуска
        assertEquals(2, queue.getSize());
    }

    @Test
    void testCloseQueue() throws InterruptedException {
        QueueOrder<Integer> queue = new QueueOrder<>(2);
        queue.close();
        assertNull(queue.get());
    }
} 