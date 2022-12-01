package br.com.inatel.quotationManagement.service;

import br.com.inatel.quotationManagement.exception.StockNotFoundException;
import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.entity.Quote;
import br.com.inatel.quotationManagement.model.entity.StockAux;
import br.com.inatel.quotationManagement.model.form.Form;
import br.com.inatel.quotationManagement.repository.QuoteRepository;
import br.com.inatel.quotationManagement.repository.StockQuoteRepository;
import br.com.inatel.quotationManagement.webclient.WebClientUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StockServiceTest {

    @Mock
    private StockQuoteRepository stockRepository;
    @Mock
    private QuoteRepository quoteRepository;
    @Mock
    private WebClientUtil webClientGetStocks;

    private String validStockId;
    private String inValidStockId;
    private StockAux stock1;
    private StockAux stock2;
    private Form form;
    private Map<LocalDate,Double> quotesMap;

    @InjectMocks
    private StockService stockService = new StockService();

    @Before
    public void init(){
        validStockId = "petr4";
        inValidStockId = "teste1";
        List<Quote> quotes = new ArrayList<>();
        quotes.add(new Quote(35.0, LocalDate.now()));
        quotes.add(new Quote(12.0, LocalDate.now().plusDays(1)));
        quotes.add(new Quote(3.5, LocalDate.now().plusDays(3)));
        stock1 = new StockAux(validStockId, quotes);
        stock2 = new StockAux(validStockId, quotes);
        stock1.setId("petr4");
        stock2.setId("val5");
        quotesMap = new HashMap<>();
        quotesMap.put(LocalDate.now(),12.5);
        form = new Form("petr4",quotesMap);
    }

    @Test
    public void givenFindAllStocks_shouldReturnStockList(){
        when(stockRepository.findAll()).thenReturn(List.of(stock1,stock2));

        List<StockDto> stocks = stockService.findAll();

        assertEquals(2,stocks.size());
    }

    @Test
    public void givenFindAllStocks_shouldReturnEmptyList(){
        when(stockRepository.findAll()).thenReturn(List.of());

        List<StockDto> stocks = stockService.findAll();

        assertEquals(0,stocks.size());
    }


    @Test
    public void givenValidStockId_WhenGetStockByStockId_thenShouldReturnStockDto(){
        when(stockRepository.findByStockId(validStockId)).thenReturn(Optional.of(stock1));

        StockDto stockDto = stockService.findStockByStockId(validStockId);

        assertEquals("petr4",stockDto.getStockId());
        assertEquals(stock1.getQuotes().get(0).getCost(),stockDto.getQuotesMap().get(LocalDate.now()));
    }

    @Test
    public void givenInvalidStockId_WhenGetStockByStockId_thenShouldThrowStockNotFoundException(){
        when(stockRepository.findByStockId(inValidStockId)).thenReturn(Optional.empty());

        String errorMessage = null;
        HttpStatus status;

        try {
            StockDto stockDto = stockService.findStockByStockId(inValidStockId);
        }catch (StockNotFoundException ignored){
            errorMessage = ignored.getMessage();
        }
        assertEquals("StockId Not Found",errorMessage);
    }

    @Test
    public void givenValidStock_WhenPostStock_thenShouldReturnStockDto(){
        when(stockRepository.findByStockId(form.getStockId())).thenReturn(Optional.of(stock1));
        when(webClientGetStocks.listAllStocks()).thenReturn(List.of(stock1,stock2));

        StockDto dto = stockService.postStocks(form);

        assertEquals(validStockId,dto.getStockId());
    }

    @Test
    public void givenAStockThatIsNotAtStockManager_whenPostStock_thenShouldThrowStockNotFoundException(){
        when(webClientGetStocks.listAllStocks()).thenReturn(List.of());

        String result = null;

        try {
            StockDto dto = stockService.postStocks(form);
        }catch (Exception e){
            result = e.getMessage();
        }

        assertEquals("StockId Not Found", result);
    }

    @Test
    public void givenAStockThatIsAtStockManager_whenPostStock_thenShouldReturnStockDto(){
        when(stockRepository.findByStockId(form.getStockId())).thenReturn(Optional.empty());
        when(webClientGetStocks.listAllStocks()).thenReturn(List.of(stock1,stock2));

        StockDto dto = stockService.postStocks(form);

        assertEquals("petr4", dto.getStockId());
    }

    @Test
    public void test(){
        when(stockRepository.findByStockId(form.getStockId())).thenThrow(IllegalStateException.class);

        try{
            StockDto stockDto = stockService.findStockByStockId(form.getStockId());
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    @Test
    public void givenValidStockId_whenDeleteStockByStockId_thenShouldReturnDeleteMessage(){
        when(stockRepository.findByStockId(form.getStockId())).thenReturn(Optional.of(stock1));

        String deleted = String.valueOf(stockService.deleteStock(form.getStockId()));

        assertTrue(deleted.contains("Stock deleted"));
    }

    @Test
    public void givenInvalidStockId_whenDeleteStockByStockId_thenShouldThrowStockNotFoundException(){

        String result = null;

        try {
            stockService.deleteStock(form.getStockId());
        }catch (Exception e){
            result = e.getMessage();
        }

        assertEquals("StockId Not Found", result);
    }

}
