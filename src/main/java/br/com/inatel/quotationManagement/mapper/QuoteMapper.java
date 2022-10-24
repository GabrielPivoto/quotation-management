package br.com.inatel.quotationManagement.mapper;


import br.com.inatel.quotationManagement.model.entity.Quote;
import br.com.inatel.quotationManagement.model.form.QuoteForm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuoteMapper {

    public static List<Quote> convertMapToList(QuoteForm form){
        List<Quote> quotes = new ArrayList<>();
        form.getQuotesMap().forEach((d,c) -> {
            quotes.add(new Quote(c,d));
        });
        return quotes;
    }

}
