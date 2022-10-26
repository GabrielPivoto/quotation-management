package br.com.inatel.quotationManagement.mapper;


import br.com.inatel.quotationManagement.model.entity.Quote;
import br.com.inatel.quotationManagement.model.entity.StockAux;
import br.com.inatel.quotationManagement.model.form.Form;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class. Responsible for conversions concerning quotes.
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
public class QuoteMapper {

    public static List<Quote> convertMapToList(Form form, StockAux stock){
        List<Quote> quotes = new ArrayList<>();
        form.getQuotesMap().forEach((d,c) -> {
            quotes.add(new Quote(c,d,stock));
        });
        return quotes;
    }

    public static List<Quote> convertMapToList(Form form) {
        List<Quote> quotes = new ArrayList<>();
        form.getQuotesMap().forEach((d,c) -> {
            quotes.add(new Quote(c,d));
        });
        return quotes;
    }
}
