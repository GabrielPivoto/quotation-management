package br.com.inatel.quotationManagement.model.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Form class. Used for sending data to DataBase.
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
@Data
public class Form {

    private String stockId;
    private Map<LocalDate, Double> quotesMap = new HashMap<>();

}
