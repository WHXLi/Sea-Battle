import com.sun.security.ntlm.Server;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    public TextField nameText;
    public Button PVEButton;
    public Button PVPButton;
    private final String IP = "5.101.133.67";
    private final int PORT = 19199;

    public void startPVE() {
        connect(IP, PORT);
    }

    public void startPVP() {
        connect(IP, PORT);
    }

    private void connect(String IP, int PORT) {
        Thread clientWork = new Thread(() -> {
            try (Socket socket = new Socket(IP, PORT);
                 DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                 DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
                outputStream.writeUTF("/connected");
                while (true) {
                    String message = inputStream.readUTF();
                    if (message.equals("/end")) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        clientWork.start();
    }

    public void exit(Socket socket, DataOutputStream outputStream){
        Stage stage = (Stage) nameText.getScene().getWindow();
        stage.setOnCloseRequest((WindowEvent e) -> {
            if (socket != null && !socket.isClosed()){
                try {
                    outputStream.writeUTF("/end");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
