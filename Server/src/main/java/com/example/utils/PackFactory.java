package com.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.managers.CommandManager;
import com.example.utils.OutputPack;
import com.example.utils.Wrapper;

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

    public OutputPack BuildPack(SocketAddress client, CommandManager curCommandManager, String[] parts) {
        ByteArrayOutputStream bufStream = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(bufStream, true);

        try {
            if (!parts[0].equals("exit") && !parts[0].equals("save")) {
                curCommandManager.newCommand(parts, out);
            }
        } catch (Exception e) {
            // ans = ("Ошибка, команда не выполнена");
        } finally {
            // System.setOut(old);
        }

        out.flush();
        String prStr = bufStream.toString();

        Wrapper res = new Wrapper();
        int sum = utils.getControlSum(prStr);
        int hash = utils.makeHash(prStr);
        res.setZapr(prStr, sum, hash);

        byte[] jsonByte;

        try {
            jsonByte = mapper.writeValueAsBytes(res);
        } catch (Exception e) {
            e.printStackTrace();
            jsonByte = "ошибка сериализации".getBytes();
        }

        ByteBuffer otvet = ByteBuffer.wrap(jsonByte);

        OutputPack outPack = new OutputPack(otvet, client);

        // System.setOut(old);

        return outPack;
    }
}
