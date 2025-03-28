package org.projetperso.crypto.controller;

import org.projetperso.crypto.API.CoinGeckoApiException;
import org.projetperso.crypto.service.DataAlreadyExists;
import org.projetperso.crypto.service.DataNotFound;
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

    @ExceptionHandler(DataNotFound.class)
    public ResponseEntity<String> handleAlertNotFound(DataNotFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAlreadyExists.class)
    public ResponseEntity<String> handleAlertALreadyExists(DataAlreadyExists e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
