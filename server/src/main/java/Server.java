import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;

public class Server {
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    public static Handler handler;
    static private final int PORT = 19199;

    public Server() {
        loggerBuild("logger/log_server.txt");
        LOGGER.log(Level.INFO,"Сервер запущен");
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(PORT);
                 Socket socket = serverSocket.accept();
                 DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                 DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
                new ClientHandler(socket, inputStream, outputStream );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loggerBuild(String pattern){
        try {
            handler = new FileHandler(pattern);
            handler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(handler);
            LOGGER.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
