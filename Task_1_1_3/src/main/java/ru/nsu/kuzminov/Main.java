package ru.nsu.kuzminov;

public class Main {
    public static void main(String[] args) {
        String expression = "4 3 3 + *";  // Это польская нотация для (3 + (2 * x))
        Expression e = ExpressionParser.parse(expression);
        e.print();  // Выведет: + 3 * 2 x

//        Expression de = e.derivative("x");
//        de.print();  // Выведет: + 0 * 0 x 0 1
    }
}

