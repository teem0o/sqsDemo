package com.sqsDemo.sqsDemo.service;

import com.sqsDemo.sqsDemo.config.awsProperties.AwsProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageServiceTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private AwsProperties awsProperties;

    @Test
    void sendMessage() {
        jmsTemplate.send(awsProperties.getCredentials().getQueuename(), session -> session.createTextMessage("test1"));
        jmsTemplate.send(awsProperties.getCredentials().getQueuename(), session -> session.createTextMessage("test2"));
        jmsTemplate.send(awsProperties.getCredentials().getQueuename(), session -> session.createTextMessage("test3"));

    }
}