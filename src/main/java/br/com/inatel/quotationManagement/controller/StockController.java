package br.com.inatel.quotationManagement.controller;

import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.form.Form;
import br.com.inatel.quotationManagement.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/quote")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public List<StockDto> listAllStocks(){
        return stockService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStockByStockId(@PathVariable String id){
        return stockService.findStockByStockId(id);
    }

    @PostMapping
    public ResponseEntity<?> postStocks(@RequestBody @Valid Form form){
        return stockService.postStockAndQuotes(form);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStocks(@PathVariable String id){
        return stockService.deleteStock(id);
    }

}
