package com.sqsDemo.sqsDemo.service;


import com.sqsDemo.sqsDemo.config.awsProperties.AwsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


@Service
public class MessageService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private AwsProperties awsProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    public void sendMessage(final String message) {
        jmsTemplate.send(awsProperties.getCredentials().getQueuename(), session -> session.createTextMessage(message));
    }

}
