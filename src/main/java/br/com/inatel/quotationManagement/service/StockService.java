package br.com.inatel.quotationManagement.service;

import br.com.inatel.quotationManagement.exception.StockNotFoundException;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    public StockDto findStockByStockId(String id){
        Optional<StockAux> opStock = stockRepository.findByStockId(id);
        if(opStock.isEmpty())
            throw new StockNotFoundException("StockId Not Found");
        return new StockDto(opStock.get());
    }

    public StockDto postStockAndQuotes(Form form){
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
            return new StockDto(stock);
        }
        throw new StockNotFoundException("StockId Not Found");
    }

    public ResponseEntity<?> deleteStock(String stockId){
        Optional<StockAux> opStock = stockRepository.findByStockId(stockId);
        if(opStock.isPresent()){
            List<Quote> quotes = opStock.get().getQuotes();
            stockRepository.delete(opStock.get());
            quoteRepository.deleteAll(quotes);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("StockId Not Found",HttpStatus.NOT_FOUND);
    }


    private List<StockAux> listStocksFromDocker(){
        return webClientGetStocks.listAllStocks();
    }

    private boolean isAtStockManager(String stockId){
        List<StockAux> stocks = listStocksFromDocker();
        return stocks.stream().anyMatch(s -> s.getId().equals(stockId));
    }

}
