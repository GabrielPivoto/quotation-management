package br.com.inatel.quotationManagement.service;

import br.com.inatel.quotationManagement.exception.StockNotFoundException;
import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.entity.Quote;
import br.com.inatel.quotationManagement.model.entity.StockAux;
import br.com.inatel.quotationManagement.repository.QuoteRepository;
import br.com.inatel.quotationManagement.repository.StockRepository;
import br.com.inatel.quotationManagement.webclient.WebClientGetStocks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;
    @Mock
    private QuoteRepository quoteRepository;
    @Mock
    private WebClientGetStocks webClientGetStocks;

    private String validStockId;
    private String inValidStockId;
    private StockAux stock;

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
        stock = new StockAux(validStockId, quotes);
    }

    @Test
    public void givenFindAllStocks_shouldReturnStockList(){
        when(stockRepository.findAll()).thenReturn(List.of());

        List<StockDto> stocks = stockService.findAll();

        assertEquals(0,stocks.size());
    }

    @Test
    public void givenFindAllStocks_shouldReturnEmptyList(){
        when(stockRepository.findAll()).thenReturn(List.of());

        List<StockDto> stocks = stockService.findAll();

        assertEquals(0,stocks.size());
    }



    @Test
    public void givenValidStockId_WhenGetStockByStockId_thenShouldReturnStockDto(){
        when(stockRepository.findByStockId(validStockId)).thenReturn(Optional.of(stock));

        StockDto stockDto = stockService.findStockByStockId(validStockId);

        assertEquals("petr4",stockDto.getStockId());
        assertEquals(stock.getQuotes().get(0).getCost(),stockDto.getQuotesMap().get(LocalDate.now()));
    }
    @Test
    public void givenInvalidStockId_WhenGetStockByStockId_thenShouldThorwStockNotFoundException(){
        when(stockRepository.findByStockId(validStockId)).thenThrow(StockNotFoundException.class);

        String errorMessage = null;
        HttpStatus status;

        try {
            StockDto stockDto = stockService.findStockByStockId(inValidStockId);
        }catch (StockNotFoundException ignored){
            errorMessage = ignored.getMessage();
        }
        assertEquals("StockId Not Found",errorMessage);
    }

}
