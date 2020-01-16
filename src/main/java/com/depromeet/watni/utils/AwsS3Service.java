package com.depromeet.watni.utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.depromeet.watni.config.AwsProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@Slf4j
@Transactional
public class AwsS3Service {

    private final AmazonS3 amazonS3Client;
    private final AwsProperties awsProperties;
    public AwsS3Service(AmazonS3 amazonS3Client,
                        AwsProperties awsProperties) {
        this.amazonS3Client = amazonS3Client;
        this.awsProperties = awsProperties;
    }

    public String upload(String path, ContentType contentType, File file) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType.toString());
            objectMetadata.setContentLength(file.length());
            objectMetadata.setHeader("filename", file.getName());

            long timestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }

            if (!path.endsWith("/")) {
                path = path + "/";
            }
            PutObjectRequest putObjectRequest = new PutObjectRequest(awsProperties.getBucketName() + path,  timestamp + file.getName(), file);
            putObjectRequest.setMetadata(objectMetadata);
            this.amazonS3Client.putObject(putObjectRequest);

            URL imageUrl = amazonS3Client.getUrl(awsProperties.getBucketName() + path, timestamp + file.getName());
            return imageUrl.toExternalForm();
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            log.error("AwsS3Service.upload AmazonServiceException : {}", e.getErrorMessage());
        } catch (SdkClientException e) {
            e.printStackTrace();
            log.error("AwsS3Service.upload SdkClientException : {}", e.getMessage());
        }
        return null;
    }
}
