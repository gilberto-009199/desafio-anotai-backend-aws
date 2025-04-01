package com.gilberto009199.anotai.desafio_backend.aws.consumer;

public enum MessageQueueEnum {
    
    OTHER,

    NEW_PRODUCT, UPDATE_PRODUCT, REMOVE_PRODUCT,

    NEW_CATEGORY, UPDATE_CATEGORY, REMOVE_CATEGORY;

    @Override
    public String toString() {
        return name();
    }
}
