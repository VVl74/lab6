package com.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerManager {
    DatagramChannel channel;
    InetSocketAddress serverAdress;

    public ServerManager(int port) throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        serverAdress = new InetSocketAddress("localhost", 12345);
    }

    public ByteBuffer recive(ByteBuffer vvodBuf) throws IOException, InterruptedException {
        long otpravTime = System.currentTimeMillis();
        long timeOut = 5000;
        while(true) {

            java.net.SocketAddress from  = channel.receive(vvodBuf);

            if (from != null) {
                break;
            }

            if (System.currentTimeMillis() - otpravTime > timeOut) {
                System.out.println("Сервер не ответил");
                return null;
            }

            Thread.sleep(100);
        }
        return vvodBuf;
    }

    public void send(ByteBuffer buf) throws IOException {
        channel.send(buf, serverAdress);
    }
}
