package ru.nsu.kuzminov;

import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void testMain() throws InterruptedException {
        // Проверяем, что программа завершается без ошибок
        String[] args = {};
        Main.main(args);
    }
} 