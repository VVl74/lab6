package com.example.managers;

import com.example.utils.InputPack;
import com.example.utils.OutputPack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

public class ServerManager {
    Logger logger = LoggerFactory.getLogger(ServerManager.class);
    ServerSocket servSocket;
    ConcurrentHashMap<SocketAddress, Socket> clients = new ConcurrentHashMap<>();

    public ServerManager( int port) throws IOException {
        servSocket = new ServerSocket(port);
        servSocket.setSoTimeout(50);
        logger.info("Сервер слушает TCP-порт {}", port);
    }

    public InputPack receive() throws IOException {
        Socket client;
        try {
            client = servSocket.accept();
        } catch (SocketTimeoutException e) {
            return new InputPack(null , null);
        }

        client.setSoTimeout(5000);
        DataInputStream in = new DataInputStream(client.getInputStream());
        int size = in.readInt();
        if (size < 0 || size > 16384) {
            client.close();
            return new InputPack(null, null);
        }
        byte[] data = new byte[size];
        in.readFully(data);

        SocketAddress addr = client.getRemoteSocketAddress();
        clients.put(addr, client);
        logger.info("ПРИЁМ: получен пакет {} байт от клиента {}", data.length, addr);
        return new InputPack(addr, data);
    }

    public void send(OutputPack pack) throws IOException {
        Socket client = clients.remove(pack.client);
        if (client == null || client.isClosed()) {
            logger.info("ОТПРАВКА: нет соединения с {}", pack.client);
            return;
        }
        ByteBuffer buf = pack.buf;
        buf.rewind();
        byte[] data = new byte[buf.remaining()];
        buf.get(data);
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        out.writeInt(data.length);
        out.write(data);
        out.flush();
        logger.info("ОТПРАВКА: ответ {} байт отправлен клиенту {}", data.length, pack.client);
        client.close();
    }
}
