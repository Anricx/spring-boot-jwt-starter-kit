package com.github.anricx.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by dengt on 2019/8/30
 */
@Setter
@Getter
@AllArgsConstructor
@Builder
public class AccessToken {

    private String type;
    private String value;

    @Override
    public String toString() {
        return String.format("%s %s", type, value);
    }
}
