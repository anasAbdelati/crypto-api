package org.projetperso.crypto.controller;

import org.projetperso.crypto.API.CoinGeckoApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CoinGeckoApiException.class)
    public ResponseEntity<String> handleCoinGeckoApiException(CoinGeckoApiException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
