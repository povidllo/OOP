package ru.nsu.kuzminov.game;

import ru.nsu.kuzminov.utils.Cell;
import ru.nsu.kuzminov.utils.ConnectionStatus;
import ru.nsu.kuzminov.utils.GameStatus;
import ru.nsu.kuzminov.utils.InGameStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.nsu.kuzminov.utils.Cell.CellType.*;

public class OnlineGameClient extends Game {

    private ConnectionStatus connectionStatus;
    private InGameStatus inGameStatus;
    private List<Thread> connectionThread;
    private int playerId;
    private AtomicInteger[] scores;
    private int playerCount;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public final Lock lock = new ReentrantLock();
    public final Condition gameStartCondition = lock.newCondition();

    public final Lock recieveLock = new ReentrantLock();
    public final Condition recieveCondition = recieveLock.newCondition();
    private AtomicBoolean flagRecieve;
    Socket socket;

    /**
     * Конструктор игры для хоста.
     */
    public OnlineGameClient(InetAddress address, int port) {
        super(20, 20, 10, 10);
        this.connectionStatus = ConnectionStatus.UNKNOWN;
        this.inGameStatus = InGameStatus.WAITING;
        this.flagRecieve = new AtomicBoolean(false);
        createClientSocket(address, port);
        this.connectionThread = new ArrayList<>();
        this.connectionThread.add(new Thread(this::runClient));
        this.connectionThread.getFirst().start();

    }

    private void createClientSocket(InetAddress address, int port) {
        try {
            this.connectionStatus = ConnectionStatus.CONNECTING;
            this.socket = new Socket(address, port);
            System.out.println("Клиент подключен к хосту");
            this.connectionStatus = ConnectionStatus.CONNECTED;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void runClient() {
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            this.playerCount = inputStream.readInt();
            this.scores = new AtomicInteger[this.playerCount];
            for (int i = 0; i < playerCount; i++) {
                this.scores[i] = new AtomicInteger(0);
            }
            this.playerId = inputStream.readInt();
            System.out.println("player id " + this.playerId);

            boolean a = inputStream.readBoolean();
            System.out.println(a);
            System.out.println("Игра началась");
            lock.lock();
            gameStartCondition.signal();
            lock.unlock();
            this.inGameStatus = InGameStatus.GAMING;

            while(true) {
                boolean hasUpdate = inputStream.readBoolean();
                if(!hasUpdate) {
                    continue;
                }
                recieveLock.lock();
                for(int i = 0; i < this.playerCount; i++) {
                    int id = inputStream.readInt();
                    int score = inputStream.readInt();
                    scores[id].set(score);
                }
                recieveLock.unlock();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Метод для движения змейки по полю.
     */
    @Override
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
            Thread sendApplesThread = new Thread(this::sendApples);
            sendApplesThread.start();
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
    
    private void sendApples() {
        try {
            recieveLock.lock();
            System.out.println("send");
            outputStream.writeInt(this.playerId);
            outputStream.writeInt(this.score);
            outputStream.flush();
            System.out.println("sended");
            recieveLock.unlock();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public AtomicInteger[] getScores() {
        return scores;
    }
}
