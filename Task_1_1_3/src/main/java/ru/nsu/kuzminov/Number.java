package ru.nsu.kuzminov;

/**
 * Класс предоставляет реализацию константы.
 * Реализован с помощью интерфейса Expression.
 */
public class Number implements Expression {

    private final int constNum;

    /**
     * Конструктор.
     * @param value присваимое значение.
     */
    public Number(int value) {
        constNum = value;
    }

    /**
     * Производит вывод выражения в стандартный поток вывода.
     */
    @Override
    public void print() {
        System.out.print(constNum);
    }

    /**
     * Производит дифференцирование выражения.
     *
     * @param x переменная, по которой дифференцируем.
     * @return возвращает новое выражение.
     */
    @Override
    public Expression derivative(String x) {
        return new Number(0);
    }

    /**
     * Возвращает константу.
     *
     * @param vars строка для подстановки, передаваемая в виде
     *             "var1 = val1; var2 = val2".
     * @return вычисленное значение.
     */
    @Override
    public double eval(String vars) {
        return constNum;
    }
}
