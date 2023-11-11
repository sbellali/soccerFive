package com.sbellali.soccerFive.configuration;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.s3.uri}")
    private String awsUri;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(this.awsRegion))
                .endpointOverride(URI.create(awsUri))
                .forcePathStyle(true)
                .build();

    }
}
