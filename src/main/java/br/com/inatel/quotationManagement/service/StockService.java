package br.com.inatel.quotationManagement.service;

import br.com.inatel.quotationManagement.exception.StockNotFoundException;
import br.com.inatel.quotationManagement.mapper.Mapper;
import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.entity.Quote;
import br.com.inatel.quotationManagement.model.entity.StockAux;
import br.com.inatel.quotationManagement.model.form.Form;
import br.com.inatel.quotationManagement.repository.QuoteRepository;
import br.com.inatel.quotationManagement.repository.StockQuoteRepository;
import br.com.inatel.quotationManagement.webclient.WebClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    private StockQuoteRepository stockQuoteRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private WebClientUtil webClientGetStocks;

    public List<StockDto> findAll(){
        List<StockAux> stocks = stockQuoteRepository.findAll();
        stocks.forEach(s -> s.getQuotes().size());
        return Mapper.convertToDto(stocks);
    }

    public StockDto findStockByStockId(String id){
        Optional<StockAux> opStock = stockQuoteRepository.findByStockId(id);
        if(opStock.isEmpty())
            throw new StockNotFoundException("StockId Not Found");
        return new StockDto(opStock.get());
    }


    public StockDto postStocks(Form form){
        StockAux stock;
        if(isAtStockManager(form.getStockId())){
            Optional<StockAux> optionalStock = stockQuoteRepository.findByStockId(form.getStockId());
            if(optionalStock.isPresent()){
                stock = optionalStock.get();
                List<Quote> quotes = Mapper.convertMapToList(form, optionalStock.get());
                quoteRepository.saveAll(quotes);
            }else{
                stock = Mapper.convertToEntity(form);
                stockQuoteRepository.save(stock);
                quoteRepository.saveAll(Mapper.convertMapToList(form,stock));
            }
            return new StockDto(stock);
        }
        throw new StockNotFoundException("StockId Not Found");
    }


    public ResponseEntity<?> deleteStock(String stockId){
        Optional<StockAux> opStock = stockQuoteRepository.findByStockId(stockId);
        if(opStock.isPresent()){
            List<Quote> quotes = opStock.get().getQuotes();
            stockQuoteRepository.delete(opStock.get());
            quoteRepository.deleteAll(quotes);
            return new ResponseEntity<>("Stock deleted",HttpStatus.NO_CONTENT);
        }
        throw new StockNotFoundException("StockId Not Found");
    }


    private List<StockAux> listStocksFromDocker(){
        return webClientGetStocks.listAllStocks();
    }

    public boolean isAtStockManager(String stockId){
        List<StockAux> stocks = listStocksFromDocker();
        return stocks.stream().anyMatch(s -> s.getId().equals(stockId));
    }

}
