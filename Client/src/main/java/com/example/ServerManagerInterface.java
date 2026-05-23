package com.example;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface ServerManagerInterface {
    void send(ByteBuffer buf) throws IOException, InterruptedException;
    ByteBuffer receive(ByteBuffer buffer) throws IOException, InterruptedException;
}
