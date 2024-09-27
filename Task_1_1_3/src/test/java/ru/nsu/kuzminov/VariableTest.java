package ru.nsu.kuzminov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class VariableTest {
    private ByteArrayOutputStream newOutStream;

    @BeforeEach
    void streamChange() {
        newOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutStream));
    }

    @Test
    void testPrint() {
        Variable var = new Variable("x");
        var.print();
        assertEquals("x", newOutStream.toString());
    }

    @Test
    void testDerivativeByCurVar() {
        Variable var = new Variable("x");
        Expression e = var.derivative("x");
        e.print();

        assertEquals("1", newOutStream.toString());
    }

    @Test
    void testDerivativeByAnotherVar() {
        Variable var = new Variable("x");
        Expression e = var.derivative("y");
        e.print();

        assertEquals("0", newOutStream.toString());
    }

    @Test
    void testEvalExeption() {
        Variable var = new Variable("x");
        assertThrows(RuntimeException.class, () -> var.eval("u = 10"));

    }

    @Test
    void testEvalOne() {
        Variable var = new Variable("x");
        double value = var.eval("x = 10");
        assertEquals(10, value);
    }

    @Test
    void testEvalTwo() {
        Variable var = new Variable("x");
        double value = var.eval("x = 10; y = 20");
        assertEquals(10, value);
    }

    @Test
    void testEvalThree() {
        Variable var = new Variable("x");
        double value = var.eval("y = 10; x = 20");
        assertEquals(20, value);
    }

    @Test
    void testEvalFour() {
        Variable var = new Variable("y");
        double value = var.eval("x = 10; y = 20; u = 10");
        assertEquals(20, value);
    }
}