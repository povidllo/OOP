package ru.nsu.kuzminov;

/**
 * Предоставляет интерфейс выражения.
 */
public interface Expression {
    /**
     * Печатает выражения в стандартный поток вывода.
     */
    void print();

    /**
     * Производит дифференцирование выражения.
     *
     * @param x переменная, по которой дифференцируем.
     * @return возвращает новое выражение.
     */
    Expression derivative(String x);

    /**
     * Производит подстановку и вычисление.
     *
     * @param vars строка для подстановки, передаваемая в виде
     *             "var1 = val1; var2 = val2".
     * @return возвращает вычисление.
     */
    double eval(String vars);
}
