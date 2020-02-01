package com.depromeet.watni.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Slf4j
public class Base64Decoding {

    public static File decodingToImage(String encodingString, String key) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(encodingString);
        File image = File.createTempFile(key, ".jpg");
        FileUtils.writeByteArrayToFile(image, bytes);
        return image;
    }
}
