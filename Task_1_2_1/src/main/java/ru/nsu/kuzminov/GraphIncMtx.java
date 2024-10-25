package ru.nsu.kuzminov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Реализация графа с помощью Матрицы инцидентности
 */
public class GraphIncMtx implements GraphInterface {
    int[][] IncMtx;
    private int verCount;
    private int edgeCount;

    /**
     * Конструктор.
     *
     * @param newVerCount - число вершин.
     */
    public GraphIncMtx(int newVerCount) {
        if (newVerCount <= 0) {
            throw new IllegalArgumentException("Некорректное количество вершин.");
        }
        this.verCount = newVerCount;
        this.edgeCount = 0;
        this.IncMtx = new int[newVerCount + 1][0];
    }

    /**
     * Добавляет новую вершину.
     *
     * @param vertex - новая вершина.
     */
    @Override
    public void addVertex(int vertex) {
        if (vertex > verCount) {
            int[][] newMatrix = new int[vertex + 1][edgeCount];
            for (int i = 0; i <= verCount; i++) {
                System.arraycopy(IncMtx[i], 0, newMatrix[i], 0, edgeCount);
            }
            IncMtx = newMatrix;
            verCount = vertex;
        }
    }

    /**
     * Удаляет вершину.
     *
     * @param vertex - номер вершины.
     */
    @Override
    public void removeVertex(int vertex) {
        if (vertex > verCount) {
            throw new IllegalArgumentException("Вершина не существует.");
        }

        int[][] newMatrix = new int[verCount][edgeCount];
        int l = 0;
        for (int i = 0; i <= verCount; i++) {
            if (i != vertex) {
                System.arraycopy(IncMtx[i], 0, newMatrix[l++], 0, edgeCount);
            }
        }
        IncMtx = newMatrix;
        verCount--;
    }

    /**
     * Добавляет ребро.
     *
     * @param from - откуда.
     * @param to   - куда.
     */
    @Override
    public void addEdge(int from, int to) {
        if (from > verCount || to > verCount) {
            throw new IllegalArgumentException("Вершина вне диапазона.");
        }

        int[][] newMatrix = new int[verCount + 1][edgeCount + 1];
        for (int i = 0; i <= verCount; i++) {
            System.arraycopy(IncMtx[i], 0, newMatrix[i], 0, edgeCount);
        }

        // Для ориентированного графа добавляем ребро только от `from` к `to`
        newMatrix[from][edgeCount] = 1;
        newMatrix[to][edgeCount] = -1;

        IncMtx = newMatrix;
        edgeCount++;
    }

    /**
     * Удаляет ребро.
     *
     * @param from - откуда.
     * @param to   - куда.
     */
    @Override
    public void removeEdge(int from, int to) {
        if (from > verCount || to > verCount) {
            throw new IllegalArgumentException("Вершина вне диапазона.");
        }

        for (int i = 0; i < edgeCount; i++) {
            if (IncMtx[from][i] == 1 && IncMtx[to][i] == -1) {
                int[][] newMatrix = new int[verCount + 1][edgeCount - 1];
                for (int j = 0; j <= verCount; j++) {
                    System.arraycopy(IncMtx[j], 0, newMatrix[j], 0, i);
                    if (i < edgeCount - 1) {
                        System.arraycopy(IncMtx[j], i + 1, newMatrix[j], i, edgeCount - i - 1);
                    }
                }
                IncMtx = newMatrix;
                edgeCount--;
                return;
            }
        }
    }

    /**
     * Возвращает список соседей.
     *
     * @param vertex - номер вершины.
     * @return - соседи этой вершины.
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        if (vertex > verCount) {
            throw new IllegalArgumentException("Вершина не существует.");
        }
        for (int i = 0; i < edgeCount; i++) {
            if (IncMtx[vertex][i] == 1) {
                for (int j = 0; j <= verCount; j++) {
                    if (IncMtx[j][i] == -1) {
                        neighbors.add(j);
                    }
                }
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
        Map<Integer, List<Integer>> edges = new HashMap<>();
        for (int i = 1; i <= verCount; i++) {
            edges.put(i, getNeighbors(i));
        }
        return edges;
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
        List<Integer> topoList = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        boolean Cycle = false;

        for (int i = 1; i <= verCount; i++) {
            status.put(i, 0);
        }

        for (int i = 1; i <= verCount; i++) {
            if (status.get(i) == 0) {
                Cycle = dfs(i, status, stack);
                if (Cycle) {
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

        for (int i = 0; i < edgeCount; i++) {
            if (IncMtx[vertex][i] == 1) {
                for (int j = 1; j <= verCount; j++) {
                    if (IncMtx[j][i] == -1) {
                        if (status.get(j) == 0) {
                            if (dfs(j, status, stack)) {
                                return true;
                            }
                        } else if (status.get(j) == 1) {
                            return true;
                        }
                    }
                }
            }
        }

        status.put(vertex, 2);
        stack.push(vertex);
        return false;
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
}
