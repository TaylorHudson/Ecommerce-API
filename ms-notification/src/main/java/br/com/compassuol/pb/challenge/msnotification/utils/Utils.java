package br.com.compassuol.pb.challenge.msnotification.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
    }

    public static String mapToString(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T mapToClass(String json, Class<T> objectClass) throws JsonProcessingException {
        return MAPPER.readValue(json, objectClass);
    }

}