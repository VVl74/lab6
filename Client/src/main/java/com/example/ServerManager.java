package com.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerManager implements ServerManagerInterface {
    DatagramChannel channel;
    InetSocketAddress serverAdress;

    public ServerManager(String host, int port) throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        serverAdress = new InetSocketAddress(host, port);

        // проверяем, что имя сервера вообще резолвится в IP
        if (serverAdress.isUnresolved()) {
            System.out.println("[ОШИБКА] Не удалось определить IP сервера '"
                    + host + "'. Проверь домен и подключение к сети ИТМО.");
        } else {
            System.out.println("[СЕТЬ] Адрес сервера: " + serverAdress);
        }
    }

    public ByteBuffer receive(ByteBuffer vvodBuf) throws IOException, InterruptedException {
        long otpravTime = System.currentTimeMillis();
        long timeOut = 5000;

        System.out.println("[ПРИЁМ] Ожидаем ответ от сервера " + serverAdress);

        while(true) {

            java.net.SocketAddress from  = channel.receive(vvodBuf);

            if (from != null) {
                int size = vvodBuf.position();
                System.out.println("[ПРИЁМ] Получен ответ " + size + " байт от " + from
                        + " (ждали " + (System.currentTimeMillis() - otpravTime) + " мс)");
                break;
            }

            if (System.currentTimeMillis() - otpravTime > timeOut) {
                System.out.println("[ПРИЁМ] Сервер не ответил за " + timeOut + " мс");
                return null;
            }

            Thread.sleep(10);
        }
        return vvodBuf;
    }

    public void send(ByteBuffer buf) throws IOException {
        buf.rewind();
        int size = buf.remaining();
        channel.send(buf, serverAdress);
        System.out.println("[ОТПРАВКА] Отправлен пакет " + size + " байт на сервер " + serverAdress);
    }
}
