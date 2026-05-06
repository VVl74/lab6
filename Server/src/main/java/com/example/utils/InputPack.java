package com.example.utils;

import java.net.SocketAddress;

public class InputPack {
    public SocketAddress client;
    public byte[] data;
    public InputPack(SocketAddress inpAdres, byte[] inData) {
        client = inpAdres;
        data = inData;
    }
}
