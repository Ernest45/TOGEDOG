package togedog.server.global.imageS3x;

//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "s3")
public class S3Config {

////    @Value("${cloud.aws.credentials.accessKey}")
//    private String accessKey;
//
////    @Value("${cloud.aws.credentials.secretKey}")
//    private String secretKey;
//
////    @Value("${cloud.aws.region.static}")
//    private String region;
//
//    @Bean
//    public AmazonS3 amazonS3Client() {
//        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//
//        return AmazonS3ClientBuilder
//                .standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(region)
//                .build();
//    }
}
