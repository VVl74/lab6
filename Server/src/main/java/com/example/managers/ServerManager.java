package com.example.managers;

import com.example.utils.InputPack;
import com.example.utils.OutputPack;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerManager {
    DatagramChannel servChannel;
    ByteBuffer buffer;

    public ServerManager( int port) throws IOException {
        servChannel = DatagramChannel.open();
        servChannel.bind(new InetSocketAddress(port));
        servChannel.configureBlocking(false);
        buffer = ByteBuffer.allocate(16384);
    }

    public InputPack receive() throws IOException {
        buffer.clear();
        SocketAddress client = servChannel.receive(buffer);

        if (client == null) {
            InputPack retPack1 = new InputPack(null , null);
            return retPack1;
        }

        buffer.flip();

        byte[] data = new byte[buffer.limit()];
        buffer.get(data);

        InputPack retPack = new InputPack(client, data);
        return retPack;
    }

    public void send(OutputPack pack) throws IOException {
        servChannel.send(pack.buf, pack.client);
    }
}
