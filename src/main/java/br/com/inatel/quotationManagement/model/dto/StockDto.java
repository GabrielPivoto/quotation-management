package br.com.inatel.quotationManagement.model.dto;

import br.com.inatel.quotationManagement.model.entity.Stock;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing the Data Transfer Object of a Stock.
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
@Data
public class StockDto {

    private String id;
    private String stockId;
    private Map<LocalDate, Double> quotesMap = new HashMap<>();

    public StockDto(Stock stock){
        this.id = stock.getId();
        this.stockId = stock.getStockId();
        stock.getQuotes().forEach(q -> quotesMap.put(q.getDate(),q.getCost()));
    }

}
