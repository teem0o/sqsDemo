package com.sqsDemo.sqsDemo.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SQSConfig {

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @Value("${cloud.aws.credntials.queue.name}")
    private String queueName;
    @Value("${cloud.aws.credentials.access-key}")
    private String ACCESS_KEY;
    @Value("${cloud.aws.credentials.secret-key}")
    private String SECRET_KEY;


    @Bean
    public AmazonSQSClient createSQSClient() {

        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonSQS sqsClient = AmazonSQSClient.builder().withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

//        Create Queue
        String queue = createQueue(sqsClient);
        return (AmazonSQSClient) sqsClient;
    }
    private String createQueue(AmazonSQS sqsClient) {
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
        CreateQueueResult queueResult = sqsClient.createQueue(createQueueRequest);
        System.out.println(queueResult.getQueueUrl());
        return queueResult.getQueueUrl();
    }
}
