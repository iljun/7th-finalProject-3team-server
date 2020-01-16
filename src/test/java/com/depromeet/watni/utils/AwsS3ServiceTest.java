package com.depromeet.watni.utils;

import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AwsS3ServiceTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AwsS3Service awsS3Service;

    @Test
    public void image_upload() throws IOException {
        String path = "test";

        Resource resource = new ClassPathResource("depromeet.png");
        BufferedImage image = ImageIO.read(resource.getInputStream());
        File file = File.createTempFile("test", "test");

        String imageUrl = this.awsS3Service.upload(path, ContentType.IMAGE_PNG, resource.getFile());
        log.info("AwsS3ServiceTest.image_upload url : {}", imageUrl);
        Assert.assertNotNull(imageUrl);
    }
}
