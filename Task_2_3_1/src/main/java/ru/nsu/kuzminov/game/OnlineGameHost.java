package ru.nsu.kuzminov.game;

import ru.nsu.kuzminov.utils.Cell;
import ru.nsu.kuzminov.utils.ConnectionStatus;
import ru.nsu.kuzminov.utils.GameStatus;
import ru.nsu.kuzminov.utils.InGameStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.nsu.kuzminov.utils.Cell.CellType.*;


public class OnlineGameHost extends Game {

    private ConnectionStatus connectionStatus;
    private InGameStatus inGameStatus;
    private final List<Thread> connectionThread;

    private final int playerCount;
    private ServerSocket serverSocket;
    private final AtomicInteger[] scores;
    private final AtomicBoolean flagSend;

    public final Lock lock = new ReentrantLock();
    public final Condition gameStatusCondition = lock.newCondition();
    public final Lock sendLock = new ReentrantLock();
    public final Condition sendCondition = sendLock.newCondition();

    /**
     * Конструктор игры для хоста.
     */
    public OnlineGameHost(int playerCount) {
        super(20, 20, 10, 10);
        this.connectionStatus = ConnectionStatus.UNKNOWN;
        this.inGameStatus = InGameStatus.WAITING;
        this.playerCount = playerCount;
        this.scores = new AtomicInteger[this.playerCount];
        for (int i = 0; i < playerCount; i++) {
            scores[i] = new AtomicInteger(0);
        }

        this.flagSend = new AtomicBoolean(false);

        createHostSocket();
        this.connectionThread = new ArrayList<>();
        for(int i = 1; i < playerCount; i++) {
            int connectedPlayerId = i;
            connectionThread.add(new Thread(() -> runHost(connectedPlayerId)));
            connectionThread.getLast().start();
        }
    }

    private void createHostSocket() {
        try {
            this.connectionStatus = ConnectionStatus.CREATING_HOST;
            InetAddress localHost = InetAddress.getLocalHost();
            int port = 0;

            this.serverSocket = new ServerSocket(port, 50, localHost);

            System.out.println("Сервер запущен на с адресом " + serverSocket);
            this.connectionStatus = ConnectionStatus.HOSTING;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void runHost(int playerId) {
        try {
            Socket client = serverSocket.accept();
            System.out.println("Игрок подключен: " + client.getInetAddress());
            HostToClientsProcessing(client, playerId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void HostToClientsProcessing(Socket client, int playerID) {
        try {
            ObjectOutputStream outStreams = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            outStreams.writeInt(this.playerCount);
            outStreams.writeInt(playerID);
            outStreams.flush();

            lock.lock();
            while (inGameStatus == InGameStatus.WAITING) {
                System.out.println("Ожидание изменения состояния игры...");
                gameStatusCondition.await();
            }
            lock.unlock();

            outStreams.writeBoolean(true);
            System.out.println("Игра началась");
            outStreams.flush();

            Thread recieveThread = new Thread(() -> recieveApples(inputStream));
            connectionThread.add(recieveThread);
            recieveThread.start();
            while(true) {
                sendLock.lock();
                while(!flagSend.get()) {
                    sendCondition.await();
                }
                flagSend.set(false);
                outStreams.writeBoolean(true);
                for(int i = 0; i < playerCount; i++) {
                    outStreams.writeInt(i);
                    outStreams.writeInt(scores[i].get());
                    outStreams.flush();
                }
                sendLock.unlock();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void recieveApples(ObjectInputStream inputStream) {
        try {
            while(true) {
                int playerNumber = inputStream.readInt();
                int playerScore = inputStream.readInt();

                sendLock.lock();
                try {
                    scores[playerNumber].set(playerScore);
                    flagSend.set(true);
                    sendCondition.signalAll();
                } finally {
                    sendLock.unlock();
                }

                System.out.println("apple " + playerScore);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    /**
     * Метод для движения змейки по полю.
     */
    public void move() {

        if (!nextDirection.isEmpty()) {
            direction = nextDirection.removeFirst();
        }

        Cell head = snake.peekLast();
        int x = head.getX();
        int y = head.getYcord();

        int newX = x;
        int newY = y;

        switch (direction) {
            case UP -> newY--;
            case DOWN -> newY++;
            case LEFT -> newX--;
            case RIGHT -> newX++;
            default -> {
                return;
            }
        }
        if (newX == -1 || newX == width || newY == -1 || newY == height
                || grid[newX][newY].getType() == SNAKE_BODY) {
            setStatus(GameStatus.LOOSE);
            return;
        }
        if (grid[newX][newY].getType() == APPLE) {
            snake.addLast(grid[newX][newY]);
            grid[newX][newY].setType(SNAKE_BODY);
            if (snake.size() == width * height) {
                setStatus(GameStatus.WIN);
            }
            this.score++;
            sendLock.lock();
            try {
                scores[0].set(this.score);
                flagSend.set(true);
                sendCondition.signalAll();
            } finally {
                sendLock.unlock();
            }

            System.out.printf("\rscore: " + score);
            setApples();

        } else {
            snake.addLast(grid[newX][newY]);
            grid[newX][newY].setType(SNAKE_BODY);

            Cell tail = snake.removeFirst();
            grid[tail.getX()][tail.getYcord()].setType(GRID);
        }
    }


    public List<Thread> getConnectionThread() {
        return connectionThread;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public void setStartGame() {
        this.inGameStatus = InGameStatus.GAMING;
    }

    public AtomicInteger[] getScores() {
        return scores;
    }
}
