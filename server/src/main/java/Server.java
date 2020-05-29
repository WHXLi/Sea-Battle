
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
        Thread serverWork = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT);
                 Socket socket = serverSocket.accept();
                 DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                 DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
                handler = new FileHandler("logger/log_server.txt");
                handler.setFormatter(new SimpleFormatter());
                LOGGER.addHandler(handler);
                LOGGER.setUseParentHandlers(false);
                LOGGER.log(Level.INFO, "Сервер запущен");
                while (true) {
                    String message = inputStream.readUTF();
                    if (message.equals("/connected")) {
                        LOGGER.log(Level.INFO, "Клиент подключен: " + socket.getInetAddress());
                    }
                    if (message.equals("/end")) {
                        outputStream.writeUTF("/end");
                        LOGGER.log(Level.INFO,"Клиент отключен: " + socket.getInetAddress());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverWork.start();
    }
}
