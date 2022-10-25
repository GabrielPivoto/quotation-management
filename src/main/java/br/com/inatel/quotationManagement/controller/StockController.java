package br.com.inatel.quotationManagement.controller;

import br.com.inatel.quotationManagement.mapper.StockMapper;
import br.com.inatel.quotationManagement.model.dto.StockDto;
import br.com.inatel.quotationManagement.model.form.Form;
import br.com.inatel.quotationManagement.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public List<StockDto> listAllStocks(){
        return stockService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStockByStockId(@PathVariable String id){
        return stockService.getStockByStockId(id);
    }

    @GetMapping("/stock")
    public List<StockDto> listStocksFromDocker(){
        return StockMapper.convertToDto(stockService.listStocksFromDocker());
    }

    @PostMapping("/postQuotes")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> postQuotes(@RequestBody Form form){
        return stockService.postStockQuotes(form);
    }

    @PostMapping("/postStocks")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> postStocks(@RequestBody Form form){
        return stockService.postStocks(form);
    }

}
