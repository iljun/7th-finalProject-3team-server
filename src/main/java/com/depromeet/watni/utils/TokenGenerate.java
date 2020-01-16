package com.depromeet.watni.utils;

import org.springframework.util.StringUtils;

import java.util.UUID;

public class TokenGenerate {

    public static String generateAccessToken() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "").substring(0, 8);
    }

    public static String generateRefreshToken() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
