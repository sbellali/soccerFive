package com.sbellali.soccerFive.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void putObject(String key, byte[] file) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(this.bucketName)
                        .key(key)
                        .build();
        this.s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file));
    }

    public byte[] getObject(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                        .bucket(this.bucketName)
                        .key(key)
                        .build();
        ResponseInputStream<GetObjectResponse> res = this.s3Client.getObject(getObjectRequest);
        try {
            return res.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteObject(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                        .bucket(this.bucketName)
                        .key(key)
                        .build();
        this.s3Client.deleteObject(deleteObjectRequest);
    }

}
