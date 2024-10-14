package com.leute.rank_system.bot.service;

import org.springframework.http.HttpStatusCode;

public class HttpSendException extends RuntimeException {

    public HttpSendException(String message) {
        super(message);
    }

    public HttpSendException(String message, HttpStatusCode code) {
        super(String.format("%s: %s", message, code.value()));
    }

}
