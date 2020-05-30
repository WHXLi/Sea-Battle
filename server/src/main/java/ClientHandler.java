import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;

public class ClientHandler {
    private static InetAddress clientIP;

    public ClientHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        clientIP = socket.getInetAddress();
            try {
                while (true) {
                    String message = inputStream.readUTF();
                    if (message.equals("/connected")) {
                        Server.LOGGER.log(Level.INFO, "Клиент подключен: " + clientIP);
                    }
                    if (message.equals("/end")) {
                        outputStream.writeUTF("/end");
                        throw new RuntimeException();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                Server.LOGGER.log(Level.INFO, "Клиент отключен: " + clientIP);
            }
    }
}
