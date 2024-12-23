package ru.nsu.kuzminov;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExpressionParserTest {

    @Test
    void testExpression1() {
        String expression = "4 3 3 + *";
        Expression parsedExpression = ExpressionParser.parse(expression);
        double result = parsedExpression.eval("");
        double expected = 24.0;
        assertEquals(expected, result);
    }

    @Test
    void testExpression2() {
        String expression = "3 2 + 5 *";
        Expression parsedExpression = ExpressionParser.parse(expression);
        double result = parsedExpression.eval("");
        double expected = 25.0;
        assertEquals(expected, result);
    }

    @Test
    void testExpression3() {
        String expression = "5 3 2 + *";
        Expression parsedExpression = ExpressionParser.parse(expression);
        double result = parsedExpression.eval("");
        double expected = 25.0;
        assertEquals(expected, result);
    }

    @Test
    void testExpression4() {
        String expression = "7 4 - 2 *";
        Expression parsedExpression = ExpressionParser.parse(expression);
        parsedExpression.print();
        double result = parsedExpression.eval("");
        double expected = 6.0;
        assertEquals(expected, result);
    }

    @Test
    void testExpression5() {
        String expression = "5 2 3 * +";
        Expression parsedExpression = ExpressionParser.parse(expression);
        double result = parsedExpression.eval("");
        double expected = 11.0;
        assertEquals(expected, result);
    }

    @Test
    void testExpressionWithVariables() {
        String expression = "x 2 + y *";
        Expression parsedExpression = ExpressionParser.parse(expression);
        double result = parsedExpression.eval("x = 3; y = 4");
        double expected = (3 + 2) * 4;
        assertEquals(expected, result);
    }

    @Test
    void testInsufficientOperands() {
        String expression = "+ 3";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ExpressionParser.parse(expression);
        });
        assertEquals("Недостаточно операндов для операции: +", exception.getMessage());
    }
    
}
