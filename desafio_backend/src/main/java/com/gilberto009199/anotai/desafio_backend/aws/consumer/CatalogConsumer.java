package com.gilberto009199.anotai.desafio_backend.aws.consumer;

import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.gilberto009199.anotai.desafio_backend.aws.services.S3Service;
import com.gilberto009199.anotai.desafio_backend.aws.services.SQSService;

import io.awspring.cloud.sqs.listener.MessageListener;
import io.awspring.cloud.sqs.listener.SqsMessageListenerContainer;
import jakarta.annotation.PostConstruct;

@Component
public class CatalogConsumer implements MessageListener<Object>{

    private final S3Service s3Service;
    private final SQSService sqsService;
    private final SqsMessageListenerContainer<Object> listener;

    public CatalogConsumer(S3Service s3Service, SQSService sqsService){
        this.s3Service = s3Service;
        this.sqsService = sqsService;
        
        this.listener = sqsService.listener(this);
    }

    @PostConstruct
    public void init(){
        this.listener.start();
    }

    @Override
    public void onMessage(Message<Object> message) {
        System.out.println("onMessage: ");
        System.out.println(message.getPayload());

        Map<String, Object> headers = message.getHeaders();
        // Id of Message
        String receiptHandle = (String) headers.get("Sqs_ReceiptHandle");



        sqsService.deleteMessage(receiptHandle);
    }

    public MessageQueue<?> extract(Message<Object> message){
        System.out.println(message.getPayload());

        Map<String, Object> headers = message.getHeaders();
        
        String messageId = (String) headers.get("Sqs_MessageId");
        String receiptHandle = (String) headers.get("Sqs_ReceiptHandle");

        return null;
    }

}