package com.sqsDemo.sqsDemo.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.*;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
//import com.gkatzioura.sqstesting.listeners.SQSListener;
import com.sqsDemo.sqsDemo.config.awsProperties.AwsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.MessageListener;


@Configuration
public class JMSSQSConfig {

    @Autowired
    private AwsProperties awsProperties;


    @Autowired
    private MessageListener sqsListener;

    @Bean
    public DefaultMessageListenerContainer jmsListenerContainer() {

//        SQSConnectionFactory sqsConnectionFactory = SQSConnectionFactory.builder().withAWSCredentialsProvider(new DefaultAWSCredentialsProviderChain()).withEndpoint(endpoint).withAWSCredentialsProvider(awsCredentialsProvider).withNumberOfMessagesToPrefetch(10).build();

        var sqsConnectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                AmazonSQSClientBuilder.standard()
                        .withRegion(awsProperties.getRegion())
                        .withCredentials(awsCredentialsProvider));

        DefaultMessageListenerContainer dmlc = new DefaultMessageListenerContainer();
        dmlc.setConnectionFactory(sqsConnectionFactory);
        dmlc.setDestinationName(awsProperties.getCredentials().getQueuename());

        dmlc.setMessageListener(sqsListener);

        return dmlc;
    }


    @Bean
    public JmsTemplate createJMSTemplate() {

//        SQSConnectionFactory sqsConnectionFactory = SQSConnectionFactory.builder().withAWSCredentialsProvider(awsCredentialsProvider).withEndpoint(endpoint).withNumberOfMessagesToPrefetch(10).build();

        var sqsConnectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                AmazonSQSClientBuilder.standard()
                        .withRegion(awsProperties.getRegion())
                        .withCredentials(awsCredentialsProvider));

        JmsTemplate jmsTemplate = new JmsTemplate(sqsConnectionFactory);
        jmsTemplate.setDefaultDestinationName(awsProperties.getCredentials().getQueuename());
        jmsTemplate.setDeliveryPersistent(false);

        return jmsTemplate;
    }

    private final AWSCredentialsProvider awsCredentialsProvider = new AWSCredentialsProvider() {
        @Override
        public AWSCredentials getCredentials() {
            return new BasicAWSCredentials(awsProperties.getCredentials().getAccesskey(), awsProperties.getCredentials().getSecretkey());
        }

        @Override
        public void refresh() {

        }
    };

}
