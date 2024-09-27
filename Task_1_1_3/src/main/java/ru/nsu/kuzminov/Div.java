package ru.nsu.kuzminov;

/**
 * Класс предоставляет реализацию деления двух выражений.
 * Реализован с помощью интерфейса Expression.
 */
public class Div implements Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор класса.
     *
     * @param one первое выражение.
     * @param two второе выражение.
     */
    public Div(Expression one, Expression two) {
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
        System.out.print("/");
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
        return new Div(new Add(newLeft, newRight), new Mul(right, right));
    }

    /**
     * Производит вычисление частного.
     *
     * @param vars строка для подстановки, передаваемая в виде
     *             "var1 = val1; var2 = val2".
     * @return вычисленное значение.
     */
    @Override
    public double eval(String vars) {
        double l = left.eval(vars);
        double r = right.eval(vars);
        if (r == 0) {
            throw new ArithmeticException("Divide by zero");
        }
        return l / r;
    }
}
