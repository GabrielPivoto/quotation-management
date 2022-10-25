package br.com.inatel.quotationManagement.service;

import br.com.inatel.quotationManagement.mapper.QuoteMapper;
import br.com.inatel.quotationManagement.mapper.StockMapper;
import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.entity.Quote;
import br.com.inatel.quotationManagement.model.entity.Stock;
import br.com.inatel.quotationManagement.model.form.QuoteForm;
import br.com.inatel.quotationManagement.repository.QuoteRepository;
import br.com.inatel.quotationManagement.repository.StockRepository;
import br.com.inatel.quotationManagement.webclient.WebClientGetStocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private WebClientGetStocks webClientGetStocks;

    @Cacheable(value = "stockList")
    public List<StockDto> findAll(){
        List<Stock> stocks = stockRepository.findAll();
        stocks.forEach(s -> s.getQuotes().size());
        return StockMapper.convertToDto(stocks);
    }

    public ResponseEntity<?> getStockByStockId(String id){
        Optional<Stock> dto = stockRepository.findByStockId(id);
        return (dto.isPresent())?new ResponseEntity<>(new StockDto(dto.get()),HttpStatus.OK):
                new ResponseEntity<>("StockId Not Found", HttpStatus.NOT_FOUND);
    }

    @CacheEvict(value = "stockList", allEntries = true)
    public ResponseEntity<?> postQuotes(QuoteForm quoteForm){
        Optional<Stock> optionalStock = stockRepository.findByStockId(quoteForm.getStockId());
        if(optionalStock.isPresent()){
            Stock stock = optionalStock.get();
            List<Quote> quotes = QuoteMapper.convertMapToList(quoteForm, stock);
            quoteRepository.saveAll(quotes);
            stock.setQuotes(quotes);
            return new ResponseEntity<>(findAll(), HttpStatus.CREATED);
        }else
            return new ResponseEntity<>("StockId Not Found", HttpStatus.NOT_FOUND);
        
    }

    public List<Stock> listStocksFromDocker(){
        return webClientGetStocks.listAllStocks();
    }

}
