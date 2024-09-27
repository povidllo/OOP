package ru.nsu.kuzminov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DivTest {
    private ByteArrayOutputStream newOutStream;

    @BeforeEach
    void streamChange() {
        newOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutStream));
    }

    @Test
    void testPrint() {
        Div mulTest = new Div(new Number(5), new Number(10));
        mulTest.print();

        assertEquals("(5/10)", newOutStream.toString());
    }

    @Test
    void testDerivative() {
        Div addTest = new Div(new Number(5), new Variable("x"));
        Expression e = addTest.derivative("x");
        e.print();
        assertEquals("(((0*x)+(5*1))/(x*x))", newOutStream.toString());
    }

    @Test
    void testEvalOne() {
        Div addTest = new Div(new Number(5), new Number(0));
        assertThrows(ArithmeticException.class, () -> addTest.eval(""));
    }

    @Test
    void testEvalTwo() {
        Div addTest = new Div(new Number(5), new Variable("x"));
        double testEval = addTest.eval("x = 10");
        assertEquals(0.5, testEval);
    }
}