package org.projetperso.crypto.glue.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.util.List;

public final class Utils {

    private Utils() { throw new IllegalStateException("Utility class"); }

    public static <T> List<T> retrieve(final MockHttpServletResponse response, final Class<T> clazz, final Class<T[]> clazzes) throws UnsupportedEncodingException, JsonProcessingException {
        final var content=response.getContentAsString();
        try{
            return List.of(retrieve(content,clazz));
        }catch(final MismatchedInputException e){
            return List.of(retrieve(content,clazzes));
        }
    }

    public static <T> T retrieve(final MockHttpServletResponse response, final Class<T> clazz) throws UnsupportedEncodingException, JsonProcessingException {
        final var content=response.getContentAsString();
        return retrieve(content,clazz);
    }

    private static <T> T retrieve(final String jsonResponse, final Class<T> clazz) throws  JsonProcessingException {
        final var mapper= new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return mapper.readValue(jsonResponse,clazz);
    }
}
