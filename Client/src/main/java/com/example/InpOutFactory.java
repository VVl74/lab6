package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.nio.ByteBuffer;

public class InpOutFactory {
    ObjectMapper mapper;
    WrapperUtils utils;
    public InpOutFactory() {
        mapper = new ObjectMapper();
        utils = new WrapperUtils(31);
    }

    public ByteBuffer OutputFactory(String input) throws JsonProcessingException {
        input = input.trim();

        if (input.equals("exit")) {
            System.exit(0);
        }
        Wrapper outWrap = new Wrapper();

        int sum = utils.getControlSum(input);
        int hash = utils.makeHash(input);

        outWrap.setZapr(input, sum, hash);

        byte[] jsonByte = mapper.writeValueAsBytes(outWrap);

        return ByteBuffer.wrap(jsonByte);
    }

    public String InputFactory(ByteBuffer buffer) throws IOException {
        if (buffer == null) {
            return "";
        }

        buffer.flip();
        byte[] data = new byte[buffer.limit()];

        buffer.get(data);
        Wrapper prvvod = mapper.readValue(data, Wrapper.class);
        String itog  = prvvod.getZapr();

        int sum = utils.getControlSum(itog);
        int hash = utils.makeHash(itog);

        if (sum != prvvod.getControlSum()) {
            System.out.println("Ошибка в контрольной сумме");
        }
        if (hash != prvvod.getHash()) {
            System.out.println("Ошибка в хеше сумме");
        }

        return itog;
    }
}
