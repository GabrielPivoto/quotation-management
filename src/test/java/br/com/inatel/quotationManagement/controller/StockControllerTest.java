package br.com.inatel.quotationManagement.controller;

import br.com.inatel.quotationManagement.model.entity.Quote;
import br.com.inatel.quotationManagement.model.entity.StockAux;
import br.com.inatel.quotationManagement.model.form.Form;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test @Order(1)
    public void givenStocksRequest_whenGetAllStocks_thenShouldReturnEmptyStocksListAnd200(){
        webTestClient.get()
                .uri("/quote")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .returnResult();
    }

    @Test @Order(2)
    public void givenNewStock_whenPostStock_thenShouldReturnStatusCreatedAndValidStock(){
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
    }

    @Test @Order(3)
    void givenValidStockId_whenGetStockByStockId_thenShouldReturnValidStockAnd200(){
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


}
