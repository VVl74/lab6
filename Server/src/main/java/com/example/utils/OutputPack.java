package com.example.utils;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class OutputPack {
    public ByteBuffer buf;
    public SocketAddress client;

    public OutputPack(ByteBuffer inByte, SocketAddress inClient) {
        buf = inByte;
        client = inClient;
    }
}
