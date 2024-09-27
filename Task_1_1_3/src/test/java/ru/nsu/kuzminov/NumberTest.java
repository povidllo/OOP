package ru.nsu.kuzminov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class NumberTest {
    private ByteArrayOutputStream newOutStream;

    @BeforeEach
    void streamChange() {
        newOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutStream));
    }

    @Test
    void testNonNegativeNumber() {
        Number num = new Number(10);

        num.print();
        assertEquals("10", newOutStream.toString());
    }

    @Test
    void testNegativeNumber() {
        Number num = new Number(-10);

        num.print();
        assertEquals("-10", newOutStream.toString());
    }

    @Test
    void testDerivative() {
        Number num = new Number(10);
        Expression newNum = num.derivative("x");
        newNum.print();
        assertEquals("0", newOutStream.toString());
    }

    @Test
    void testEval() {
        Number num = new Number(10);
        double returned = num.eval("x = 10");
        assertEquals(returned, 10);
    }
}
