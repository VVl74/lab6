package com.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RetryDecor implements ServerManagerInterface {
    private ServerManagerInterface serverManager;
    private int attempt = 10;
    private long retryDelay = 1000;
    private long nattempt = 5;
    ByteBuffer lastsendbuffer;
    public RetryDecor(ServerManagerInterface serverManager, int attempt) {
        this.serverManager = serverManager;
        this.attempt = attempt;
    }

    @Override
    public void send(ByteBuffer buf) throws IOException, InterruptedException {
        for (int i=0; i<attempt; i++) {
            try {
                lastsendbuffer = buf;
                buf.rewind();
                serverManager.send(buf);
                return;
            } catch (Exception e) {
                System.out.println("Отправка не удалась, попытка: " + (i + 1));
            }

            Thread.sleep(retryDelay);
        }
    }

    @Override
    public ByteBuffer receive(ByteBuffer vvodBuf) throws IOException, InterruptedException {
        for(int i=1; i<=attempt; i++) {
            vvodBuf.clear();

            ByteBuffer res = serverManager.receive(vvodBuf);

            if (res != null) {
                return res;
            }

            serverManager.send(lastsendbuffer);
            Thread.sleep(retryDelay);
        }
        return null;
    }
}
