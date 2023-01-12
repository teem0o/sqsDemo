package com.sqsDemo.sqsDemo.service;

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

    @Value("${cloud.aws.credntials.queue.name}")
    private String queueName;

    @Test
    void sendMessage() {
        jmsTemplate.send(queueName, session -> session.createTextMessage("test1"));
        jmsTemplate.send(queueName, session -> session.createTextMessage("test2"));
//        jmsTemplate.send(queueName, session -> session.createTextMessage("test3"));

    }
}