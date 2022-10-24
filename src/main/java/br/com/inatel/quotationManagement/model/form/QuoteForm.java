package br.com.inatel.quotationManagement.model.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class QuoteForm {

    private String stockId;
    private Map<LocalDate, Double> quotesMap = new HashMap<>();

}
