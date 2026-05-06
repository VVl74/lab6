package com.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.utils.Wrapper;

import java.io.IOException;

public class Deserializer {
    Logger logger = LoggerFactory.getLogger(Deserializer.class);
    ObjectMapper mapper;
    WrapperUtils utils;
    public Deserializer() {
        mapper = new ObjectMapper();
        utils = new WrapperUtils(31);
    }

    public String[] deserialize(InputPack pack) {
        String input = null;

        Wrapper prvvod = null;

        try {
            prvvod = mapper.readValue(pack.data, Wrapper.class);
        } catch (IOException e) {
            return null;
        }

        input  = prvvod.getZapr();
        int sum = utils.getControlSum(input);
        int hash = utils.makeHash(input);

        if (prvvod.getHash() != hash) {
            logger.info("hash не совпал");
        }
        if (prvvod.getControlSum() != sum) {
            logger.info("контрольная сумма не совпала");
        }

        if (input == null) {
            return null;
        }

        String[] parts = input.split(" ");

        return parts;
    }
}
