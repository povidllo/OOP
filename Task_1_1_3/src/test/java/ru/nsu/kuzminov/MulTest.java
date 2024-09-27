package ru.nsu.kuzminov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MulTest {
    private ByteArrayOutputStream newOutStream;

    @BeforeEach
    void streamChange() {
        newOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutStream));
    }

    @Test
    void testPrint() {
        Mul mulTest = new Mul (new Number(5), new Number(10));
        mulTest.print();

        assertEquals("(5*10)", newOutStream.toString());
    }

    @Test
    void testDerivative() {
        Mul addTest = new Mul (new Number(5), new Variable("x"));
        Expression e = addTest.derivative("x");
        e.print();
        assertEquals("((0*x)+(5*1))", newOutStream.toString());
    }

    @Test
    void testEvalOne() {
        Mul addTest = new Mul (new Number(5), new Number(10));
        double testEval = addTest.eval("");
        assertEquals(50, testEval);
    }

    @Test
    void testEvalTwo() {
        Mul addTest = new Mul (new Number(5), new Variable("x"));
        double testEval = addTest.eval("x = 10");
        assertEquals(50, testEval);
    }
}