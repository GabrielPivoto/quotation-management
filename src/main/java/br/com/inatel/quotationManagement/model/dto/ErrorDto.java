package br.com.inatel.quotationManagement.model.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDto {

    private HttpStatus status;
    private String errorMessage;

}
