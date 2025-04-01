package com.gilberto009199.anotai.desafio_backend.aws.services;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.awspring.cloud.sqs.listener.MessageListener;
import io.awspring.cloud.sqs.listener.SqsMessageListenerContainer;

import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;


@Service
public class SQSService {
    
    private final String sqsQueueUrl;
    private final SqsClient sqsClient;
    private final SqsAsyncClient sqsAsyncClient;

    public SQSService(SqsClient sqsClient,
                      String sqsQueueUrl,
                      SqsAsyncClient sqsAsyncClient){
        this.sqsClient      = sqsClient;
        this.sqsQueueUrl    = sqsQueueUrl;
        this.sqsAsyncClient = sqsAsyncClient;
    }

    public SendMessageResponse sendMessage(String messageBody) { return sendMessage(null, messageBody);   }
    public SendMessageResponse sendMessage( Map<String, String> headers, String messageBody) {
        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(sqsQueueUrl) 
                .messageBody(messageBody) 
                .messageAttributes(convertHeaders(headers)) 
                .build();

        return sqsClient.sendMessage(request);
    }


    public CompletableFuture<SendMessageResponse> sendMessageAsync(String messageBody) { return sendMessageAsync(null, messageBody);  }
    public CompletableFuture<SendMessageResponse> sendMessageAsync( Map<String, String> headers, String messageBody) {
        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(sqsQueueUrl)
                .messageBody(messageBody)
                .messageAttributes(convertHeaders(headers)) 
                .build();

        return sqsAsyncClient.sendMessage(request)
        .thenApply(response -> {
            System.out.println("Mensagem enviada com ID: " + response.messageId());
            return response;
        });
    }


    public CompletableFuture<Void> deleteMessage(String receiptHandle) {
        DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                .queueUrl(sqsQueueUrl)
                .receiptHandle(receiptHandle)
                .build();

        return sqsAsyncClient.deleteMessage(deleteRequest)
        .thenAccept(response -> {
            System.out.println("Mensagem Removida: " + response);
        });
    }


    public SqsMessageListenerContainer<Object> listener(MessageListener<Object> messageHandler) {
        var container = SqsMessageListenerContainer
            .builder()
            .sqsAsyncClient(sqsAsyncClient)
            .queueNames(sqsQueueUrl)
            // Listen in queue
            .messageListener(messageHandler)
            .build();        

        return container;
    }

    private Map<String, MessageAttributeValue> convertHeaders(Map<String, String> headers) {
        
        if(headers == null) return Map.of(); 

        return headers.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> MessageAttributeValue.builder()
                                .dataType("String")
                                .stringValue(entry.getValue())
                                .build()
                ));
    }

}
