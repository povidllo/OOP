package ru.nsu.kuzminov;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Интерфейс для реализации неориентированного графа, без кратных ребер.
 */
public interface GraphInterface {
    /**
     * Добавляет новую вершину.
     *
     * @param vertex - новая вершина.
     */
    void addVertex(int vertex);

    /**
     * Удаляет вершину.
     *
     * @param vertex - номер вершины.
     */
    void removeVertex(int vertex);

    /**
     * Добавляет ребро.
     *
     * @param from - откуда.
     * @param to   - куда.
     */
    void addEdge(int from, int to);

    /**
     * Удаляет ребро.
     *
     * @param from - откуда.
     * @param to   - куда.
     */
    void removeEdge(int from, int to);

    /**
     * Возвращает список соседей.
     *
     * @param vertex - номер вершины.
     * @return - соседи этой вершины.
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * Возвращает все ребра.
     *
     * @return - все ребра в виде хэш мапа.
     */
    Map<Integer, List<Integer>> allEdges();

    /**
     * Читает граф из файла.
     * ver1 ver2 - то это ребро.
     * ver1 - это вершина.
     *
     * @param filename - имя файла.
     * @throws IOException - возвращает исключение.
     */
    void readFromFile(String filename) throws IOException;

    /**
     * Превращает граф в строку.
     *
     * @return строку.
     */
    String toString();

    /**
     * Сравнивает два графа.
     *
     * @param obj с чем сравниваем.
     * @return true/false.
     */
    boolean equals(Object obj);

    /**
     * Топологическая сортировка.
     *
     * @return список отсортированных вершин.
     */
    List<Integer> topologicalSort();
}
