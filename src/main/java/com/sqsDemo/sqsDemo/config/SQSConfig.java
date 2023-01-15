package com.sqsDemo.sqsDemo.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.sqsDemo.sqsDemo.config.awsProperties.AwsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SQSConfig {

    @Autowired
    private AwsProperties awsProperties;


    @Bean
    public AmazonSQSClient createSQSClient() {

        var awsCredentials = new BasicAWSCredentials(awsProperties.getCredentials().getAccesskey(), awsProperties.getCredentials().getSecretkey());
        AmazonSQS sqsClient = AmazonSQSClient.builder().withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

//        Create Queue
        String queue = createQueue(sqsClient);
        return (AmazonSQSClient) sqsClient;
    }
    private String createQueue(AmazonSQS sqsClient) {
        var createQueueRequest = new CreateQueueRequest(awsProperties.getCredentials().getQueuename());
        CreateQueueResult queueResult = sqsClient.createQueue(createQueueRequest);
        System.out.println(queueResult.getQueueUrl());
        return queueResult.getQueueUrl();
    }
}
