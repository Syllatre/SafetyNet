package com.application.safetynet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotExistException extends RuntimeException {
    public PersonNotExistException(String s) {super(s);}
}
