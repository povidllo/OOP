package ru.nsu.kuzminov;

import ru.nsu.kuzminov.utils.GameStatus;
import ru.nsu.kuzminov.game.Game;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class OnlineGameWrapperOld {
    private int playerCount;
    private Game curGame;
    private int playerId;
    private Status status;
    private int[] scores;
    private GameStatus[] playersStatuses;
    private List<Socket> clientSockets; // Для хранения соединений с клиентами
    private ServerSocket serverSocket;
    private boolean started;


    private final ReentrantLock lock = new ReentrantLock();
    private final Condition startCondition = lock.newCondition();

    private enum Status {
        CLIENT, HOST, UNKNOWN;
    }

    private enum Connection {
        UNKNOWN, HOSTED, CONNECTED
    }

    private boolean IsNeedStart() throws InterruptedException {
        lock.lock();
        try {
            while (!started) {
                startCondition.await();
            }
            return true;
        } finally {
            lock.unlock();

        }
    }

    public void setStarted(boolean newVal) {
        lock.lock();
        try {
            this.started = newVal;
            if (newVal) {
                startCondition.signal();  // Будим только ОДИН поток
            }
        } finally {
            lock.unlock();
        }
    }

    public OnlineGameWrapperOld(int playerCount) {
        this.playerCount = playerCount;
        this.curGame = new Game(10, 10, 5, 5);
        this.status = Status.UNKNOWN;
        this.scores = new int[this.playerCount];
        this.started = false;
        this.clientSockets = new ArrayList<>();

    }

    public OnlineGameWrapperOld() {
        this.curGame = new Game(10, 10, 5, 5);
        this.status = Status.UNKNOWN;
        this.started = false;
    }

    // Метод для запуска хоста
    public void runHost(int port, int playerCount) {
        this.playerCount = playerCount;
        this.playerId = 0;
        this.status = Status.HOST;
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен на порту " + port);
            int clientPlayerId = this.playerId + 1;

            while (this.clientSockets.size() < this.playerCount - 1) {
                Socket client = serverSocket.accept();
                this.clientSockets.add(client);
                System.out.println("Игрок подключен: " + client.getInetAddress());

                clientPlayerId++;
            }

            HostToClientsProcessing();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для запуска клиента
    public void runClient(InetAddress address, int port) {
        try {
            this.status = Status.CLIENT;
            Socket socket = new Socket(address, port);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            // Ожидаем получение данных от сервера
            this.playerCount = inputStream.readInt();
            this.playerId = inputStream.readInt();
            System.out.printf("Current player ID: " + this.playerId);
            this.scores = new int[this.playerCount];

            int a = inputStream.readInt();

            System.out.println("close");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Метод для отправки данных клиентам
    private void HostToClientsProcessing() {
        try {
            ObjectOutputStream[]  outstrems = new ObjectOutputStream[this.clientSockets.size()];
            for(int i = 0; i < this.clientSockets.size(); i++) {
                Socket client = clientSockets.get(i);
                outstrems[i] = new ObjectOutputStream(client.getOutputStream());
                outstrems[i].writeInt(this.playerCount);  // Количество игроков
                outstrems[i].writeInt(i + 1);  // ID игрока
                outstrems[i].writeInt(1);
                outstrems[i].flush();  // Важно вызвать flush() для отправки данных
            }
//            if(IsNeedStart()) {
                for (Socket client : this.clientSockets) {
                    ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                    outputStream.writeInt(1);
                    outputStream.flush();
                }
//            }
            for(int i = 0; i < this.clientSockets.size(); i++) {
                outstrems[i].writeInt(1);
                outstrems[i].flush();
            }
            System.out.println("close");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

