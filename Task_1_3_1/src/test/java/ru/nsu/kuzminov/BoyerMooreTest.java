package ru.nsu.kuzminov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoyerMooreTest {

    @Test
    public void testWithSubstring() throws IOException {
        File testFile = new File("testfile.txt");
        String content = "абракадабра";
        Files.write(Paths.get(testFile.toURI()), content.getBytes());

        String pattern = "бра";

        List<Integer> result = BoyerMoore.find(testFile.getAbsolutePath(), pattern);

        assertEquals(2, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(8));

        testFile.delete();
    }

    @Test
    public void testWithoutSubstring() throws IOException {
        File testFile = new File("testfile.txt");
        String content = "абракадабра";
        Files.write(Paths.get(testFile.toURI()), content.getBytes());

        String pattern = "t";

        List<Integer> result = BoyerMoore.find(testFile.getAbsolutePath(), pattern);

        assertEquals(0, result.size());

        testFile.delete();
    }

    @Test
    public void testEmptyFile() throws IOException {
        File testFile = new File("testfile.txt");
        String content = "";
        Files.write(Paths.get(testFile.toURI()), content.getBytes());

        String pattern = "t";

        List<Integer> result = BoyerMoore.find(testFile.getAbsolutePath(), pattern);

        assertEquals(0, result.size());


        testFile.delete();
    }

    @Test
    public void testEmptySubstring() throws IOException {
        File testFile = new File("testfile.txt");
        String content = "dsfas";
        Files.write(Paths.get(testFile.toURI()), content.getBytes());

        String pattern = "";

        List<Integer> result = BoyerMoore.find(testFile.getAbsolutePath(), pattern);

        assertEquals(0, result.size());


        testFile.delete();
    }

    @Test
    public void testLargeFile() throws IOException {
        File testFile = new File("testfile.txt");
        StringBuilder put = new StringBuilder();
        put.append("dsaf".repeat(50000));
        put.append("t");
        put.append("dsaf".repeat(50000));
        Files.write(Paths.get(testFile.toURI()), put.toString().getBytes());


        String pattern = "t";

        List<Integer> result = BoyerMoore.find(testFile.getAbsolutePath(), pattern);

        assertEquals(1, result.size());


        testFile.delete();
    }

}