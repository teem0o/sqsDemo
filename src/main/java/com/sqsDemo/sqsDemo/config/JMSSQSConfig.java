package com.sqsDemo.sqsDemo.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.*;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
//import com.gkatzioura.sqstesting.listeners.SQSListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.MessageListener;


@Configuration
public class JMSSQSConfig {

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @Value("${cloud.aws.credntials.queue.name}")
    private String queueName;

    @Value("${cloud.aws.credentials.access-key}")
    private String ACCESS_KEY;
    @Value("${cloud.aws.credentials.secret-key}")
    private String SECRET_KEY;

    @Value("${cloud.aws.region.static}")
    private String REGION;
    @Autowired
    private MessageListener sqsListener;

    @Bean
    public DefaultMessageListenerContainer jmsListenerContainer() {

//        SQSConnectionFactory sqsConnectionFactory = SQSConnectionFactory.builder().withAWSCredentialsProvider(new DefaultAWSCredentialsProviderChain()).withEndpoint(endpoint).withAWSCredentialsProvider(awsCredentialsProvider).withNumberOfMessagesToPrefetch(10).build();

        SQSConnectionFactory sqsConnectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                AmazonSQSClientBuilder.standard()
                        .withRegion(REGION)
                        .withCredentials(awsCredentialsProvider));

        DefaultMessageListenerContainer dmlc = new DefaultMessageListenerContainer();
        dmlc.setConnectionFactory(sqsConnectionFactory);
        dmlc.setDestinationName(queueName);

        dmlc.setMessageListener(sqsListener);

        return dmlc;
    }


    @Bean
    public JmsTemplate createJMSTemplate() {

//        SQSConnectionFactory sqsConnectionFactory = SQSConnectionFactory.builder().withAWSCredentialsProvider(awsCredentialsProvider).withEndpoint(endpoint).withNumberOfMessagesToPrefetch(10).build();

        SQSConnectionFactory sqsConnectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                AmazonSQSClientBuilder.standard()
                        .withRegion(REGION)
                        .withCredentials(awsCredentialsProvider));

        JmsTemplate jmsTemplate = new JmsTemplate(sqsConnectionFactory);
        jmsTemplate.setDefaultDestinationName(queueName);
        jmsTemplate.setDeliveryPersistent(false);

        return jmsTemplate;
    }

    private final AWSCredentialsProvider awsCredentialsProvider = new AWSCredentialsProvider() {
        @Override
        public AWSCredentials getCredentials() {
            return new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        }

        @Override
        public void refresh() {

        }
    };

}
