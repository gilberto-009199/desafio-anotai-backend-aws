package com.gilberto009199.anotai.desafio_backend.config;

import java.net.URI;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;

@Configuration
public class AWSConfig {

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKeyId;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretAccessKey;

    @Value("${aws.s3.name}")
    private String bucketName;

    @Value("${aws.sqs.name}")
    private String sqsName;


    private StaticCredentialsProvider awsCredentialsProvider() {
        return StaticCredentialsProvider.create(
            AwsBasicCredentials.create(accessKeyId, secretAccessKey)
        );
    }

    @Bean
    public String sqsQueueUrl(SqsClient sqsClient) {
        GetQueueUrlResponse queueUrlResponse = sqsClient.getQueueUrl(req -> req.queueName(sqsName));
        return queueUrlResponse.queueUrl();
    }

    @Bean
    public String s3BucketName() {
        return bucketName;
    }

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(awsCredentialsProvider())
                .build();
    }

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(awsCredentialsProvider())
                .build();
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(awsCredentialsProvider())
                .build();
    }
}