package com.hhhirokunnn.classroom_community_server.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@Configuration
@PropertySource("classpath:aws.properties")
data class AwsConfig(
    @Value("\${cloud.aws.region.static}") val region: String,
    @Value("\${cloud.aws.bucket.name}") val bucketName: String
) {
    @Value("\${cloud.aws.credentials.accessKey}")
    lateinit var accessKey: String
    operator fun component3() = accessKey

    @Value("\${cloud.aws.credentials.secretKey}")
    lateinit var secretKey: String
    operator fun component4() = secretKey

    @Value("\${cloud.aws.bucket.key.step}")
    lateinit var stepBucketKey: String

    @Value("\${cloud.aws.bucket.key.article}")
    lateinit var articleBucketKey: String
}
