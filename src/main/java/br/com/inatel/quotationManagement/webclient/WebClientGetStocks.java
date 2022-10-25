package br.com.inatel.quotationManagement.webclient;

import br.com.inatel.quotationManagement.model.entity.Stock;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * WebClient responsible for consuming external API.
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
@Service
public class WebClientGetStocks {

    public List<Stock> listAllStocks(){
        List<Stock> stocks = new ArrayList<>();

        Flux<Stock> fluxStock = WebClient.create("http://localhost:8080")
                .get()
                .uri("/stock")
                .retrieve()
                .bodyToFlux(Stock.class);

        fluxStock.subscribe(stocks::add);
        fluxStock.blockLast();

        return stocks;
    }

}
