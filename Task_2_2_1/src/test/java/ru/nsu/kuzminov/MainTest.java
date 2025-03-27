package ru.nsu.kuzminov;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class MainTest {

    @Test
    void testMain() throws InterruptedException, IOException, ParseException {
        // Проверяем, что программа завершается без ошибок
        Main.main(new String[] {});
    }
}
