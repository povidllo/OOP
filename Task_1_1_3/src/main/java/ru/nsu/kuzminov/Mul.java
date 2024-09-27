package ru.nsu.kuzminov;

/**
 * Класс предоставляет реализацию произведения двух выражений.
 * Реализован с помощью интерфейса Expression.
 */
public class Mul implements Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор класса.
     *
     * @param one первое выражение.
     * @param two второе выражение.
     */
    public Mul(Expression one, Expression two) {
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
        System.out.print("*");
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
        Expression newLeft = new Mul(left.derivative(x), right);
        Expression newRight = new Mul(left, right.derivative(x));
        return new Add(newLeft, newRight);
    }

    /**
     * Производит вычисление произведения.
     *
     * @param vars строка для подстановки, передаваемая в виде
     *             "var1 = val1; var2 = val2".
     * @return вычисленное значение.
     */
    @Override
    public double eval(String vars) {
        return left.eval(vars) * right.eval(vars);
    }
}
