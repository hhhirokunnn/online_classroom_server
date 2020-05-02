package com.hhhirokunnn.classroom_community_server.configs

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:aws.properties")
class MyApplicationConfig {

    @Bean
    fun amazonS3Client(@Value("cloud.aws.region.static") region: String,
                       @Value("cloud.aws.credentials.accessKey") accessKey: String,
                       @Value("cloud.aws.credentials.secretKey") secretKey: String): AmazonS3
        = AmazonS3ClientBuilder
        .standard()
        .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey))).build()
}
