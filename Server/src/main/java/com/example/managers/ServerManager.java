package com.example.managers;

import com.example.utils.InputPack;
import com.example.utils.OutputPack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerManager {
    Logger logger = LoggerFactory.getLogger(ServerManager.class);
    DatagramChannel servChannel;
    ByteBuffer buffer;

    public ServerManager( int port) throws IOException {
        servChannel = DatagramChannel.open();
        servChannel.bind(new InetSocketAddress(port));
        servChannel.configureBlocking(false);
        buffer = ByteBuffer.allocate(16384);
        logger.info("Сервер слушает UDP-порт {}", port);
    }

    public InputPack receive() throws IOException {
        buffer.clear();
        SocketAddress client = servChannel.receive(buffer);

        if (client == null) {
            // данных в канале нет (неблокирующий режим)
            InputPack retPack1 = new InputPack(null , null);
            return retPack1;
        }

        buffer.flip();

        byte[] data = new byte[buffer.limit()];
        buffer.get(data);

        logger.info("ПРИЁМ: получен пакет {} байт от клиента {}", data.length, client);

        InputPack retPack = new InputPack(client, data);
        return retPack;
    }

    public void send(OutputPack pack) throws IOException {
        int size = pack.buf.remaining();
        servChannel.send(pack.buf, pack.client);
        logger.info("ОТПРАВКА: ответ {} байт отправлен клиенту {}", size, pack.client);
    }
}
