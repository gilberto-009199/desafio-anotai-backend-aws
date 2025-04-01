package com.gilberto009199.anotai.desafio_backend.aws.consumer;

import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gilberto009199.anotai.desafio_backend.aws.services.S3Service;
import com.gilberto009199.anotai.desafio_backend.aws.services.SQSService;
import com.gilberto009199.anotai.desafio_backend.entities.CategoryEntity;
import com.gilberto009199.anotai.desafio_backend.entities.ProductEntity;

import io.awspring.cloud.sqs.listener.MessageListener;
import io.awspring.cloud.sqs.listener.SqsMessageListenerContainer;
import jakarta.annotation.PostConstruct;

@Component
public class CatalogConsumer implements MessageListener<Object>{

    private final SqsMessageListenerContainer<Object> listener;
    private final ObjectMapper objectMapper;
    private final SQSService sqsService;
    private final S3Service s3Service;
    
    

    public CatalogConsumer( S3Service s3Service,
                            SQSService sqsService,
                            ObjectMapper objectMapper){
        this.s3Service      = s3Service;
        this.sqsService     = sqsService;
        this.objectMapper   = objectMapper;

        this.listener = sqsService.listener(this);
    }

    @PostConstruct
    public void init(){
        this.listener.start();
    }

    @Override
    public void onMessage(Message<Object> messageRaw) {
        System.out.println("onMessage: ");

        var message = extract(messageRaw);

        System.out.println("processType: "+ message.processType());
        System.out.println("Payload: "+ message.payload());

        CatalogStrategy.process(s3Service, message);

        sqsService.deleteMessage(message.receiptHandle());
        
    }



    public MessageQueue<?> extract(Message<Object> message){
        System.out.println(message.getPayload());

        Map<String, Object> headers = message.getHeaders();
        
        String messageId = (String) headers.get("Sqs_MessageId");
        String receiptHandle = (String) headers.get("Sqs_ReceiptHandle");
    
        MessageQueueEnum processType = headers.containsKey(SQSService.IDENTIFICATION_PROCESS_TYPE)
                ? MessageQueueEnum.valueOf(headers.get(SQSService.IDENTIFICATION_PROCESS_TYPE).toString())
                : MessageQueueEnum.OTHER;
    
        MessageQueue<?> extractedMessage = new MessageQueue<>(
            messageId,
            receiptHandle,
            processType,
            headers,
            deserilizePayload(processType, message.getPayload().toString())
        );
    
        return extractedMessage;

    }

    private Object deserilizePayload(MessageQueueEnum processType, String payload) {
        
        try {
            return switch (processType) {
                case OTHER -> payload;
                case NEW_PRODUCT, UPDATE_PRODUCT, REMOVE_PRODUCT -> objectMapper.readValue(payload, ProductEntity.class);
                case NEW_CATEGORY, UPDATE_CATEGORY, REMOVE_CATEGORY -> objectMapper.readValue(payload, CategoryEntity.class);
            };
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao desserializar o JSON", e);
        }
    }
}