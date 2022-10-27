package br.com.inatel.quotationManagement.service;

import br.com.inatel.quotationManagement.mapper.QuoteMapper;
import br.com.inatel.quotationManagement.mapper.StockMapper;
import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.entity.Quote;
import br.com.inatel.quotationManagement.model.entity.StockAux;
import br.com.inatel.quotationManagement.model.form.Form;
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

/**
 * Service class. Business logic implemented here.
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
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
        List<StockAux> stocks = stockRepository.findAll();
        stocks.forEach(s -> s.getQuotes().size());
        return StockMapper.convertToDto(stocks);
    }

    public ResponseEntity<?> findStockByStockId(String id){
        Optional<StockAux> dto = stockRepository.findByStockId(id);
        return (dto.isPresent())?new ResponseEntity<>(new StockDto(dto.get()),HttpStatus.OK):
                new ResponseEntity<>("StockId Not Found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> postStockAndQuotes(Form form){
        StockAux stock;
        if(isAtStockManager(form.getStockId())){
            Optional<StockAux> optionalStock = stockRepository.findByStockId(form.getStockId());
            if(optionalStock.isPresent()){
                stock = optionalStock.get();
                List<Quote> quotes = QuoteMapper.convertMapToList(form, optionalStock.get());
                quoteRepository.saveAll(quotes);
            }else{
                stock = StockMapper.convertToEntity(form);
                stockRepository.save(stock);
                quoteRepository.saveAll(QuoteMapper.convertMapToList(form,stock));
            }
            return new ResponseEntity<>(new StockDto(stock), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("StockId Not Found", HttpStatus.BAD_REQUEST);
    }


    private List<StockAux> listStocksFromDocker(){
        return webClientGetStocks.listAllStocks();
    }

    private boolean isAtStockManager(String stockId){
        List<StockAux> stocks = listStocksFromDocker();
        return stocks.stream().anyMatch(s -> s.getId().equals(stockId));
    }

}
