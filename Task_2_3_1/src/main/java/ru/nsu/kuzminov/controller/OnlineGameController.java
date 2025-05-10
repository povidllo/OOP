package ru.nsu.kuzminov.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.nsu.kuzminov.game.Game;
import ru.nsu.kuzminov.game.OnlineGameClient;
import ru.nsu.kuzminov.game.OnlineGameHost;
import ru.nsu.kuzminov.utils.ConnectionStatus;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.List;

public class OnlineGameController {
    private Game onlineGame;
    private List<Thread> connectingThread;


    @FXML
    private ChoiceBox<String> playersCountChoose;
    private int playersCount;

    @FXML
    private TextField connectionIpField;

    @FXML
    private TextField portField;

    @FXML
    public void initialize() {
        playersCountChoose.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.playersCount = Integer.parseInt(newValue);
        });
    }


    @FXML
    public void HostGame(ActionEvent event) {
        System.out.println(this.playersCount);
        this.onlineGame = new OnlineGameHost(this.playersCount);
        this.connectingThread = ((OnlineGameHost) this.onlineGame).getConnectionThread();

        if(!tryHosting()) {
            throw new RuntimeException("Can't host the game");
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/nsu/kuzminov/host-menu.fxml"));
            Parent gameRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setRoot(gameRoot);
            stage.sizeToScene();

            HostMenuController hostMenuController = loader.getController();
            hostMenuController.setGame((OnlineGameHost) onlineGame);

            stage.setOnCloseRequest(e -> {
                stopThreads();
                Platform.exit();
                System.exit(0);
            });

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ClientGame(ActionEvent event) throws UnknownHostException {
        String ipText = connectionIpField.getText();
        String portText = portField.getText();

        if (ipText == null || portText == null || ipText.isEmpty() || portText.isEmpty()) {
            throw new IllegalArgumentException("IP и порт должны быть заполнены.");
        }
        InetAddress address = InetAddress.getByName(ipText);
        int port = Integer.parseInt(portText);

        this.onlineGame = new OnlineGameClient(address, port);
        this.connectingThread = ((OnlineGameClient) this.onlineGame).getConnectionThread();

        if(!tryConnect()) {
            throw new RuntimeException("Can't connect to the game");
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/nsu/kuzminov/client-menu.fxml"));
            Parent gameRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setRoot(gameRoot);
            stage.sizeToScene();

            stage.setOnCloseRequest(e -> {
                stopThreads();
                Platform.exit();
                System.exit(0);
            });
            stage.show();

            ClientMenuController controller = loader.getController();
            controller.processClient(stage, (OnlineGameClient)this.onlineGame);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean tryHosting() {
        long seconds = Instant.now().getEpochSecond();
        long between = Instant.now().getEpochSecond();
        OnlineGameHost host = (OnlineGameHost) this.onlineGame;
        while(between - seconds != 30) {
            if (host.getConnectionStatus() == ConnectionStatus.HOSTING) {
                return true;
            }
            between = Instant.now().getEpochSecond();
        }
        return false;
    }

    private boolean tryConnect() {
        long seconds = Instant.now().getEpochSecond();
        long between = Instant.now().getEpochSecond();
        OnlineGameClient host = (OnlineGameClient) this.onlineGame;
        while(between - seconds != 30) {
            if (host.getConnectionStatus() == ConnectionStatus.CONNECTED) {
                return true;
            }
            between = Instant.now().getEpochSecond();
        }
        return false;
    }

    private void stopThreads() {
//        if (connectingThread != null && connectingThread.isAlive()) {
//            connectingThread.interrupt();
//        }
        for(var i : connectingThread) {
            if(i != null && i.isAlive()) {
                i.interrupt();
            }
        }
    }

}