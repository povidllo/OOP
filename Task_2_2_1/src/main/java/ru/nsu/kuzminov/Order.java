package ru.nsu.kuzminov;

/**
 * Класс, представляющий собой один заказ.
 */
public class Order {
    final int id;
    String status;

    /**
     * Конструктор класса.
     *
     * @param id     id заказа.
     * @param status статус заказа.
     */
    public Order(int id, String status) {
        this.id = id;
        this.status = status;
    }

    /**
     * Метод задающий новый статус заказу.
     *
     * @param newStatus Новый статус для заказа.
     */
    public synchronized void setNewStatus(String newStatus) {
        this.status = newStatus;
    }

    /**
     * Метод для получения id заказа.
     *
     * @return возвращает id заказа.
     */
    public int getId() {
        return id;
    }

    /**
     * Метод для получения статуса заказа.
     *
     * @return возвращает статус заказа.
     */
    public String getStatus() {
        return status;
    }
}
