package com.example.linkfamily.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
public class MinioConfig {
    
    @Value("${minio.s3-endpoint}")
    private String s3Endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(s3Endpoint)
                .credentials(accessKey, secretKey)
                .region("eu-west-1")
                .build();
    }
}
