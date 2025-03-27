package ru.nsu.kuzminov;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void testMain() throws InterruptedException {
        // Проверяем, что программа завершается без ошибок
        String[] args = {};
        Main.main(args);
    }
}
