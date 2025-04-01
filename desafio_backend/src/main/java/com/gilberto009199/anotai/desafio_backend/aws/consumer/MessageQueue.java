package com.gilberto009199.anotai.desafio_backend.aws.consumer;

import java.util.Map;

public record MessageQueue<T>(
    String messageId,
    String receiptHandle,
    MessageQueueEnum processType,
    Map<String, Object> headers,
    T payload
) {

}
