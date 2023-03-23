package co.ke.personal.shopfilestorage.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class S3Config {
    @Value("${aws.s3.buckets.shopfilestorage.name}")
    private String bucketName;

    @Value("${aws.s3.buckets.shopfilestorage.accessKey}")
    private String accessKey;

    @Value("${aws.s3.buckets.shopfilestorage.secret}")
    private String secret;

    @Value("${aws.s3.buckets.shopfilestorage.region}")
    private String region;

    @Bean
    public AmazonS3 generateS3Client() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secret);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
