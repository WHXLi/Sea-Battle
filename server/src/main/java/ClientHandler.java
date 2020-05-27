import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class ClientHandler {
    private Server server;
    private Socket socket;

    public ClientHandler(Socket socket, Server server) {
        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            this.socket = socket;
            this.server = server;
            new Thread(() -> {
                try {
                    while (true) {
                        String message = inputStream.readUTF();
                        if (message.startsWith("/")) {
                            if (message.equals("/end")) {
                                server.LOGGER.log(Level.INFO, "Клиент отключен: " + socket.getInetAddress());
                                outputStream.writeUTF("/end");
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
