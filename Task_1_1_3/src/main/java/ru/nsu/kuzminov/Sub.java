package ru.nsu.kuzminov;

/**
 * Класс предоставляет реализацию суммирования двух выражений.
 * Реализован с помощью интерфейса Expression.
 */
public class Sub implements Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Конструктор класса.
     *
     * @param one первое выражение.
     * @param two второе выражение.
     */
    public Sub(Expression one, Expression two) {
        left = one;
        right = two;
    }

    /**
     * Производит вывод выражения в стандартный поток вывода.
     */
    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("-");
        right.print();
        System.out.print(")");
    }

    /**
     * Производит дифференцирование выражения.
     *
     * @param x переменная, по которой дифференцируем.
     * @return возвращает новое выражение.
     */
    @Override
    public Expression derivative(String x) {
        return new Add(left.derivative(x), right.derivative(x));
    }

    /**
     * Производит вычисление суммы.
     *
     * @param vars строка для подстановки, передаваемая в виде
     *             "var1 = val1; var2 = val2".
     * @return вычисленное значение.
     */
    @Override
    public double eval(String vars) {
        return left.eval(vars) - right.eval(vars);
    }
}
