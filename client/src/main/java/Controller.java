import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Controller implements Initializable {
    public TextField nameText;
    public Button PVEButton;
    public Button PVPButton;
    private Socket socket;
    private final String IP = "5.101.133.67";
    private final int PORT = 19199;

    public void startPVE(ActionEvent actionEvent) {
        connect(IP, PORT);
        sendMessage("/modePVE");
    }

    public void startPVP(ActionEvent actionEvent) {
        connect(IP, PORT);
        sendMessage("modePVP");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            Stage stage = (Stage) nameText.getScene().getWindow();
            stage.setOnCloseRequest(windowEvent -> {
                if (socket != null && !socket.isClosed()) {
                    sendMessage("/end");
                }
            });
        });
    }

    private void connect(String IP, int PORT) {
        try (Socket socket = new Socket(IP, PORT)) {
            new Thread(() -> {
                while (true) {
                    if (takeMessage().startsWith("/")) {
                        if (takeMessage().equals("/end")) {
                            break;
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try (DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            outputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String takeMessage() {
        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
            return inputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
