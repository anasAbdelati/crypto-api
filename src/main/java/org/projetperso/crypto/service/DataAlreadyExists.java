package org.projetperso.crypto.service;

public class DataAlreadyExists extends RuntimeException {
    public DataAlreadyExists(String message) {
        super(message);
    }
}
