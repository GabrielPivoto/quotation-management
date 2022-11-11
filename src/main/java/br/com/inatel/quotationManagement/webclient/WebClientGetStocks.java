package br.com.inatel.quotationManagement.webclient;

import br.com.inatel.quotationManagement.model.entity.StockAux;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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

    @Value("${url.manager}")
    private String URL_MANAGER;

    @Cacheable(value = "stockList")
    public List<StockAux> listAllStocks(){
        List<StockAux> stocks = new ArrayList<>();

        Flux<StockAux> fluxStock = WebClient.create(URL_MANAGER)
                .get()
                .uri("/stock")
                .retrieve()
                .bodyToFlux(StockAux.class);

        fluxStock.subscribe(stocks::add);
        fluxStock.blockLast();

        return stocks;
    }

}
