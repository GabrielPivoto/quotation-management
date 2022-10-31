package br.com.inatel.quotationManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(String message) {
        super(message);
    }
}
