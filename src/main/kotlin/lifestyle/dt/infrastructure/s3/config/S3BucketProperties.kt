package lifestyle.dt.infrastructure.s3.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(value = "cloud.aws.s3")
class S3BucketProperties(
    val bucket: String,
    val url: String,
    val defaultUrl: String
)