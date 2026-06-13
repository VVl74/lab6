package com.example.utils;

import com.example.Wrapper;
import com.example.WrapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.managers.CommandManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class PackFactory {
    ObjectMapper mapper;
    WrapperUtils utils;
    public PackFactory() {
        mapper = new ObjectMapper();
        utils = new WrapperUtils(31);
    }

    public OutputPack BuildPack(SocketAddress client, CommandManager curCommandManager, String[] parts, String login, String password) {
        ByteArrayOutputStream bufStream = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(bufStream, true);

        if (!parts[0].equals("exit") && !parts[0].equals("save")) {
            curCommandManager.newCommand(parts, out, login, password);
        }

        out.flush();
        String prStr = bufStream.toString();


        ByteBuffer otvet = makeWrap(prStr);

        OutputPack outPack = new OutputPack(otvet, client);

        return outPack;

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

        ByteBuffer ansver = ByteBuffer.wrap(jsonByte);
        return ansver;
    }
}
