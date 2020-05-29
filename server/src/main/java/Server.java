
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;

public class Server {
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    public static Handler handler;
    static private final int PORT = 19199;
    static private InetAddress clientIP;

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
                        clientIP = socket.getInetAddress();
                        throw new RuntimeException();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (RuntimeException e){
                LOGGER.log(Level.INFO,"Клиент отключен: " + clientIP);
            }
        });
        serverWork.start();
    }
}
