package ru.nsu.kuzminov;

import java.util.Stack;

public class ExpressionParser {

    public static Expression parse(String expr) {
        Stack<Expression> stack = new Stack<>();
        String[] tokens = expr.split(" ");

        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Недостаточно операндов для операции: " + token);
                }
                Expression right = stack.pop();
                Expression left = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(new Add(left, right));
                        break;
                    case "-":
                        stack.push(new Sub(left, right));
                        break;
                    case "*":
                        stack.push(new Mul(left, right));
                        break;
                    case "/":
                        stack.push(new Div(left, right));
                        break;
                    default:
                        throw new IllegalArgumentException("Неизвестный оператор: " + token);
                }
            } else if (token.matches("\\d+")) {
                stack.push(new Number(Integer.parseInt(token)));
            } else {
                stack.push(new Variable(token));
            }
        }

        return stack.peek();
    }
}
