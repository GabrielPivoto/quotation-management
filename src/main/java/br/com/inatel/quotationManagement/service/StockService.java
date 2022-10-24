package br.com.inatel.quotationManagement.service;

import br.com.inatel.quotationManagement.mapper.QuoteMapper;
import br.com.inatel.quotationManagement.mapper.StockMapper;
import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.entity.Quote;
import br.com.inatel.quotationManagement.model.entity.Stock;
import br.com.inatel.quotationManagement.model.form.QuoteForm;
import br.com.inatel.quotationManagement.repository.QuoteRepository;
import br.com.inatel.quotationManagement.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    public List<StockDto> findAll(){
        List<Stock> stocks = stockRepository.findAll();
        stocks.forEach(s -> s.getQuotes().size());
        return StockMapper.convertToDto(stocks);
    }

    public ResponseEntity<StockDto> getStockByStockId(String id){
        Optional<Stock> dto = stockRepository.findByStockId(id);
        return dto.map(value -> ResponseEntity.ok(new StockDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public void postQuotes(QuoteForm quoteForm){
        Optional<Stock> optionalStock = stockRepository.findByStockId(quoteForm.getStockId());
        if(optionalStock.isPresent()){
            Stock stock = optionalStock.get();
            List<Quote> quotes = QuoteMapper.convertMapToList(quoteForm);
            quotes.forEach(q -> {
                q.setStock(stock);
                quoteRepository.save(q);
            });
        }
    }

}
