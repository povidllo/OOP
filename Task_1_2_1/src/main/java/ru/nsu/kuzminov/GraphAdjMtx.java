package ru.nsu.kuzminov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Реализация графа с помощью Матрицы смежности.
 */
public class GraphAdjMtx implements GraphInterface {
    int[][] adjMtx;
    int vertexCount;

    /**
     * Конструтор.
     *
     * @param size число вершин.
     */
    public GraphAdjMtx(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Неправильное значение size");
        }
        vertexCount = size;
        adjMtx = new int[size + 1][size + 1];
    }


    /**
     * Добавляет новую вершину.
     *
     * @param vertex - новая вершина.
     */
    @Override
    public void addVertex(int vertex) {
        if (vertex > vertexCount) {
            int[][] newMatrix = new int[vertex + 1][vertex + 1];
            for (int i = 0; i <= vertexCount; i++) {
                System.arraycopy(adjMtx[i], 0, newMatrix[i], 0, vertexCount + 1);
            }
            adjMtx = newMatrix;
            vertexCount = vertex;
        }
    }

    /**
     * Добавляет новую вершину.
     *
     * @param vertex - новая вершина.
     */
    @Override
    public void removeVertex(int vertex) {
        if (vertex > vertexCount) {
            throw new IllegalArgumentException("Нет такой вершины " + vertex);
        }
        for (int i = 1; i <= vertexCount; i++) {
            adjMtx[vertex][i] = 0;
            adjMtx[i][vertex] = 0;
        }
    }

    /**
     * Добавляет ребро.
     *
     * @param from - откуда.
     * @param to   - куда.
     */
    @Override
    public void addEdge(int from, int to) {
        if (from > vertexCount || to > vertexCount) {
            throw new IllegalArgumentException("Нет одной из вершин");
        }
        adjMtx[from][to] = 1;  // Для ориентированного графа только from -> to
    }

    /**
     * Удаляет ребро.
     *
     * @param from - откуда.
     * @param to   - куда.
     */
    @Override
    public void removeEdge(int from, int to) {
        if (from > vertexCount || to > vertexCount) {
            throw new IllegalArgumentException("Нет одной из вершин");
        }
        adjMtx[from][to] = 0;  // Удаляем направленное ребро from -> to
    }

    /**
     * Возвращает список соседей.
     *
     * @param vertex - номер вершины.
     * @return - соседи этой вершины.
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        if (vertex > vertexCount) {
            throw new IllegalArgumentException("Нет такой вершины " + vertex);
        }

        List<Integer> neighbors = new ArrayList<>();
        for (int i = 1; i <= vertexCount; i++) {
            if (adjMtx[vertex][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    /**
     * Возвращает все ребра.
     *
     * @return - все ребра в виде хэш мапа.
     */
    @Override
    public Map<Integer, List<Integer>> allEdges() {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for (int i = 1; i <= vertexCount; i++) {
            List<Integer> to = new ArrayList<>();
            for (int j = 1; j <= vertexCount; j++) {
                if (adjMtx[i][j] == 1) {
                    to.add(j);
                }
            }
            adjList.put(i, to);
        }
        return adjList;
    }

    /**
     * Читает граф из файла.
     * ver1 ver2 - то это ребро.
     * ver1 - это вершина.
     *
     * @param filename - имя файла.
     * @throws IOException - возвращает исключение.
     */
    @Override
    public void readFromFile(String filename) throws IOException {
        BufferedReader buf = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = buf.readLine()) != null) {
            String[] parts = line.split(" ");
            if (parts.length == 1) {
                addVertex(Integer.parseInt(parts[0]));
            } else {
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                addEdge(from, to);
            }
        }
    }

    /**
     * Превращает граф в строку.
     *
     * @return строку.
     */
    @Override
    public String toString() {
        return allEdges().toString();
    }

    /**
     * Сравнивает два графа.
     *
     * @param obj с чем сравниваем.
     * @return true/false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof GraphInterface)) return false;

        return this.allEdges().equals(((GraphInterface) obj).allEdges());
    }

    /**
     * Топологическая сортировка.
     *
     * @return список отсортированных вершин.
     */
    @Override
    public List<Integer> topologicalSort() {
        Map<Integer, Integer> status = new HashMap<>();
        boolean hasCycle = false;
        List<Integer> topoList = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();

        for (int i = 1; i <= vertexCount; i++) {
            if (status.getOrDefault(i, 0) == 0) {
                hasCycle = dfs(i, status, stack);
                if (hasCycle) {
                    return new ArrayList<>();
                }
            }
        }

        while (!stack.isEmpty()) {
            topoList.add(stack.pop());
        }

        return topoList;
    }

    /**
     * DFS.
     *
     * @param vertex - вершина.
     * @param status - статус.
     * @param stack  - стек.
     * @return - true если есть цикл, иначе false.
     */
    private boolean dfs(int vertex, Map<Integer, Integer> status, Stack<Integer> stack) {
        status.put(vertex, 1);

        for (int i = 1; i <= vertexCount; i++) {
            if (adjMtx[vertex][i] == 1) {
                if (status.getOrDefault(i, 0) == 0) {
                    if (dfs(i, status, stack)) {
                        return true;
                    }
                } else if (status.get(i) == 1) {
                    return true; // Цикл найден
                }
            }
        }

        status.put(vertex, 2);
        stack.push(vertex);
        return false;
    }
}
