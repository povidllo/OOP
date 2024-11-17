package ru.nsu.kuzminov;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, реализующий алгоритм Бойера Мура для поиска подстроки.
 */
public class BoyerMoore {
    /**
     * Основной метод.
     *
     * @param fileName  - имя файла.
     * @param substring - подстрока.
     * @return возвращает индексы для подстрок в файле.
     * @throws IOException - если есть ошибки с открытием и чтением файла.
     */
    public static List<Integer> find(String fileName, String substring) throws IOException {
        List<Integer> result = new ArrayList<>();
        int substringLen = substring.length();

        if (substringLen == 0) {
            return result;
        }

        Map<Character, Integer> shiftTable = SubstringCharTable(substring);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),
                StandardCharsets.UTF_8))) {
            StringBuilder buffer = new StringBuilder();
            int globalIndex = 0;
            int bytesRead;

            char[] dataBlock = new char[1024];
            while ((bytesRead = reader.read(dataBlock)) != -1) {
                buffer.append(dataBlock, 0, bytesRead);

                while (buffer.length() >= substringLen) {
                    int matchIndex = searchInBuffer(buffer, substring, shiftTable);

                    if (matchIndex != -1) {
                        result.add(globalIndex + matchIndex);
                        buffer.delete(0, matchIndex + 1);
                        globalIndex += matchIndex + 1;
                    } else {
                        globalIndex += buffer.length() - substringLen + 1;
                        buffer = new StringBuilder(buffer.substring(buffer.length() - substringLen + 1));
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Этот метод создает хэш таблицу для сдвигов.
     *
     * @param substring - искомая подстрока.
     * @return хэш таблицу со здвигами.
     */
    private static Map<Character, Integer> SubstringCharTable(String substring) {
        Map<Character, Integer> table = new HashMap<>();
        int length = substring.length();

        for (int i = 0; i < length - 1; i++) {
            table.put(substring.charAt(i), length - i - 1);
        }
        return table;
    }

    /**
     * Этот метод производит поиск в заданном блоке данных.
     *
     * @param buffer     - строка, в которой ищем.
     * @param substring  - подстрока, которую ищем.
     * @param shiftTable - таблица смещений.
     * @return возвращает индекс, если нашел, иначе -1.
     */
    private static int searchInBuffer(StringBuilder buffer, String substring, Map<Character, Integer> shiftTable) {
        int bufferLength = buffer.length();
        int patternLength = substring.length();

        int offset = 0;
        while (offset <= bufferLength - patternLength) {
            int j = patternLength - 1;

            while (j >= 0 && buffer.charAt(offset + j) == substring.charAt(j)) {
                j--;
            }

            if (j < 0) {
                return offset;
            }

            char mismatchChar = buffer.charAt(offset + j);
            int shift = shiftTable.getOrDefault(mismatchChar, patternLength);
            offset += Math.max(1, shift - (patternLength - 1 - j));
        }
        return -1;
    }
}
