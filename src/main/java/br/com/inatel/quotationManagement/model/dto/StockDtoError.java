package br.com.inatel.quotationManagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StockDtoError {

    private String field;
    private String error;

}
