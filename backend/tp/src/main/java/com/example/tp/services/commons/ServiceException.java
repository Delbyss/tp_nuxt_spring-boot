package com.example.tp.services.commons;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ServiceException extends RuntimeException {

    @Getter
    private List<String> causesMessage = new ArrayList<>();

    public ServiceException(String message) {
        super(message);
        this.causesMessage = List.of(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.causesMessage = extractCausesMessages(cause);
    }

    private List<String> extractCausesMessages(Throwable cause) {
        if (cause == null) {
            return new ArrayList<>();
        } else {
            List<String> messages = extractCausesMessages(cause.getCause());
            messages.add(cause.getMessage());
            return messages;
        }
    }
}

