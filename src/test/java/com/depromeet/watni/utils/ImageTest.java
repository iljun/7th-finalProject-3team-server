package com.depromeet.watni.utils;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Base64;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ImageTest {

    @Test
    public void encodingAndDecoding() throws IOException {
        Resource resource = new ClassPathResource("depromeet.png");

        byte[] fileContent = FileUtils.readFileToByteArray(resource.getFile());
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        System.out.println(encodedString);
    }
}
