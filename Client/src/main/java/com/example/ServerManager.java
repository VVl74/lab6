package com.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class ServerManager implements ServerManagerInterface {
    InetSocketAddress serverAdress;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ServerManager(String host, int port) throws IOException {
        InetAddress ipv4 = resolveIpv4(host);
        serverAdress = new InetSocketAddress(ipv4, port);

        if (serverAdress.isUnresolved()) {
            System.out.println("[ОШИБКА] Не удалось определить IPv4 сервера '"
                    + host + "'. Проверь домен и подключение к сети ИТМО.");
        } else {
            System.out.println("[СЕТЬ] Адрес сервера: " + serverAdress);
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

    private void connect() throws IOException {
        closeQuietly();
        socket = new Socket();
        socket.connect(serverAdress, 5000);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public ByteBuffer receive(ByteBuffer vvodBuf) throws IOException, InterruptedException {
        if (socket == null || socket.isClosed()) {
            return null;
        }
        try {
            socket.setSoTimeout(5000);
            int size = in.readInt();
            if (size < 0 || size > vvodBuf.capacity()) {
                throw new IOException("неверный размер пакета: " + size);
            }
            byte[] data = new byte[size];
            in.readFully(data);
            System.out.println("[ПРИЁМ] Получен ответ " + size + " байт от " + serverAdress);
            vvodBuf.clear();
            vvodBuf.put(data);
            closeQuietly();
            return vvodBuf;
        } catch (SocketTimeoutException e) {
            System.out.println("[ПРИЁМ] Сервер не ответил за 5000 мс");
            closeQuietly();
            return null;
        }
    }

    public void send(ByteBuffer buf) throws IOException {
        buf.rewind();
        byte[] data = new byte[buf.remaining()];
        buf.get(data);
        connect();
        out.writeInt(data.length);
        out.write(data);
        out.flush();
        System.out.println("[ОТПРАВКА] Отправлен пакет " + data.length + " байт на сервер " + serverAdress);
    }

    private void closeQuietly() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ignored) {
        }
        socket = null;
        in = null;
        out = null;
    }
}
