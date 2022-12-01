package br.com.inatel.quotationManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StockCacheController {

    @CacheEvict(value = "stockList", allEntries = true)
    @DeleteMapping("/stockcache")
    public void deleteCache(){
        log.info("Cache has been cleaned");
    }

}
