package ru.nsu.kuzminov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Реализация графа с помощью списка смежности.
 */
public class GraphAdjList implements GraphInterface {

    private Map<Integer, List<Integer>> adjList;

    /**
     * Конструктор.
     */
    public GraphAdjList() {
        adjList = new HashMap<>();
    }

    /**
     * Добавляет новую вершину.
     *
     * @param vertex - новая вершина.
     */
    @Override
    public void addVertex(int vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Удаляет вершину.
     *
     * @param vertex - номер вершины.
     */
    @Override
    public void removeVertex(int vertex) {
        if (!adjList.containsKey(vertex) || adjList.get(vertex) == null) {
            throw new IllegalArgumentException("Такой вершины не существует");
        }

        adjList.remove(vertex);
        for (List<Integer> neighbors : adjList.values()) {
            neighbors.remove(Integer.valueOf(vertex));
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
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            throw new IllegalArgumentException("Одной из вершин не существует");
        }
        List<Integer> neighbors = adjList.get(from);
        if (!neighbors.contains(to)) {
            neighbors.add(to);
        }
    }

    /**
     * Удаляет ребро.
     *
     * @param from - откуда.
     * @param to   - куда.
     */
    @Override
    public void removeEdge(int from, int to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            throw new IllegalArgumentException("Одной из вершин не существует");
        }

        List<Integer> neighbors = adjList.get(from);
        if (neighbors != null) {
            neighbors.remove(Integer.valueOf(to));
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
        if (!adjList.containsKey(vertex) || adjList.get(vertex) == null) {
            throw new IllegalArgumentException("Такой вершины не существует");
        }
        return adjList.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * Возвращает все ребра.
     *
     * @return - все ребра в виде хэш мапа.
     */
    @Override
    public Map<Integer, List<Integer>> allEdges() {
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
        return adjList.toString();
    }

    /**
     * Сравнивает два графа.
     *
     * @param obj с чем сравниваем.
     * @return true/false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GraphInterface)) {
            return false;
        }

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
        boolean hasCycle = false;

        for (Integer vertex : adjList.keySet()) {
            status.put(vertex, 0);
        }


        for (Integer vertex : adjList.keySet()) {
            if (status.get(vertex) == 0) {
                hasCycle = dfs(vertex, status, stack);
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

        for (Integer neighbor : adjList.get(vertex)) {
            if (status.get(neighbor) == 0) {
                if (dfs(neighbor, status, stack)) {
                    return true;
                }
            } else if (status.get(neighbor) == 1) {
                return true;
            }
        }


        status.put(vertex, 2);
        stack.push(vertex);
        return false;
    }

}
