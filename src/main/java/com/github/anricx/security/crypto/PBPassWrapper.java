package com.github.anricx.security.crypto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.io.IOException;

/**
 * Created by dengt on 2019/8/30
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PBPassWrapper {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String wrap(String plain, String seed) {
        return PBPassWrapper.builder()
                .seed(seed)
                .plain(plain)
                .build()
                .toString();
    }

    public static PBPassWrapper unwrap(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, PBPassWrapper.class);
        } catch (IOException e) {
            return null;
        }
    }

    private String seed;
    private String plain;

    @Override
    public String toString() {
        try {
            return OBJECT_MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // Should Never Happen!
            throw new RuntimeException(e);
        }
    }
}
