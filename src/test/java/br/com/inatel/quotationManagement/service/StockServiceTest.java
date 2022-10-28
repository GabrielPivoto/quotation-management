package br.com.inatel.quotationManagement.service;

import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.repository.QuoteRepository;
import br.com.inatel.quotationManagement.repository.StockRepository;
import br.com.inatel.quotationManagement.webclient.WebClientGetStocks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;
    @Mock
    private QuoteRepository quoteRepository;
    @Mock
    private WebClientGetStocks webClientGetStocks;
    @InjectMocks
    private StockService stockService = new StockService();

    @Before
    public void init(){

    }

    @Test
    public void givenFindAllStocks_shouldReturnEmptyList(){
        when(stockRepository.findAll()).thenReturn(List.of());
        List<StockDto> stocks = stockService.findAll();
        assertEquals(0,stocks.size());
    }

}
