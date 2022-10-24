package br.com.inatel.quotationManagement.model.form;

import br.com.inatel.quotationManagement.model.entity.Quote;
import br.com.inatel.quotationManagement.model.entity.Stock;
import br.com.inatel.quotationManagement.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteForm {

    private String stockId;
    private LocalDate date;
    private Double cost;

    public Quote convert(StockRepository stockRepository) {
        Optional<Stock> stock = stockRepository.findByStockId(stockId);
        return new Quote(cost, date,stock.get());
    }
}
