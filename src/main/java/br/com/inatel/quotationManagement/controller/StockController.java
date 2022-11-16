package br.com.inatel.quotationManagement.controller;

import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.form.Form;
import br.com.inatel.quotationManagement.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class. All endpoints are built here.
 * @author Gabriel Pivoto
 * @since Oct. 2022
 */
@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/quote")
    @ResponseStatus(HttpStatus.OK)
    public List<StockDto> listAllStocks(){
        return stockService.findAll();
    }

    @GetMapping("/quote/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StockDto getStockByStockId(@PathVariable String id){
        return stockService.findStockByStockId(id);
    }

    @PostMapping("/quote")
    @ResponseStatus(HttpStatus.CREATED)
    public StockDto postStocks(@RequestBody @Valid Form form){
        return stockService.postStocks(form);
    }

    @DeleteMapping("/quote/{id}")
    public ResponseEntity<?> deleteStocks(@PathVariable String id){
        return stockService.deleteStock(id);
    }

    @DeleteMapping("/stockcache")
    public void deleteCache(){
        System.out.println("Cache cleaned");
    }

}
