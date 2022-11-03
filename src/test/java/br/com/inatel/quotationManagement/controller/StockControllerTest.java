package br.com.inatel.quotationManagement.controller;

import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.entity.StockAux;
import br.com.inatel.quotationManagement.model.form.Form;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
public class StockControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test @Order(1)
    public void givenStocksRequest_whenGetAllStocks_thenShouldReturn200Ok(){
        webTestClient.get()
                .uri("/quote")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .returnResult();
    }

    @Test @Order(2)
    public void givenValidStockId_whenPostStock_thenShouldReturnStatusCreatedAndValidStock(){
        Form form = new Form();
        form.setStockId("petr4");
        Map<LocalDate,Double> quotesMap = new HashMap<>();
        quotesMap.put(LocalDate.now(),30.0);
        form.setQuotesMap(quotesMap);

        StockAux stock = webTestClient.post()
                .uri("/quote")
                .bodyValue(form)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(StockAux.class)
                .returnResult().getResponseBody();

        assertNotNull(stock);
        assertNotNull(stock.getId());
        assertEquals("petr4",stock.getStockId());
    }

    @Test @Order(3)
    void givenValidStockId_whenGetStockByStockId_thenShouldReturnValidStockAnd200Ok(){
        String stockId = "petr4";

        StockAux stock = webTestClient.get()
                .uri("/quote/" + stockId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StockAux.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(stock);
        assertEquals(stock.getStockId(),stockId);
    }


    @Test @Order(4)
    public void givenInvalidStockId_whenPostStock_thenShouldReturnStatusNotFound(){
        Form form = new Form();
        form.setStockId("vale5");
        Map<LocalDate,Double> quotesMap = new HashMap<>();
        quotesMap.put(LocalDate.now(),30.0);
        form.setQuotesMap(quotesMap);

        String result = webTestClient.post()
                .uri("/quote")
                .bodyValue(form)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(StockAux.class)
                .returnResult().toString();

        assertTrue(result.contains("StockId Not Found"));
    }


    @Test @Order(5)
    void givenInvalidStockId_whenGetStockByStockId_thenShouldReturnStatusNotFound(){
        String stockId = "vale5";

        String result = webTestClient.get()
                .uri("/quote/" + stockId)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(StockAux.class)
                .returnResult()
                .toString();

        assertTrue(result.contains("StockId Not Found"));
    }

}
