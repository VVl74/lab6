package com.example.utils;

import com.example.Wrapper;
import com.example.WrapperUtils;
import com.example.managers.CommandManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class PackFactory {
    private final ObjectMapper mapper;
    private final WrapperUtils utils;
    private final CommandManager commandManager;

    public PackFactory(CommandManager commandManager) {
        this.commandManager = commandManager;
        mapper = new ObjectMapper();
        utils = new WrapperUtils(31);
    }

    public OutputPack buildPack(SocketAddress client, ParsedRequest request) {
        ByteArrayOutputStream bufStream = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(bufStream, true);

        String[] parts = request.getParts();
        if (!parts[0].equals("exit") && !parts[0].equals("save")) {
            commandManager.newCommand(parts, out, request.getLogin(), request.getPassword());
        }

        out.flush();
        ByteBuffer otvet = makeWrap(bufStream.toString());
        return new OutputPack(otvet, client);
    }

    public ByteBuffer makeWrap(String prStr) {
        Wrapper res = new Wrapper();
        int sum = utils.getControlSum(prStr);
        int hash = utils.makeHash(prStr);
        res.setZapr(prStr, sum, hash, "", "");

        byte[] jsonByte;
        try {
            jsonByte = mapper.writeValueAsBytes(res);
        } catch (Exception e) {
            e.printStackTrace();
            jsonByte = "ошибка сериализации".getBytes();
        }
        return ByteBuffer.wrap(jsonByte);
    }
}
