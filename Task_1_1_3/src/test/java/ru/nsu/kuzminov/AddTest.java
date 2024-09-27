package ru.nsu.kuzminov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AddTest {
    private ByteArrayOutputStream newOutStream;

    @BeforeEach
    void streamChange() {
        newOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutStream));
    }

    @Test
    void testPrint() {
        Add addTest = new Add (new Number(5), new Number(10));
        addTest.print();

        assertEquals("(5+10)", newOutStream.toString());
    }

    @Test
    void testDerivative() {
        Add addTest = new Add (new Number(5), new Variable("x"));
        Expression e = addTest.derivative("x");
        e.print();
        assertEquals("(0+1)", newOutStream.toString());
    }

    @Test
    void testEvalOne() {
        Add addTest = new Add (new Number(5), new Number(10));
        double testEval = addTest.eval("");
        assertEquals(15, testEval);
    }

    @Test
    void testEvalTwo() {
        Add addTest = new Add (new Number(5), new Variable("x"));
        double testEval = addTest.eval("x = 10");
        assertEquals(15, testEval);
    }
}