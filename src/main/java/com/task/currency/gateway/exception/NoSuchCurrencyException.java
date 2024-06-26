package com.task.currency.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="No such currency type")
public class NoSuchCurrencyException extends RuntimeException{
}
