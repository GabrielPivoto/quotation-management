package br.com.inatel.quotationManagement.notification;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationUtil implements InitializingBean {

    @Value("${url.manager}")
    private String URL_MANAGER;

    @Value("${url.host}")
    private String host;

    @Value("${url.port}")
    private String port;

    @EventListener(ApplicationReadyEvent.class)
    public void postNotification(){
//        Notification notification = new Notification(host,port);
//        WebClient.create(URL_MANAGER)
//                .post()
//                .uri("/notification")
//                .bodyValue(notification)
//                .retrieve()
//                .toBodilessEntity()
//                .block();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        postNotification();
    }
}
