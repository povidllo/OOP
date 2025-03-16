package ru.nsu.kuzminov;

/**
 * Класс, представляющий собой один заказ.
 */
public class Order {
    final int id;
    String status;

    public Order(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public synchronized void setNewStatus(String new_status) {
        this.status = new_status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
