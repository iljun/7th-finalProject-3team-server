package com.depromeet.watni.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

@Configuration
public class S3Configuration {

    private final AwsProperties awsProperties;
    public S3Configuration(AwsProperties awsProperties) {
        Assert.notNull(awsProperties.getAccessKey(), "AWS AccessKey is required");
        Assert.notNull(awsProperties.getSecretKey(), "AWS SecretKey is required");
        Assert.notNull(awsProperties.getBucketName(), "AWS S3 bucketName is required");
        Assert.notNull(awsProperties.getRegion(), "AWS S3 region is required");
        this.awsProperties = awsProperties;
    }

    @Bean
    public BasicAWSCredentials awsCredentials() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(awsProperties.getAccessKey(), awsProperties.getSecretKey());
        return basicAWSCredentials;
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(awsProperties.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(this.awsCredentials()))
                .build();
    }
}
