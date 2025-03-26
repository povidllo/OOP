package ru.nsu.kuzminov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void testOrderCreation() {
        Order order = new Order(1, "Pending");
        assertEquals(1, order.getId());
        assertEquals("Pending", order.getStatus());
    }

    @Test
    void testSetNewStatus() {
        Order order = new Order(1, "Pending");
        order.setNewStatus("Processing");
        assertEquals("Processing", order.getStatus());
    }
} 