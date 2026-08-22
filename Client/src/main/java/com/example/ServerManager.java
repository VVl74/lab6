package com.example;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerManager implements ServerManagerInterface {
    DatagramChannel channel;
    InetSocketAddress serverAdress;

    public ServerManager(String host, int port) throws IOException {
        InetAddress ipv4 = resolveIpv4(host);
        serverAdress = new InetSocketAddress(ipv4, port);

        // ipv4, а то localhost может уйти в ipv6
        channel = DatagramChannel.open(StandardProtocolFamily.INET);
        channel.bind(new InetSocketAddress("0.0.0.0", 0));
        channel.configureBlocking(false);

        if (serverAdress.isUnresolved()) {
            System.out.println("[ОШИБКА] Не удалось определить IPv4 сервера '"
                    + host + "'. Проверь домен и подключение к сети ИТМО.");
        } else {
            System.out.println("[СЕТЬ] Адрес сервера: " + serverAdress);
            System.out.println("[СЕТЬ] Локальный UDP-сокет: " + channel.getLocalAddress());
        }
    }

    private static InetAddress resolveIpv4(String host) throws IOException {
        if (host == null || host.isBlank() || "localhost".equalsIgnoreCase(host)) {
            return InetAddress.getByName("127.0.0.1");
        }
        InetAddress fallback = null;
        for (InetAddress addr : InetAddress.getAllByName(host)) {
            if (addr instanceof Inet4Address) {
                if (!addr.isLoopbackAddress()) {
                    return addr;
                }
                fallback = addr;
            }
        }
        if (fallback != null) {
            return fallback;
        }
        throw new IOException("Нет IPv4-адреса для хоста '" + host + "'");
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
        int sent = channel.send(buf, serverAdress);
        if (sent == 0) {
            System.out.println("[ОТПРАВКА] Пакет не ушёл (send вернул 0), повторю");
            buf.rewind();
            sent = channel.send(buf, serverAdress);
        }
        System.out.println("[ОТПРАВКА] Отправлен пакет " + sent + "/" + size + " байт на сервер " + serverAdress);
    }
}
