package com.gilberto009199.anotai.desafio_backend.aws.services;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String   s3BucketName;

    public S3Service(S3Client s3Client, String s3BucketName){
        this.s3Client = s3Client;
        this.s3BucketName = s3BucketName;
    }
}
