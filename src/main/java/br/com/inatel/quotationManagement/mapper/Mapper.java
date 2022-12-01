package br.com.inatel.quotationManagement.mapper;

import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.entity.Quote;
import br.com.inatel.quotationManagement.model.entity.StockAux;
import br.com.inatel.quotationManagement.model.form.Form;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class. Responsible for convertions.
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
public class Mapper {

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

    public static List<StockDto> convertToDto(List<StockAux> stocks){
        return stocks.stream().map(StockDto::new).collect(Collectors.toList());
    }

    public static StockAux convertToEntity(Form form){
        return new StockAux(form.getStockId(),Mapper.convertMapToList(form));
    }

}
