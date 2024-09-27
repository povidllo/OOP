package ru.nsu.kuzminov;

import java.util.Objects;

/**
 * Класс предоставляет реализацию переменной.
 * Реализован с помощью интерфейса Expression.
 */
public class Variable implements Expression {

    final String var;
    int value;

    /**
     * Конструктор.
     *
     * @param newVar присваимое значение.
     */
    public Variable(String newVar) {
        var = newVar;
        value = 0;
    }

    /**
     * Производит вывод выражения в стандартный поток вывода.
     */
    @Override
    public void print() {
        System.out.print(var);
    }

    /**
     * Производит дифференцирование переменной.
     *
     * @param x переменная, по которой дифференцируем.
     * @return возвращает новое выражение.
     */
    @Override
    public Expression derivative(String x) {
        if (Objects.equals(this.var, x)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    /**
     * Подставляет число под переменную.
     *
     * @param vars строка для подстановки, передаваемая в виде
     *             "var1 = val1; var2 = val2".
     * @return вычисленное значение.
     */
    @Override
    public double eval(String vars) {
        String newVar = var + " =";
        int ind = vars.indexOf(var);
        if (ind == -1) {
            throw new RuntimeException("Нет переменной " + var);
        }
        ind += var.length() + 3;
        String newNum = "";
        while (ind < vars.length() && vars.charAt(ind) != ';') {
            newNum += vars.charAt(ind);
            ind++;
        }
        value = Integer.parseInt(newNum);
        return value;
    }
}
