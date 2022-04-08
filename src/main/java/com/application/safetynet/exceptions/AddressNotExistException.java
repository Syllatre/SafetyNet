package com.application.safetynet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AddressNotExistException extends RuntimeException {
    public AddressNotExistException(String s) {
        super(s);
    }
}
