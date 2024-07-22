package com.yobrunox.webbasic.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RequestException extends RuntimeException{
    private HttpStatus code;
    public RequestException(HttpStatus code, String message) {
        super(message);
        this.code = code;
    }

}
