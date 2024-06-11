package com.task.currency.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Duplicating request id, please use a newly generated id")
public class DuplicateIdException extends RuntimeException{
}
