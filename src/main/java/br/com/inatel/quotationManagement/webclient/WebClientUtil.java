package br.com.inatel.quotationManagement.webclient;

import br.com.inatel.quotationManagement.model.entity.StockAux;
import br.com.inatel.quotationManagement.notification.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
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
public class WebClientUtil implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${url.manager}")
    private String URL_MANAGER;

    @Value("${url.host}")
    private String host;

    @Value("${url.port}")
    private String port;

    public void postNotification(){
        Notification notification = new Notification(host,port);
        try {
            WebClient.builder().baseUrl("http://"+URL_MANAGER).build()
                    .post()
                    .uri("/notification")
                    .bodyValue(notification)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        postNotification();
    }

    @Cacheable(value = "stockList")
    public List<StockAux> listAllStocks(){
        List<StockAux> stocks = new ArrayList<>();

        Flux<StockAux> fluxStock = WebClient.builder().baseUrl("http://"+URL_MANAGER).build()
                .get()
                .uri("/stock")
                .retrieve()
                .bodyToFlux(StockAux.class);

        fluxStock.subscribe(stocks::add);
        fluxStock.blockLast();

        return stocks;
    }

}
