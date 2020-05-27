import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;

public class Server {
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    public static Handler handler;

    public Server() {
        final int PORT = 19199;
        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket socket = serverSocket.accept()) {
            handler = new FileHandler("logger/log_server.txt");
            handler.setFormatter(new SimpleFormatter());
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(handler);
            LOGGER.log(Level.INFO, "Сервер запущен");
            while (true) {
                LOGGER.log(Level.INFO, "Клиент подключен: " + socket.getInetAddress());
                new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
