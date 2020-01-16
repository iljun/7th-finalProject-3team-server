package com.depromeet.watni.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EncryptionTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void 암호화() {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(System.getProperty("encryptionKey"));

        String enc = pbeEnc.encrypt("");
        log.info("enc = " + enc);
    }
}
